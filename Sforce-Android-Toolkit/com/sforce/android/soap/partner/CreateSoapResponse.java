package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class CreateSoapResponse implements Response { 
	private ArrayList<SaveResult> resultArray=new ArrayList<SaveResult>();
	static final String CREATE_RESPONSE="createResponse";
	static final String ID="id";
	static final String RESULT="result";
	static final String SUCCESS="success";
	static final String ERRORS="errors";
	
	public CreateSoapResponse() {
	}
	
	public CreateSoapResponse(String response1) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			SaveResult currentResult=new SaveResult();
			Error currentError=new Error();
			int eventType = xpp.getEventType();
			int i=0;
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						//System.out.println("start tag name="+name);
						if (name.equalsIgnoreCase(RESULT)){
							if (currentResult==null){
								currentResult=new SaveResult();
							} 
						} else if(name.equalsIgnoreCase(ID)){
							if (!(currentResult==null)){
								String id=xpp.nextText();
								currentResult.setId(id);
							}
						} else if(name.equalsIgnoreCase(SUCCESS)){
							if (!(currentResult==null)){
								currentResult.setSuccess(Boolean.parseBoolean(xpp.nextText()));
							}
						} else if(name.equalsIgnoreCase("errors")){
							if (currentError==null){
								currentError=new Error();
								(currentResult.getErrors()).add(currentError);
								i=currentResult.getErrors().size()-1;
							}
						} else if(name.equalsIgnoreCase("message")){
							if (currentError!=null){
								currentError.setMessage(xpp.nextText());
							}
						} else if(name.equalsIgnoreCase("statusCode")){
							if (currentError!=null){
								currentError.setStatusCode(new StatusCode(xpp.nextText()));
							}
						}
					break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						//System.out.println("end tag name="+name);
						if (name.equalsIgnoreCase(RESULT)){
							resultArray.add(currentResult);
							currentResult=null;
						}else if (name.equalsIgnoreCase(ERRORS)){
							currentResult.setErrors(i, currentError);
							currentError=null;
						}else if (name.equalsIgnoreCase(CREATE_RESPONSE)){
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
	public ArrayList<SaveResult> getResult() {
	    return this.resultArray;
	}
	
	public void setResult(ArrayList<SaveResult> resultArray) {
	    this.resultArray = resultArray;
	}
	
	public Response getSoapResponse(){
		return this;
	}
}
