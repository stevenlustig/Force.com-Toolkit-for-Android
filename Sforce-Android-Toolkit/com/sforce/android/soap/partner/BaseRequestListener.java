package com.sforce.android.soap.partner;

import com.sforce.android.soap.partner.AsyncSforce.RequestListener;
import com.sforce.android.soap.partner.Salesforce.ResponseListener;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.fault.SforceSoapFaultFactory;

import android.os.Bundle;
import android.util.Log;

public abstract class BaseRequestListener implements RequestListener {
	private static final String SFORCE="Sforce";
	private ResponseListener listener;
	
	public abstract void onComplete(Object response);
		
	public void onSforceError(String faultType, Response response) {
		ApiFault apiFault=SforceSoapFaultFactory.getSoapFault(faultType, response);
        listener.onSforceError(apiFault);
    }    

	public void onException(Exception e) {
		Log.e(SFORCE, e.getMessage());
		Bundle bundle=new Bundle();
		bundle.putString("exceptionMessage", e.getMessage());
	}
	
	public ResponseListener getResponseListener(){
		return listener;
	}
	
	public void setResponseListener(ResponseListener listener){
		this.listener=listener;
	}
}
