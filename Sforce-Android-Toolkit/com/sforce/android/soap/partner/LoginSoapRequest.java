package com.sforce.android.soap.partner;

import java.io.StringWriter;
import java.util.HashMap;
import org.xmlpull.v1.XmlSerializer;


import android.util.Xml;

public class LoginSoapRequest implements Request{
	static final String ENV="http://schemas.xmlsoap.org/soap/envelope/";
	static final String URN="urn:partner.soap.sforce.com";
	static final String SOAPENV="soapenv";
	static final String URN_STRING="urn";
	static final String ENVELOPE="Envelope";
	static final String HEADER="Header";
	static final String BODY="Body";
	static final String LOGIN="login";
	static final String USERNAME="username";
	static final String PASSWORD="password";
	static final String SECURITY_TOKEN="securityToken";
	
	private String soapRequest=null;
	
	public LoginSoapRequest(HashMap<String, String> requestFields){
		this.soapRequest=createSoapRequest(requestFields);
	}

	public String getRequest() {
		return this.soapRequest;
	}

	public void setRequest(String soapRequest) {
		this.soapRequest=soapRequest;
	}
	
	private String createSoapRequest(HashMap<String, String> requestFields){
    	XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);				    	
			serializer.setPrefix(SOAPENV, ENV);
			serializer.setPrefix(URN_STRING, URN);
			serializer.startTag(ENV, ENVELOPE);
			serializer.startTag(ENV, HEADER);
			serializer.endTag(ENV, HEADER);
			serializer.startTag(ENV, BODY);
			serializer.startTag(URN,LOGIN);
			serializer.startTag(URN,USERNAME);
		    serializer.text(requestFields.get(USERNAME));
		    serializer.endTag(URN, USERNAME);
		    serializer.startTag(URN, PASSWORD);
		    serializer.text(requestFields.get(PASSWORD));
		    String secToken = requestFields.get(SECURITY_TOKEN);
		    if (secToken != null && !secToken.trim().equals(""))
		    	serializer.text(requestFields.get(SECURITY_TOKEN));
		    serializer.endTag(URN, PASSWORD);
		    serializer.endTag(URN, LOGIN);
			serializer.endTag(ENV, BODY);
			serializer.endTag(ENV, ENVELOPE);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
