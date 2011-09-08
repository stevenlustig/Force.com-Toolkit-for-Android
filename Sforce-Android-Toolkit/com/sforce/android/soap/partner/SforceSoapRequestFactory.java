package com.sforce.android.soap.partner;

import java.util.ArrayList;
import java.util.HashMap;

import com.sforce.android.soap.partner.sobject.SObject;


public final class SforceSoapRequestFactory extends RequestFactory{

	public static Request getSoapRequest(HashMap<String, String> requestFields) {
		final String requestType=requestFields.get("requestType");
		if (requestType.equals("login")){
			return new LoginSoapRequest(requestFields);
		} else if (requestType.equals("describeSObject")){
			return new DescribeSObjectSoapRequest(requestFields);
		} else if (requestType.equals("query")){
			return new QuerySoapRequest(requestFields);
		} else if (requestType.equals("retrieve")){
			return new RetrieveSoapRequest(requestFields);
		} else if (requestType.equals("delete")){
			return new DeleteSoapRequest(requestFields);
		} else if (requestType.equals("queryMore")){
			return new QueryMoreSoapRequest(requestFields);
		} else if (requestType.equals("queryAll")){
			return new QueryAllSoapRequest(requestFields);
		} else if (requestType.equals("logout")){
			return new LogoutSoapRequest(requestFields);
		} else return null;
	}

	public static Request getSoapRequest(ArrayList<SObject> records, HashMap<String, String> sessionFields) {
		final String requestType=sessionFields.get("requestType");
		if (requestType.equals("createSObject")){
			return new CreateSObjectSoapRequest(records, sessionFields);
		} else if (requestType.equals("updateSObject")){
			return new UpdateSObjectSoapRequest(records, sessionFields);
		} else if (requestType.equals("upsertSObject")){
			return new UpsertSObjectSoapRequest(records, sessionFields);
		} else return null;
	}
}
