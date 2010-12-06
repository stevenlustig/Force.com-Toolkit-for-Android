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

public class SforceDisplayLoginResult extends Activity implements OnClickListener{
	Button doneButton;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.login_result_layout);
		setTitle("Sforce Toolkit Demo - Login Result");
		context=getApplicationContext();
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mText.setText(bundle.getString("loginResult"));
		doneButton.setOnClickListener(this);
	}
	
	public void onClick(View v) {
    	final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                Intent intent=new Intent();
   		    	intent.setClass(context, com.sforce.android.sample.SF8.class);
   		    	startActivity(intent);                
            }
        });
    }
}

