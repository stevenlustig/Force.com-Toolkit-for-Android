package com.sforce.android.soap.partner;

import static com.sforce.android.soap.partner.SforceConstants.RESPONSE_TYPE;
import static com.sforce.android.soap.partner.SforceConstants.SOAP_RESPONSE;
import android.os.Bundle;

import com.sforce.android.soap.partner.fault.FaultSoapResponse;

public final class SforceSoapResponseFactory extends ResponseFactory{
	private static final String TAG = SforceSoapResponseFactory.class.getName();
	
	public static Response getSoapResponse(Bundle bundle){
		
		final String responseType=bundle.getString(RESPONSE_TYPE);
		System.out.println("ResponseType="+responseType);
		final String response=bundle.getString(SOAP_RESPONSE);
		System.out.println("response="+response);
		if (responseType.equals("login")){
			return new LoginSoapResponse(response);
		} else if (responseType.equals("describeSObject")){
			return new DescribeSObjectSoapResponse(response);
		} else if (responseType.equals("query")){
			return new QuerySoapResponse(response);
		} else if (responseType.equals("retrieve")){
			return new RetrieveSoapResponse(response);
		} else if (responseType.equals("create")){
			return new CreateSoapResponse(response);
		} else if (responseType.equals("update")){
			return new UpdateSoapResponse(response);
		} else if (responseType.equals("upsert")){
			return new UpsertSoapResponse(response);
		} else if (responseType.equals("delete")){
			return new com.sforce.android.soap.partner.DeleteSoapResponse(response);
		} else if (responseType.equals("queryMore")){
			return new QueryMoreSoapResponse(response);
		} else if (responseType.equals("queryAll")){
			return new QueryAllSoapResponse(response);
		} else if (responseType.equals("logout")){
			return new LogoutSoapResponse(response);
		} else if (responseType.equals("fault")){
			return new FaultSoapResponse(response);
		} else return null;
	}
}
