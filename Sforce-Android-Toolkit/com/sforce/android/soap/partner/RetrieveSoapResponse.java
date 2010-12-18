package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

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
								currentSObject.setId(Id);
								currentSObject.setField(ID, Id);
							}
						} else if (!(currentSObject==null)){
							if (!(currentSObject.getType()==null)){
								String value=xpp.nextText();
								currentSObject.setField(name, value);										
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
