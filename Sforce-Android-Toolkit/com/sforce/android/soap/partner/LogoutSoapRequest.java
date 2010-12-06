package com.sforce.android.soap.partner;

import java.io.StringWriter;
import java.util.HashMap;
import org.xmlpull.v1.XmlSerializer;


import android.util.Xml;

public class LogoutSoapRequest implements Request{
	static final String ENV="http://schemas.xmlsoap.org/soap/envelope/";
	static final String URN="urn:partner.soap.sforce.com";
	static final String SOAPENV="soapenv";
	static final String URN_STRING="urn";
	static final String ENVELOPE="Envelope";
	static final String HEADER="Header";
	static final String SESSION_HEADER="SessionHeader";
	static final String SESSION_ID="sessionId";
	static final String BODY="Body";
	static final String LOGOUT="logout";
	
	private String soapRequest=null;
	
	public LogoutSoapRequest(HashMap<String, String> requestFields) {
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
			serializer.startTag(URN, SESSION_HEADER);
			serializer.startTag(URN, SESSION_ID);
			serializer.text(requestFields.get(SESSION_ID));
			serializer.endTag(URN, SESSION_ID);
			serializer.endTag(URN, SESSION_HEADER);
			serializer.endTag(ENV, HEADER);
			serializer.startTag(ENV, BODY);
		    serializer.startTag(URN, LOGOUT);
		    serializer.endTag(URN, LOGOUT);
			serializer.endTag(ENV, BODY);
			serializer.endTag(ENV, ENVELOPE);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
