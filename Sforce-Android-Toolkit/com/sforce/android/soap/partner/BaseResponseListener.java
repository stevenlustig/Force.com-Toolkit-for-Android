package com.sforce.android.soap.partner;

import com.sforce.android.soap.partner.Salesforce.ResponseListener;
import com.sforce.android.soap.partner.fault.ApiFault;

import android.os.Bundle;
import android.util.Log;

public abstract class BaseResponseListener implements ResponseListener {
	public static final String SFORCE="Sforce";
	
	public abstract void onComplete(Object response);
	
	public abstract void onSforceError(ApiFault ApiFault);		
	
	public void onException(Exception e) {
		Log.e(SFORCE, e.getMessage());
	}
}
