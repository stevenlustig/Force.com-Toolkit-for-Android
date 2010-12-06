package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class LogoutSoapResponse implements Response{
	static final String LOGOUT_RESPONSE="logoutResponse";
	boolean result=false;
	public LogoutSoapResponse(){
	}

	public LogoutSoapResponse(String responseXML){
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
	         
			xpp.setInput(new StringReader(responseXML));
			int eventType = xpp.getEventType();
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(LOGOUT_RESPONSE)){
							if(xpp.nextText().equals("")){
								result=true;
							}
						} 
						break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(LOGOUT_RESPONSE)){
							done = true;
						}
						break;
				}
				eventType = xpp.next();
			}
		}catch(XmlPullParserException xppe){
			xppe.printStackTrace();
			System.out.println(""+xppe);
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.out.println(""+ioe);
		}
		System.out.println("Ended LogoutResponse Normally.");
	}
	
	public boolean getResult(){
		return this.result;
	}
	
	public void setResult(boolean result){
		this.result=result;
	}
	
	public Response getSoapResponse(){
		return this;
	}
}
