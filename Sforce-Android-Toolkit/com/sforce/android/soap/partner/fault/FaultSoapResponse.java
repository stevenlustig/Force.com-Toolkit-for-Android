package com.sforce.android.soap.partner.fault;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.Response;

public class FaultSoapResponse implements Response { 
	private FaultResult result=new FaultResult();
	static final String FAULT="Fault";
	static final String FAULT_STRING="faultString";
	static final String FAULT_CODE="faultCode";
	static final String DETAIL="detail";
	static final String EXCEPTION_CODE="exceptionCode";
	static final String EXCEPTION_MESSAGE="exceptionMessage";
	static final String ROW="row";
	static final String COLUMN="column";
	
	public FaultSoapResponse() {
	}
	
	public FaultSoapResponse(FaultResult result) {
	       this.result = result;
	}
	
	public FaultSoapResponse(String response1) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			int eventType = xpp.getEventType();
			FaultResult currentResult=new FaultResult();
			FaultDetail faultDetail=null;
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(FAULT_CODE)){
							result.setFaultCode(xpp.nextText());
						} else if (name.equalsIgnoreCase(FAULT_STRING)){
							String fs=xpp.nextText();
							result.setFaultString(fs);
						} else if (name.equalsIgnoreCase(DETAIL)){
							faultDetail=new FaultDetail();
							result.setFaultDetail(faultDetail);
						} else if (name.equalsIgnoreCase(EXCEPTION_CODE)){
							//ExceptionCode ec=new ExceptionCode(xpp.nextText());
							faultDetail.setExceptionCode(xpp.nextText());
						} else if(name.equalsIgnoreCase(EXCEPTION_MESSAGE)){
							faultDetail.setExceptionMessage(xpp.nextText());
						} else if(name.equalsIgnoreCase(ROW)){
							faultDetail.setRow(xpp.nextText());
						} else if(name.equalsIgnoreCase(COLUMN)){
							faultDetail.setColumn(xpp.nextText());
						} 
					break;
					case XmlPullParser.END_TAG:
						name = xpp.getName();
						if (name.equalsIgnoreCase(DETAIL)){
						} else if (name.equalsIgnoreCase(FAULT)){
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
	
	public FaultResult getResult() {
	    return result;
	}
	
	public void setResult(FaultResult result) {
	    this.result = result;
	}

	public Response getSoapResponse(){
		return this;
	}
}
