package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class QueryMoreSoapResponse implements Response { 
	private QueryResult result=new QueryResult();
	static final String QUERY_MORE_RESPONSE="queryMoreResponse";
	
	public QueryMoreSoapResponse() {
	}
	
	public QueryMoreSoapResponse(QueryResult result) {
	       this.result = result;
	}
	
	public QueryMoreSoapResponse(String response1) {
		this.result = new QuerySoapResponse().parseQueryResponse(response1, QUERY_MORE_RESPONSE);
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

