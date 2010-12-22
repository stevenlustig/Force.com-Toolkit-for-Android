package com.sforce.android.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class SforceRetrieve extends Activity implements OnClickListener{
	TextView fieldList;
	TextView sObjectType;
	TextView ids;
	Button retrieveButton;
	Button doneButton;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.retrieve_layout);
		setTitle("Sforce Toolkit Demo - Retrieve");
		context=getApplicationContext();
		fieldList = (TextView)this.findViewById(R.id.fieldList);
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		ids = (TextView)this.findViewById(R.id.ids);
		retrieveButton = (Button)this.findViewById(R.id.retrieveButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
		Button picButton = (Button)this.findViewById(R.id.picButton);
        mText = (TextView) this.findViewById(R.id.txt);
		retrieveButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		picButton.setOnClickListener(new PictureButtonClickListener());
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
    	Salesforce.retrieve(fieldList.getText().toString().split(","), sObjectType.getText().toString(), 
				ids.getText().toString().split(","), new RetrieveResponseListener());
	}
    
	public class PictureButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent();
	    	intent.setClass(context, com.sforce.android.sample.PicSample.class);
	    	startActivity(intent);
		}
	}

	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
    public class RetrieveResponseListener extends BaseResponseListener{

        public void onComplete(final Object rresults) {
	    	ArrayList<SObject> records=(ArrayList<SObject>) rresults;
	    	StringBuffer collateRecords=new StringBuffer();
	    	if (records.size()==0){
	    		collateRecords.append("No records rerieved. Please specify correct Id value(s).");
	    	} else {
		    	for (SObject hm:records){
			        Editor editor = getApplicationContext().getSharedPreferences("SObject-Id", Context.MODE_PRIVATE).edit();
			        editor.putString("Id", hm.getField("Id"));
			        editor.commit();
		    		collateRecords.append(hm.toString());
		    	}
	    	}
	    	final String display=collateRecords.toString();
	    	SforceRetrieve.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
        }
        
	      @Override
	      public void onSforceError(ApiFault apiFault){
	      	StringBuffer sb=new StringBuffer();
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
	      	SforceRetrieve.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }

}
}