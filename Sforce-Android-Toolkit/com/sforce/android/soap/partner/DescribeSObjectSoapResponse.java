package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Stack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class DescribeSObjectSoapResponse implements Response { 
	private DescribeSObjectResult result=new DescribeSObjectResult();
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
	
	
	private Stack parentChildrelationships = new Stack();
	
	
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
	
	
	public Response getSoapResponse(){
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
			boolean done = false;
			SObject currentSObject=null;
			boolean inRecord=false;
			String ignoringTagsUntil = null;
			
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						name = xpp.getName();
						//System.out.println("start tag name="+name);
						if (!inRecord && ignoringTagsUntil == null) {
							if (name.equalsIgnoreCase(FIELDS)) {
								currentSObject=new SObject();
								inRecord=true;
							} else if (name.equalsIgnoreCase(SIZE)) {
								result.setSize(Integer.parseInt(xpp.nextText()));
							} else if (name.equalsIgnoreCase(ACTIVATEABLE)) {
								result.setActivateable(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(CREATEABLE)) {
								result.setCreateable(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(CUSTOM)) {
								result.setCustom(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(CUSTOMSETTING)) {
								result.setCustomSetting(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(DELETABLE)) {
								result.setDeletable(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(DEPRECATEDANDHIDDEN)) {
								result.setDeprecatedAndHidden(Boolean.parseBoolean(xpp.nextText()));
							} else if (name.equalsIgnoreCase(FEEDENABLED)) {
								result.setFeedEnabled(Boolean.parseBoolean(xpp.nextText()));
							}
						} else if (!(currentSObject==null)) {
							if (xpp.getAttributeCount()==0)
							{
								if (inRecord && ignoringTagsUntil == null) {
									int eventType2 = xpp.next();
									
									if (eventType2 == XmlPullParser.TEXT) { // Field
										String value=xpp.getText();
										currentSObject.setField(getRelationshipPrefix(parentChildrelationships)+name , value);
									}
									else if (eventType2 == XmlPullParser.START_TAG) { // Sub-tags...ignore them
										ignoringTagsUntil = xpp.getName();
									}
								}
							}
							else 
							{
								if (xpp.getAttributeValue(null, "type") != null &&
									xpp.getAttributeValue(null, "type").contains("sObject")	)
									parentChildrelationships.push(xpp.getName());											
							}
						
						}
					break;
					case XmlPullParser.END_TAG:
						name = xpp.getName().trim();
						//System.out.println("end tag name="+name);
						
						if (ignoringTagsUntil != null && name.equals(ignoringTagsUntil)) {
							ignoringTagsUntil = null;
						}						
						else if (name.equalsIgnoreCase(FIELDS)) {
							if (currentSObject != null) {
								result.getRecords().add(currentSObject);
								inRecord=false;
							}
						}
						else if (name.equalsIgnoreCase(queryType)) {
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
		
		return result;
	}
	
	public static String getRelationshipPrefix(Stack parentChildRels)
	{
		if (parentChildRels.isEmpty())
			return "";
		
		String prefix = "";
		for (Enumeration e = parentChildRels.elements() ; e.hasMoreElements() ;) {
			prefix += (String)e.nextElement() + ".";
		}
		return prefix;
	}
}