/**
 * AsyncSforce.java
 *
 * Main class that executes the Sforce SOAP requests asynchronously
 * 
 */

package com.sforce.android.soap.partner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sforce.android.soap.partner.fault.FaultSoapResponse;
import com.sforce.android.soap.partner.sobject.SObject;


public class AsyncSforce {
	private static final String ANDROID = "Android";
	private static final String SOAP_ACTION = "soapaction";
	private static final String SOAP_ACTION_VALUE = "\"\"";
	private static final String SOAP_RESPONSE = "response";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String RESPONSE_TYPE = "responseType";

	private static final String PROD_OAUTH_URL="https://login.salesforce.com/services/oauth2/authorize?response_type=token&display=touch&client_id=";
	private static final String SANDBOX_OAUTH_URL="https://test.salesforce.com/services/oauth2/authorize?response_type=token&display=touch&client_id=";
	
	private final Sforce sf;
    private RequestListener oAuthLoginListener = null;
	
    public AsyncSforce(Sforce sf) {
    	this.sf = sf;
    }

    /**
     * Gets the request parameters in HashMap. Sends the appropriate SOAP Sforce SOAP request
     * and then returns the appropriate SOAP response. Based on success or failure, it invokes
     * fault processing or success processing in the listener.
     * 
     * @return void
     */
    public void request(final HashMap<String, String> requestFields, final RequestListener listener) {
    	request(null, requestFields, false, listener);
    }

    public void request(final ArrayList<SObject> records, final HashMap<String, String> sessionFields, final RequestListener listener) {
    	request(records, sessionFields, true, listener);
    }
    
    private void request(final ArrayList<SObject> records, final HashMap<String, String> sessionFields, final boolean isRecordsRequest, final RequestListener listener) {
        new Thread() {
            @Override 
            public void run() {	
           		AndroidHttpClient httpClient = AndroidHttpClient.newInstance(ANDROID);
           		Response qr = null;
				Request request = isRecordsRequest ? SforceSoapRequestFactory.getSoapRequest(records, sessionFields)
						: SforceSoapRequestFactory.getSoapRequest(sessionFields);
           		String SOAPRequestXML=request.getRequest();
           		try{
        			HttpPost httppost = new HttpPost(sf.getServerURL());
        			HttpEntity entity = new StringEntity(SOAPRequestXML);
        			httppost.setEntity(entity);
        			httppost.setHeader(SOAP_ACTION, SOAP_ACTION_VALUE);
        			httppost.setHeader(CONTENT_TYPE, "text/xml; charset=utf-8");
        			HttpResponse response = httpClient.execute(httppost);

        			String soapResponseString=null;
    				HttpEntity r_entity = response.getEntity();
    				BufferedHttpEntity b_entity=new BufferedHttpEntity(r_entity);
    				BufferedReader br = new BufferedReader(new InputStreamReader(b_entity.getContent(), "UTF8"));  
    				StringBuffer sb = new StringBuffer();  
    				String line;  
    				while ((line = br.readLine()) != null) {  
    				   sb.append(line).append("\n");  
    				}  
    				soapResponseString = sb.toString(); 
        		
        			Bundle bundle = new Bundle();
        			if (response.getStatusLine().getStatusCode() > 299) {
        				bundle.putString(RESPONSE_TYPE, "fault");
        			} else {
        				bundle.putString(RESPONSE_TYPE, sessionFields.get("responseType"));
        			}
        			bundle.putString(SOAP_RESPONSE, soapResponseString);
        			qr = SforceSoapResponseFactory.getSoapResponse(bundle);
        			
        		} catch(IOException ioe) {
        			throw new RuntimeException(ioe);

        		} finally {
        			httpClient.close();
        			httpClient.getConnectionManager().shutdown();
        		}

        		if (qr instanceof FaultSoapResponse) {
        			listener.onSforceError(sessionFields.get("responseType"), qr);
        		} else {
        			listener.onComplete(qr);
        		}
            }
        }.start();
    }    

    public void loginOAuth(Activity mActivity, OAuthConnectorConfig parameters, RequestListener listener){
    	oAuthLoginListener = listener;
    	
	  	StringBuffer oauthURL=new StringBuffer();
		if (parameters.getIsSandbox()){
			oauthURL.append(SANDBOX_OAUTH_URL).append(parameters.getConsumerKey()).append("&redirect_uri=").append(parameters.getCallbackURL());
		} else {
			oauthURL.append(PROD_OAUTH_URL).append(parameters.getConsumerKey()).append("&redirect_uri=").append(parameters.getCallbackURL());
		}

    	WebView webview = new WebView(mActivity.getApplicationContext());
    	mActivity.setContentView(webview);
    	
        webview.setWebViewClient(new OAuthWebViewClient(parameters.getCallbackURL()));
        
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(oauthURL.toString());
        webview.requestFocus(View.FOCUS_DOWN);        
    }

    private class OAuthWebViewClient extends WebViewClient {
        
    	private String callbackURL;
    	public OAuthWebViewClient(String cURL) {
    		super();
    		callbackURL	= cURL;
    	}
    	
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            
            /* Check to make sure that the redirect URI from Salesforce contains the 'special' URI that we passed as the
             * redirect URI in the initial OAuth request. This confirms that the redirect is indeed from Salesforce.
             */
    	if (url.startsWith(callbackURL)) {
    			String temp = url.substring(callbackURL.length()+1);
    			String firstParam = temp.split("=")[0];
    			
    			if (firstParam.equals("error")){
    				oAuthLoginListener.onSforceError("oAuthError", new OAuthFaultResponse(temp));
    			}
    			else{	
    				oAuthLoginListener.onComplete(parseToken(temp));
    			}	
                return true;
            } else {
            	return false;
            }
        }
           
        /* Per the OAuth 2.0 Use Agent flow supported by Salesforce, the redirect URI will contain the access token (among other
         * other things) after the '#' sign. This method extracts those values. 
         */

    	private OAuthLoginResult parseToken(String url) {
    		String[] keypairs = url.split("&");
    		OAuthLoginResult oauthResult = new OAuthLoginResult();
    		for (int i=0;i<keypairs.length;i++) {
    			String[] onepair = keypairs[i].split("=");
    			if (onepair[0].equals("access_token")) {
    				oauthResult.setAccessToken(URLDecoder.decode(onepair[1]));
    			} else if (onepair[0].equals("refresh_token")) {
    				oauthResult.setRefreshToken(onepair[1]);
    			} else if (onepair[0].equals("instance_url")) {
    				oauthResult.setInstanceUrl(onepair[1]);
    				oauthResult.setInstanceUrl(oauthResult.getInstanceUrl().replaceAll("%2F", "/"));
    				oauthResult.setInstanceUrl(oauthResult.getInstanceUrl().replaceAll("%3A", ":"));
    			} else if (onepair[0].equals("id")) {
    				oauthResult.setId(onepair[1]);
    			} else if (onepair[0].equals("issued_at")) {
    				oauthResult.setIssuedAt(Long.valueOf(onepair[1]));
    			} else if (onepair[0].equals("signature")) {
    				oauthResult.setSignature(onepair[1]);
    			}
    		}
    		return oauthResult;
    	}   	    	
    }
    /**
     * Interface to be implemented in the Listener class.
     * 
     */
    public static interface RequestListener {
        public void onComplete(Object response);
		public void onSforceError(String faultType, Response fsr);
        public void onException(Exception e);
    }
}
