package com.sforce.android.soap.partner;

import java.io.StringWriter;
import java.util.HashMap;
import org.xmlpull.v1.XmlSerializer;


import android.util.Xml;

public class RetrieveSoapRequest implements Request{
	static final String ENV="http://schemas.xmlsoap.org/soap/envelope/";
	static final String URN="urn:partner.soap.sforce.com";
	static final String SOAPENV="soapenv";
	static final String URN_STRING="urn";
	static final String ENVELOPE="Envelope";
	static final String HEADER="Header";
	static final String SESSION_HEADER="SessionHeader";
	static final String SESSION_ID="sessionId";
	static final String CALLOPTIONS = "CallOptions";
	static final String CLIENT = "client";
	static final String BODY="Body";
	static final String RETRIEVE="retrieve";
	static final String FIELD_LIST="fieldList";
	static final String SOBJECT_TYPE="sObjectType";
	static final String IDS="ids";
	static final String SEPARATOR=",";
	
	private String soapRequest=null;
	
	public RetrieveSoapRequest(HashMap<String, String> requestFields) {
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
			serializer.startTag(ENV, SESSION_HEADER);
			serializer.startTag(URN, SESSION_ID);
			serializer.text(requestFields.get(SESSION_ID));
			serializer.endTag(URN, SESSION_ID);
			serializer.endTag(ENV, SESSION_HEADER);
			serializer.startTag(ENV, CALLOPTIONS);
			serializer.startTag(URN, CLIENT);
			serializer.text(requestFields.get(CLIENT));
			serializer.endTag(URN, CLIENT);
			serializer.endTag(ENV, CALLOPTIONS);
			serializer.endTag(ENV, HEADER);
			serializer.startTag(ENV, BODY);
			serializer.startTag(URN, RETRIEVE);
			serializer.startTag(URN, FIELD_LIST);
			serializer.text(requestFields.get(FIELD_LIST));
			serializer.endTag(URN, FIELD_LIST);
			serializer.startTag(URN, SOBJECT_TYPE);
			serializer.text(requestFields.get(SOBJECT_TYPE));
			System.out.println("sObjectType="+requestFields.get(SOBJECT_TYPE));
			serializer.endTag(URN, SOBJECT_TYPE);
			
			String[] fields = (requestFields.get(IDS)).split(SEPARATOR);
			for (String id:fields){
				serializer.startTag(URN, IDS);
				serializer.text(id.trim());
				serializer.endTag(URN, IDS);
			}
			
			serializer.endTag(URN, RETRIEVE);
			serializer.endTag(ENV, BODY);
			serializer.endTag(ENV, ENVELOPE);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
