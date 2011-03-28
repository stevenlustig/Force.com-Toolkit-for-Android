package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class QuerySoapResponse implements Response { 
	private QueryResult result=new QueryResult();
	static final String DONE="done";
	static final String RECORDS="records";
	static final String SFNAME="Name";
	static final String SFBILLINGSTREET="BillingStreet";
	static final String QUERY_RESPONSE="queryResponse";
	static final String TYPE="type";
	static final String ID="Id";
	static final String SIZE="size";
	static final String QUERY_LOCATOR="queryLocator";
	static final String RESULT="result";
	static final String FAULTSTRING="faultstring";
	
	
	public QuerySoapResponse() {
	}
	
	public QuerySoapResponse(QueryResult result) {
	       this.result = result;
	}
	
	public QuerySoapResponse(String response1) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			int eventType = xpp.getEventType();
			boolean done = false;
			SObject currentSObject=null;
			boolean inRecord=false;
			String prevType=null;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						System.out.println("start tag name="+name);
						if (name.equalsIgnoreCase(DONE)){
							if (!inRecord){
								result.setDone(Boolean.parseBoolean(xpp.nextText()));
							}
						} else if (name.equalsIgnoreCase(QUERY_LOCATOR)){
							if (!inRecord){
								String ql=xpp.nextText();
								result.setQueryLocator(ql);
							}
						} else if (name.equalsIgnoreCase(SIZE)){
							if (!inRecord){
								result.setSize(Integer.parseInt(xpp.nextText()));
							}
						} else if (name.equalsIgnoreCase(RECORDS)){
							if (!inRecord){
								currentSObject=new SObject();
								inRecord=true;
							}
						} else if(name.equalsIgnoreCase(TYPE)){
							if (inRecord){
								String type=xpp.nextText();
								if ((prevType==null)|| (type.equals(prevType))){
									currentSObject.setType(type);
									prevType=type;
								}
							}
						} else if(name.equalsIgnoreCase(ID)){
							if (inRecord){
								if (prevType.equals(currentSObject.getType())){
									String Id=xpp.nextText();
									currentSObject.setId(Id);
									currentSObject.setField(ID, Id);
								}
							}
						} else if (!(currentSObject==null)){
								if (xpp.getPrefix().equals("sf") && (xpp.getAttributeCount()==0)){
									if ((inRecord) && (prevType.equals(currentSObject.getType()))) {
										String value=xpp.nextText();
										currentSObject.setField(name, value);										
									}
								}
						} 
					break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						System.out.println("end tag name="+name);
						if (name.equalsIgnoreCase(RECORDS)){
							if (currentSObject.getType()!=null){
								result.getRecords().add(currentSObject);
								inRecord=false;
							}
						}
						else if (name.equalsIgnoreCase(QUERY_RESPONSE)){
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

	public QueryResult getResult() {
	    return result;
	}
	
	public void setResult(QueryResult result) {
	    this.result = result;
	}
	
	
	public Response getSoapResponse(){
		return this;
	}
}