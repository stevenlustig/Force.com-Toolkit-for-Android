package com.sforce.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sforce.android.soap.partner.ConnectorConfig;
import com.sforce.android.soap.partner.LoginResult;
import com.sforce.android.soap.partner.Salesforce;

public class SforceLogin extends Activity implements OnClickListener{
	TextView username;
	TextView password;
	TextView securityToken;
	Button loginButton;
	Context context;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.login_layout);
		setTitle("Sforce Toolkit Demo - Login");
		username = (TextView)this.findViewById(R.id.username);
		password = (TextView)this.findViewById(R.id.password);
		securityToken = (TextView)this.findViewById(R.id.securityToken);
		loginButton = (Button)this.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(this);
		context=getApplicationContext();
		username.setText(this.getResources().getString(R.string.username).toString());
		password.setText(getResources().getString(R.string.password).toString());
		securityToken.setText(getResources().getString(R.string.securityToken).toString());
		Salesforce.init(context);
	}
	public void onClick(View v) {
		ConnectorConfig parameters=new ConnectorConfig(username.getText().toString(), password.getText().toString(), securityToken.getText().toString());
		try {
	        final LoginResult lr=Salesforce.login(parameters);
	    	Handler mHandler = new Handler();
	        mHandler.post(new Runnable() {
	            public void run() {
	                Intent intent=new Intent();
	   		    	intent.setClass(context, com.sforce.android.sample.SforceDisplayLoginResult.class);
	   		    	intent.putExtra("loginResult", lr.toString());
	   		    	startActivity(intent);                
	            }
	        });
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}


