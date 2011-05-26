package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class RetrieveSoapResponse implements Response { 
	private ArrayList<SObject> records=new ArrayList<SObject>();
	static final String RETRIEVE_RESPONSE="retrieveResponse";
	static final String TYPE="type";
	static final String ID="Id";
	static final String RESULT="result";
	private Stack parentChildrelationships = new Stack();
	
	public RetrieveSoapResponse() {
	}
	
	public RetrieveSoapResponse(String response1) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			int eventType = xpp.getEventType();
			boolean done = false;
			SObject currentSObject=null;

			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(RESULT)){
							if (currentSObject==null){
								currentSObject=new SObject();
							}
						} else if(name.equalsIgnoreCase(TYPE)){
							if (!(currentSObject==null)){
								String type=xpp.nextText();
								currentSObject.setType(type);
							}
						} else if(name.equalsIgnoreCase(ID)){
							if (!(currentSObject==null)){
								String Id=xpp.nextText();
								if (Id != null && Id != "null" && Id != "")
								{
									currentSObject.setField(QuerySoapResponse.getRelationshipPrefix(parentChildrelationships)+ID, Id);										
								}
							}
						} else if (!(currentSObject==null)){
							if (!(currentSObject.getType()==null)){
								if (xpp.getAttributeCount()==0)
								{	
									String value=xpp.nextText();
									currentSObject.setField(QuerySoapResponse.getRelationshipPrefix(parentChildrelationships)+name , value);										
								}
								else 
								{
									if (xpp.getAttributeValue(null, "type") != null &&
										xpp.getAttributeValue(null, "type").contains("sObject")	)
										parentChildrelationships.push(xpp.getName());											
								}
							}
						}
					break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(RESULT)){
							if (currentSObject.getType()!=null){
								records.add(currentSObject);
								currentSObject=null;
							}
						} else if (name.equalsIgnoreCase(RETRIEVE_RESPONSE)){
							done = true;
						}
						else if (!parentChildrelationships.isEmpty()) 
						{
							String lastRel = (String)parentChildrelationships.peek();
							if (name.equalsIgnoreCase(lastRel))
								parentChildrelationships.pop();
						}
						break;
			}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException xppe)
		{
			throw new RuntimeException(xppe);
		} catch (IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
	}
	public ArrayList<SObject> getResult() {
	    return records;
	}
	
	public void setResult(ArrayList<SObject> records) {
	    this.records = records;
	}
	
	public Response getSoapResponse(){
		return this;
	}	
}
