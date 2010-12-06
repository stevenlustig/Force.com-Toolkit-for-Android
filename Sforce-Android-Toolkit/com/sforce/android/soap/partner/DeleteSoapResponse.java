package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class DeleteSoapResponse implements Response { 
	ArrayList<DeleteResult> resultArray=new ArrayList<DeleteResult>();
	static final String DELETE_RESPONSE="deleteResponse";
	static final String ID="id";
	static final String RESULT="result";
	static final String SUCCESS="success";
	static final String ERRORS="errors";
	
	public DeleteSoapResponse() {
	}
	
	public DeleteSoapResponse(String response1) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			DeleteResult currentResult=new DeleteResult();
			Error currentError=new Error();
			int i=0;
			int eventType = xpp.getEventType();
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						System.out.println("start tag name="+name);
						if (name.equalsIgnoreCase(RESULT)){
							if (currentResult==null){
								currentResult=new DeleteResult();
							} 
						} else if(name.equalsIgnoreCase(ID)){
							if (!(currentResult==null)){
								String id=xpp.nextText();
								currentResult.setId(id);
							}
						} else if(name.equalsIgnoreCase(SUCCESS)){
							if (!(currentResult==null)){
								String success=xpp.nextText();
								currentResult.setSuccess(Boolean.parseBoolean(success));
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
						System.out.println("end tag name="+name);
						if (name.equalsIgnoreCase(RESULT)){
							resultArray.add(currentResult);
							currentResult=null;
						}else if (name.equalsIgnoreCase(ERRORS)){
							currentResult.setErrors(i, currentError);
							currentError=null;
						}else if (name.equalsIgnoreCase(DELETE_RESPONSE)){
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
	
	public ArrayList<DeleteResult> getResult() {
	    return this.resultArray;
	}
	
	public void setResult(ArrayList<DeleteResult> resultArray) {
	    this.resultArray = resultArray;
	}
		
	public Response getSoapResponse(){
		return this;
	}
}
