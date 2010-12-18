package com.sforce.android.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.OAuthConnectorConfig;
import com.sforce.android.soap.partner.OAuthLoginResult;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.fault.ExceptionCode;

public class SforceOAuthLogin extends Activity implements OnClickListener{
	String consumerKey=null;
	String callbackUrl=null;
	Button loginButton;
	Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.oauth_login_layout);
		setTitle("Sforce Toolkit Demo - OAuth Login");
		loginButton = (Button)this.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		context=getApplicationContext();
		consumerKey=(this.getResources().getString(R.string.consumerKey).toString());
		callbackUrl=(this.getResources().getString(R.string.callbackUrl).toString());
		Salesforce.init(context);
	}
	public void onClick(View v) {
		OAuthConnectorConfig parameters=new OAuthConnectorConfig(consumerKey, callbackUrl);
		try {
			Salesforce.loginOAuth(this, parameters, new LoginResponseListener());
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public class LoginResponseListener extends BaseResponseListener{
	      @Override
		  public void onComplete(Object sObjects) {
	    	  OAuthLoginResult result = (OAuthLoginResult) sObjects;
			  String id = result.getUserId();
			  setResult(RESULT_OK);
		      finish();
	      }

	      @Override
	      public void onSforceError(ApiFault apiFault){
		      	String msg = apiFault.getExceptionMessage();
		      	String code = apiFault.getExceptionCode().getValue();
		      	if (code.equals(ExceptionCode._ACCESS_DENIED))
		      	{	
		      		System.out.println("User didn't grant access");
		      	}
		  }
	  }
}
