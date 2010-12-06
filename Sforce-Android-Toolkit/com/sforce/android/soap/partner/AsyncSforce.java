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
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;

import android.net.http.AndroidHttpClient;
import android.os.Bundle;

import com.sforce.android.soap.partner.fault.FaultSoapResponse;
import com.sforce.android.soap.partner.sobject.SObject;


public class AsyncSforce {
	private static final String ANDROID="Android";
	private static final String SOAP_ACTION="soapaction";
	private static final String SERVER="Server";
	private static final String SOAP_RESPONSE="response";
	private static final String SOAP_SERVER="soapServer"; 
	private static final String CONTENT_TYPE="Content-Type";
	private static final String RESPONSE_TYPE="responseType";
	private static final String FAULT_STRING="<soapenv:Fault>";
	private static final String soapAction="\"\"";

	private Sforce sf;
	
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
    	
        new Thread() {
            @Override public void run() {	
            		AndroidHttpClient androidHttpClient1=AndroidHttpClient.newInstance(ANDROID);
            		Response qr=null;
                	Request request=SforceSoapRequestFactory.getSoapRequest(requestFields);
            		String SOAPRequestXML=request.getRequest();
            		try{
	        			HttpPost httppost = new HttpPost(sf.getServerURL());
	        			HttpEntity entity = new StringEntity(SOAPRequestXML);
	        			httppost.setEntity(entity);
	        			httppost.setHeader(SOAP_ACTION, soapAction);
	        			httppost.setHeader(CONTENT_TYPE, "text/xml; charset=utf-8");
	        			httppost.setHeader(SERVER, requestFields.get(SOAP_SERVER));
	        			HttpResponse response=androidHttpClient1.execute(httppost);

	        			String soapResponseString=null;
	        			if (response!=null)
	        			{
	        				HttpEntity r_entity = response.getEntity();
	        				BufferedHttpEntity b_entity=new BufferedHttpEntity(r_entity);
	        				BufferedReader br = new BufferedReader(new InputStreamReader(b_entity.getContent()));  
	        				StringBuffer sb = new StringBuffer();  
	        				String line;  
	        				while ((line = br.readLine()) != null) {  
	        				   sb.append(line).append("\n");  
	        				}  
	        				soapResponseString = sb.toString(); 
	        			}
        		
        		Bundle bundle=new Bundle();
        		if (soapResponseString.contains(FAULT_STRING)){
        			bundle.putString(RESPONSE_TYPE, "fault");
        		} else {
        			bundle.putString(RESPONSE_TYPE, requestFields.get("responseType"));
        		}
        		bundle.putString(SOAP_RESPONSE, soapResponseString);
        		qr=SforceSoapResponseFactory.getSoapResponse(bundle);
        		}catch(IOException ioe){
        			throw new RuntimeException(ioe);
        		} finally{
        			androidHttpClient1.getConnectionManager().shutdown();
        			androidHttpClient1=null;
        		}
        		if (qr instanceof FaultSoapResponse){
        			listener.onSforceError(requestFields.get("responseType"), qr);
         		}
        		else {
        			listener.onComplete(qr);
        		}
            }
        }.start();
    }

    public void request(final ArrayList<SObject> records, final HashMap<String, String> sessionFields, final RequestListener listener) {
    	
        new Thread() {
            @Override public void run() {	
            		AndroidHttpClient androidHttpClient1=AndroidHttpClient.newInstance(ANDROID);
            		Response qr=null;
                	Request request=SforceSoapRequestFactory.getSoapRequest(records, sessionFields);
            		String SOAPRequestXML=request.getRequest();
            		try{
	        			HttpPost httppost = new HttpPost(sf.getServerURL());
	        			HttpEntity entity = new StringEntity(SOAPRequestXML);
	        			httppost.setEntity(entity);
	        			httppost.setHeader(SOAP_ACTION, soapAction);
	        			httppost.setHeader(CONTENT_TYPE, "text/xml; charset=utf-8");
	        			httppost.setHeader(SERVER, sessionFields.get(SOAP_SERVER));
	        			HttpResponse response=androidHttpClient1.execute(httppost);

	        			String soapResponseString=null;
	        			if (response!=null)
	        			{
	        				HttpEntity r_entity = response.getEntity();
	        				BufferedHttpEntity b_entity=new BufferedHttpEntity(r_entity);
	        				BufferedReader br = new BufferedReader(new InputStreamReader(b_entity.getContent()));  
	        				StringBuffer sb = new StringBuffer();  
	        				String line;  
	        				while ((line = br.readLine()) != null) {  
	        				   sb.append(line).append("\n");  
	        				}  
	        				soapResponseString = sb.toString(); 
	        			}
        		
        		Bundle bundle=new Bundle();
        		if (soapResponseString.contains(FAULT_STRING)){
        			bundle.putString(RESPONSE_TYPE, "fault");
        		} else {
        			bundle.putString(RESPONSE_TYPE, sessionFields.get("responseType"));
        		}
        		bundle.putString(SOAP_RESPONSE, soapResponseString);
        		qr=SforceSoapResponseFactory.getSoapResponse(bundle);
        		}catch(IOException ioe){
        			throw new RuntimeException(ioe);
        		} finally{
        			androidHttpClient1.getConnectionManager().shutdown();
        			androidHttpClient1=null;
        		}
        		if (qr instanceof FaultSoapResponse){
        			listener.onSforceError(sessionFields.get("responseType"), qr);
        		}
        		else {
        			listener.onComplete(qr);
        		}
            }
        }.start();
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
