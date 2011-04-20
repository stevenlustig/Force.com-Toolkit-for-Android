package com.sforce.android.sample;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SforceLogout  extends Activity {
	Context context;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		context=getApplicationContext();
		Salesforce.init(context);
    	Salesforce.logout(new LogoutResponseListener());
	}
	
    private class LogoutResponseListener extends BaseResponseListener{
    	private Handler mHandler = new Handler();
        public void onComplete(Object response) {
            mHandler.post(new Runnable() {
                public void run() {
	                Intent intent=new Intent();
	   		    	intent.setClass(context, com.sforce.android.sample.SforceThanks.class);
	   		    	startActivity(intent);                
                }
            });
        }
        
	      @Override
	      public void onSforceError(ApiFault apiFault){
	      	StringBuffer sb=new StringBuffer();
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
            mHandler.post(new Runnable() {
                public void run() {
	                Intent intent=new Intent();
	   		    	intent.setClass(context, com.sforce.android.sample.SforceThanks.class);
	   		    	intent.putExtra("exceptionMessage", display);
	   		    	startActivity(intent);                
                }
            });
        }
	      
	      @Override
	      public void onException(Exception e){
	      }	      

    }    
}
