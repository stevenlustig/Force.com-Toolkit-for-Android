package com.sforce.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SforceCreate extends Activity {
	Spinner recordTypeSpinner;
	Button doneButton;
	String recordType;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.select_layout);
		setTitle("Sforce Toolkit Demo - Select Record Type");
		context=getApplicationContext();
		doneButton = (Button)this.findViewById(R.id.doneButton);
		String[] recordTypes = getResources().getStringArray(R.array.recordTypes);
		recordTypeSpinner = (Spinner)this.findViewById(R.id.recordTypeSpinner);
		ArrayAdapter<CharSequence> adapter=new ArrayAdapter<CharSequence>(this, R.layout.main, recordTypes);
		recordTypeSpinner.setAdapter(adapter);
		OnItemSelectedListener oisl=new RecordTypeSelectedListener(this, adapter);
		recordTypeSpinner.setOnItemSelectedListener(oisl);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
	}
	
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
	
	public class RecordTypeSelectedListener implements OnItemSelectedListener{
		ArrayAdapter<CharSequence> mLocalAdapter;
        Activity mLocalContext;
        public RecordTypeSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {
          this.mLocalContext = c;
          this.mLocalAdapter = ad;
        }

        public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
        	String recordType=recordTypeSpinner.getSelectedItem().toString();
        	Intent intent=new Intent();
        	int requestCode=0;
        	if (recordType.equals("")){
        		// do nothing
        	} else if (recordType.equals("Contact")){ 
		    	intent.setClass(context, com.sforce.android.sample.SforceCreateContact.class);
		    	requestCode=0;
		    	startActivityForResult(intent, requestCode);
        	} if (recordType.equals("Account")){ 
		    	intent.setClass(context, com.sforce.android.sample.SforceCreateAccount.class);
		    	requestCode=1;
		    	startActivityForResult(intent, requestCode);
        	}
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    }
}

