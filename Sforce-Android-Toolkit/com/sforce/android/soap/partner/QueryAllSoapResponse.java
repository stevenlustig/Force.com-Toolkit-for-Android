package com.sforce.android.soap.partner;

import java.io.IOException;
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.sforce.android.soap.partner.sobject.SObject;


public class QueryAllSoapResponse implements Response { 
	private QueryResult result=new QueryResult();
	static final String QUERY_ALL_RESPONSE="queryAllResponse";
	
	public QueryAllSoapResponse() {
	}
	
	public QueryAllSoapResponse(QueryResult result) {
	       this.result = result;
	}
	
	public QueryAllSoapResponse(String response1) {
		this.result = new QuerySoapResponse().parseQueryResponse(response1, QUERY_ALL_RESPONSE);
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

