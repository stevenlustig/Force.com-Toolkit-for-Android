package com.sforce.android.soap.partner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.fault.ExceptionCode;

public class OAuthFaultResponse implements Response {

	private ApiFault fault;
	
	public OAuthFaultResponse(String response)
	{
		String[] errors = response.split("&");
		ExceptionCode code = ExceptionCode.fromString(errors[0].split("=")[1]);
		
		String desc = null;
		if (errors.length > 1)
		{	
			try
			{
				desc = URLDecoder.decode(errors[1].split("=")[1], "UTF-8");
			}
			catch(UnsupportedEncodingException e){}
		}	
		fault = new ApiFault(code, desc);
	}

	public ApiFault getApiFault() {
	    return fault;
	}
	
	public Response getSoapResponse() {
		return this;
	}

}
