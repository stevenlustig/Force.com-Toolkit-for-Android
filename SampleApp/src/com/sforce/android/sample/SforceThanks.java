package com.sforce.android.sample;

import android.app.Activity;
import android.os.Bundle;

public class SforceThanks extends Activity{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.logout_layout);
		setTitle("Sforce Toolkit Demo - End");
	}	
}
