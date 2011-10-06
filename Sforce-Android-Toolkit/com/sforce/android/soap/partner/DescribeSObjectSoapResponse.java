package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class DescribeSObjectSoapResponse implements Response { 
	private DescribeSObjectResult result=new DescribeSObjectResult();
	static final String RESULT = "result";
	static final String QUERY_RESPONSE="queryResponse";
	static final String SIZE="size";
	static final String ACTIVATEABLE="activateable";
	static final String CREATEABLE="createable";
	static final String CUSTOM="custom";
	static final String CUSTOMSETTING="customSetting";
	static final String DELETABLE="deletable";
	static final String DEPRECATEDANDHIDDEN="deprecatedAndHidden";
	static final String FEEDENABLED="feedEnabled";
	
	static final String FIELDS="fields";
		static final String DEFAULTVALUEFORMULA="defaultValueFormula";
		static final String PICKLISTVALUES="picklistValues";
			static final String VALUE="value";
			static final String DEFAULTVALUE="defaultValue";
	
	private Stack<String> parentChildrelationships = new Stack<String>();
	
	
	public DescribeSObjectSoapResponse() {
	}
	
	public DescribeSObjectSoapResponse(DescribeSObjectResult result) {
	       this.result = result;
	}
	
	public DescribeSObjectSoapResponse(String response1) {
			this.result = parseQueryResponse(response1, QUERY_RESPONSE);
	}

	public DescribeSObjectResult getResult() {
	    return result;
	}
	
	public void setResult(DescribeSObjectResult result) {
	    this.result = result;
	}
	
	
	public Response getSoapResponse() {
		return this;
	}
	
	public DescribeSObjectResult parseQueryResponse(String response1, String queryType)
	{
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(response1));
			int eventType = xpp.getEventType();
			String picklistCurrent = null;
			boolean picklistCurrentIsDefault = false;
			boolean done = false;
			SObject currentSObject=null;
			
			while (eventType != XmlPullParser.END_DOCUMENT && !done) {
				switch (eventType) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						//System.out.println("start tag name="+xpp.getName());
						
						String startTag = xpp.getName();
						parentChildrelationships.push(startTag);
						
						if (startTag.equalsIgnoreCase(FIELDS)) {
							currentSObject = new SObject();
						}
						
						break;
					case XmlPullParser.TEXT:
						String field = parentChildrelationships.pop();
						String value = xpp.getText();
						
						if (parentChildrelationships.peek().equalsIgnoreCase(RESULT)) {
							if (field.equalsIgnoreCase(SIZE)) {
								result.setSize(Integer.parseInt(value));
							} else if (field.equalsIgnoreCase(ACTIVATEABLE)) {
								result.setActivateable(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(CREATEABLE)) {
								result.setCreateable(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(CUSTOM)) {
								result.setCustom(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(CUSTOMSETTING)) {
								result.setCustomSetting(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(DELETABLE)) {
								result.setDeletable(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(DEPRECATEDANDHIDDEN)) {
								result.setDeprecatedAndHidden(Boolean.parseBoolean(value));
							} else if (field.equalsIgnoreCase(FEEDENABLED)) {
								result.setFeedEnabled(Boolean.parseBoolean(value));
							}
						}
						else if (parentChildrelationships.peek().equalsIgnoreCase(FIELDS)) {
							currentSObject.setField(field, value);
						}
						else if (parentChildrelationships.peek().equalsIgnoreCase(PICKLISTVALUES)) {
							if (field.equals(DEFAULTVALUE) && value.equalsIgnoreCase("true")) {
								picklistCurrentIsDefault = true;
								if (picklistCurrent != null) {
									currentSObject.setField(DEFAULTVALUEFORMULA, picklistCurrent);
								}
							}
							if (field.equals(VALUE)) {
								if (currentSObject.getField(PICKLISTVALUES) == null) {
									currentSObject.setField(PICKLISTVALUES, value);
								}
								else {
									currentSObject.setField(PICKLISTVALUES, currentSObject.getField(PICKLISTVALUES) + "||" + value);
								}
								
								picklistCurrent = value;
								if (picklistCurrentIsDefault == true) {
									currentSObject.setField(DEFAULTVALUEFORMULA, picklistCurrent);
								}
							}
						}
						
						xpp.next(); // Skip ending tag for TEXT item
						break;
					case XmlPullParser.END_TAG:
						//System.out.println("end tag name="+xpp.getName());
						
						parentChildrelationships.pop();
						
						String endTag = xpp.getName();
						if (endTag.equalsIgnoreCase(FIELDS)) {
							result.getRecords().add(currentSObject);
							currentSObject = null;
						}
						else if (endTag.equalsIgnoreCase(PICKLISTVALUES)) {
							picklistCurrent = null;
							picklistCurrentIsDefault = false;
						}
						else if (endTag.equalsIgnoreCase(queryType)) {
							done = true;
						}
						
						break;
				}
				
				eventType = xpp.next();
			}
			
			
		} catch (XmlPullParserException xppe) {
			throw new RuntimeException(xppe);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		
		return result;
	}
	
}