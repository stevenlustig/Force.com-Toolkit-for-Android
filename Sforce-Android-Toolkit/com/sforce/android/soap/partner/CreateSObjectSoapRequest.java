package com.sforce.android.soap.partner;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.sforce.android.soap.partner.sobject.SObject;

public class CreateSObjectSoapRequest implements Request{
	static final String ENV="http://schemas.xmlsoap.org/soap/envelope/";
	static final String URN="urn:partner.soap.sforce.com";
	static final String SOAPENV="soapenv";
	static final String URN_STRING="urn";
	static final String URN1="urn:sobject.partner.soap.sforce.com";
	static final String URN1_STRING="urn1";
	static final String ENVELOPE="Envelope";
	static final String HEADER="Header";
	static final String SESSION_HEADER="SessionHeader";
	static final String SESSION_ID="sessionId";
	static final String CALLOPTIONS = "CallOptions";
	static final String CLIENT = "client";
	static final String BODY="Body";
	static final String CREATE="create";
	static final String SOBJECTS_STRING="sObjects";
	static final String TYPE="type";
	static final String REQUEST_TYPE="requestType";
	static final String RESPONSE_TYPE="responseType";
	static final String NAME="Name";
	static final String FIELDS_TO_NULL="fieldsToNull";	
	static final String NUMBER_OF_EMPLOYEES="NumberOfEmployees";
	static final String FAX="Fax";
	
	private String soapRequest=null;
	
	public CreateSObjectSoapRequest(ArrayList<SObject> records, HashMap<String, String> sessionFields) {
		this.soapRequest= createSoapRequest(records, sessionFields);
	}

	public String getRequest() {
		return this.soapRequest;
	}

	public void setRequest(String soapRequest) {
		this.soapRequest=soapRequest;
	}
	
	
	private String createSoapRequest(ArrayList<SObject> records, HashMap<String, String> sessionFields){
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
			serializer.text(sessionFields.get(SESSION_ID));
			serializer.endTag(URN, SESSION_ID);
			serializer.endTag(ENV, SESSION_HEADER);
			serializer.startTag(ENV, CALLOPTIONS);
			serializer.startTag(URN, CLIENT);
			serializer.text(sessionFields.get(CLIENT));
			serializer.endTag(URN, CLIENT);
			serializer.endTag(ENV, CALLOPTIONS);
			serializer.endTag(ENV, HEADER);
			serializer.startTag(ENV, BODY);
			serializer.startTag(URN, CREATE);
			
			Iterator<SObject> objs =records.iterator();
			while (objs.hasNext()){
				SObject obj=objs.next();
				serializer.startTag(URN, SOBJECTS_STRING);
				serializer.startTag(URN1, TYPE);
				serializer.text(obj.getType());
				serializer.endTag(URN1, TYPE);

				HashMap<String, String> requestFields = obj.getFields();
				Set<String> fields=requestFields.keySet();
				Iterator<String> itrFields=fields.iterator();

				while (itrFields.hasNext()){
					String fieldName=itrFields.next();
					if (!(fieldName.equals(SESSION_ID)) && (!(fieldName.equals(TYPE))) && (!(fieldName.equals(REQUEST_TYPE))) && (!(fieldName.equals(RESPONSE_TYPE)))){
						serializer.startTag(URN1, fieldName);
						serializer.text(requestFields.get(fieldName));
						serializer.endTag(URN1, fieldName);
					} 
				}

				ArrayList<String> fields2Null = obj.getFieldsToNull();
				if (fields2Null != null){	
			    	for (String f:fields2Null){
						serializer.startTag(URN1, FIELDS_TO_NULL);
						serializer.text(f);
						serializer.endTag(URN1, FIELDS_TO_NULL);			    		
			    	}
				}
				
				serializer.endTag(URN, SOBJECTS_STRING);
			}
			
			serializer.endTag(URN, CREATE);
			serializer.endTag(ENV, BODY);
			serializer.endTag(ENV, ENVELOPE);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}