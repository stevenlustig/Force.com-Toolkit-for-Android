package com.sforce.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.ConnectorConfig;
import com.sforce.android.soap.partner.LoginResult;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.fault.ExceptionCode;

public class SforceLogin extends Activity {
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
		username = (TextView) this.findViewById(R.id.username);
		password = (TextView) this.findViewById(R.id.password);
		securityToken = (TextView) this.findViewById(R.id.securityToken);
		context = getApplicationContext();
		username.setText(this.getResources().getString(R.string.username)
				.toString());
		password.setText(getResources().getString(R.string.password).toString());
		securityToken.setText(getResources().getString(R.string.securityToken)
				.toString());
		Salesforce.init(context);
	}

	public void loginOnClick(View v) {
		ConnectorConfig parameters = new ConnectorConfig(username.getText().toString(), 
				password.getText().toString(), 
				securityToken.getText().toString());
		try {
			Salesforce.login(parameters, new LoginResponseListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class LoginResponseListener extends BaseResponseListener {
		@Override
		public void onComplete(Object sObjects) {
			LoginResult result = (LoginResult) sObjects;
			String id = result.getUserId();
			System.out.println("User id is:" + id);
			String orgId = result.getUserInfo().getOrganizationId();
			System.out.println("Org id is:" + orgId);
			Intent intent = new Intent();
			intent.setClass(context,
					com.sforce.android.sample.SforceDisplayLoginResult.class);
			intent.putExtra("loginResult", result.toString());
			startActivity(intent);
		}

		@Override
		public void onSforceError(ApiFault apiFault) {
			String msg = apiFault.getExceptionMessage();
			System.out.println("Error msg:" + msg);
			String code = apiFault.getExceptionCode().getValue();
			System.out.println("Error code:" + code);
			if (code.equals(ExceptionCode._INVALID_LOGIN)) {
				System.out.println("Invalid login");
			}
		}
	}
}
