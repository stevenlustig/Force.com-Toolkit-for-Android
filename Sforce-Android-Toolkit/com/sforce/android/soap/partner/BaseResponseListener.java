package com.sforce.android.soap.partner;

import com.sforce.android.soap.partner.Salesforce.ResponseListener;
import com.sforce.android.soap.partner.fault.ApiFault;

public abstract class BaseResponseListener implements ResponseListener {
	public static final String SFORCE="Sforce";
	
	public abstract void onComplete(Object response);
	
	public abstract void onSforceError(ApiFault ApiFault);		
	
	public abstract void onException(Exception e);
}
