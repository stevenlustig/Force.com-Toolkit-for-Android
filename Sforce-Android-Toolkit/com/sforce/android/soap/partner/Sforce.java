package com.sforce.android.soap.partner;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

public class Sforce {

	static final String SERVERURL = "serverUrl";
	static final String SESSIONID = "sessionId";
	static final String LOGINRESPONSE="loginResponse";
	static final String END_POINT="endPoint";
	static final String USER_AGENT="User-Agent";
	static final String ANDROID="Android";
	static final String SOAP_ACTION="SOAPAction";
	static final String CONTENT_TYPE="Content-Type";
	static final String CONTENT_LENGTH="Content-Length";
	static final String CONNECTION="Connection";
	static final String RESPONSE="response";
	static final String RESPONSE_TYPE="responseType";
	static final String FAULT_STRING="<soapenv:Fault>";
	static final String FAULT_RESPONSE="faultResponse";
	static final String FAULT_TYPE="faultType";
	
	static final String soapAction="\"\"";
	LoginResult lr=null;
    private String mSessionId = null;
    private String mServerURL = null;
    private long mSessionExpires = 0;
    private String mUserName=null;
    private String mPassword=null;
    private String mSecurityToken=null;
    private String mAccessToken=null;
    private String mEndPoint=null;
    private String mSoapServer = null;
    Context context;

    public Sforce(Context context){
    	this.context=context;
    }

    public LoginResult login(final HashMap<String, String> requestFields, final Context context){
		String loginString=null;
		lr=new LoginResult();
		LoginSoapResponse loginResponse;

		try{
			String url=requestFields.get(END_POINT);
			System.out.println("url="+url);
			HttpURLConnection connection;
			connection = (HttpURLConnection) new URL(url).openConnection();
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    connection.setDoInput(true);
		    
		    Request lsr=SforceSoapRequestFactory.getSoapRequest(requestFields);
		    String fin=lsr.getRequest();
	        ByteArrayOutputStream bout = new ByteArrayOutputStream();
	        copy(fin,bout);
	        byte[] requestData = bout.toByteArray();
	        
	        connection.setRequestProperty(USER_AGENT, ANDROID);
	        connection.setRequestProperty(SOAP_ACTION, soapAction);
	        connection.setRequestProperty(CONTENT_TYPE, "text/xml");
	        connection.setRequestProperty(CONNECTION, "close");
	        connection.setRequestProperty(CONTENT_LENGTH, "" + requestData.length);
	        connection.setRequestMethod("POST");
	        connection.connect();
	        
	        OutputStream os = connection.getOutputStream();
	        os.write(requestData, 0, requestData.length);
	        os.flush();
	        os.close();
	        requestData = null;
	        InputStream is;
            connection.connect();
    		is=connection.getInputStream();
    		if (is != null) {
    			Writer writer = new StringWriter();
    			
    			char[] buffer = new char[1024];
    			try {
    				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    				int n;
    				while ((n = reader.read(buffer)) != -1) {
    					writer.write(buffer, 0, n);
    				}
    			} finally {
    				is.close();
    			}
    			loginString=writer.toString();
    			} else {       
    				loginString=null;
    			}
    		Bundle bundle=new Bundle();
    		bundle.putString(RESPONSE, loginString);
			bundle.putString(RESPONSE_TYPE, requestFields.get("responseType"));
			loginResponse=(LoginSoapResponse)SforceSoapResponseFactory.getSoapResponse(bundle);
    		lr=loginResponse.getLoginResult();
		} catch (IOException ioe)
		{
			throw new RuntimeException(ioe);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return lr;
     }

    
    public static void copy(String in, OutputStream out) 
	   throws IOException {
	    synchronized (in) {
	      synchronized (out) {
	          byte[] buffer=in.getBytes();
	          int bytesRead = buffer.length;
	          out.write(buffer, 0, bytesRead);
	      }
	    }
	}

public String getAccessToken() {
    return mAccessToken;
}

public void setAccessToken(String token) {
    mAccessToken = token;
}

    
public String getSessionId() {
    return mSessionId;
}

public void setSessionId(String token) {
    mSessionId = token;
}

public String getServerURL() {
    return mServerURL;
}

public void setServerURL(String serverURL) {
    mServerURL = serverURL;
}

public long getSessionExpires() {
    return mSessionExpires;
}

public void setSessionExpires(long time) {
    mSessionExpires = time;
}

public void setSessionExpiresIn(String expiresIn) {
    if (expiresIn != null && !expiresIn.equals("0")) {
        setSessionExpires(System.currentTimeMillis()
                + Integer.parseInt(expiresIn) * 1000);
    }
}

public boolean isSessionIdValid() {
	return (getSessionId() != null);
}

public String getUserName(){
	return mUserName;
}

public String getPassword(){
	return mPassword;
}

public String getSecurityToken(){
	return mSecurityToken;
}

public String getEndPoint(){
	return mEndPoint;
}

public String getSoapServer(){
	return mSoapServer;
}

public void setUserName(String userName){
	mUserName=userName;
}

public void setPassword(String password){
	mPassword=password;
}
public void setSecurityToken(String securityToken){
	mSecurityToken=securityToken;
}

public void setEndPoint(String endPoint){
	mEndPoint=endPoint;
}

public void setSoapServer(String mServerURL){
	if (!(mSoapServer==null)){
		mSoapServer=(mServerURL.substring(9, mServerURL.indexOf("/services")));
	} else {
		mSoapServer=null;
	}
}
}
