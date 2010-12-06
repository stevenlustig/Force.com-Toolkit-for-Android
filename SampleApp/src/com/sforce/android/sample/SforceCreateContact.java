package com.sforce.android.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.SaveResult;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class SforceCreateContact extends Activity implements OnClickListener{
	TextView sObjectType;
	TextView type;
	TextView accountId;
	TextView firstName;
	TextView lastName;
    TextView mText;
    
	Button createButton;
	Button doneButton;
	Context context;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.create_contact_layout);
		setTitle("Sforce Toolkit Demo - Create Contact");
		context=getApplicationContext();
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		type= (TextView)this.findViewById(R.id.type);
		type.setText("Contact");
        accountId = (TextView)this.findViewById(R.id.accountId);
       	firstName = (TextView)this.findViewById(R.id.firstName);
       	lastName = (TextView)this.findViewById(R.id.lastName);
		createButton = (Button)this.findViewById(R.id.createButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
		createButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		Salesforce.init(context);
	}

	public void onClick(View v) {
		SObject obj = new SObject();
		obj.setType(type.getText().toString().trim());
        HashMap<String, String> requestFields=new HashMap<String, String>();
        if ((accountId.getText().toString()!=null)&&(!(accountId.getText().toString().equals("")))){
        	requestFields.put("accountId", accountId.getText().toString());
        }
        if ((firstName.getText().toString()!=null)&&(!(firstName.getText().toString().equals("")))){
        	requestFields.put("firstName", firstName.getText().toString());
        }
        if ((lastName.getText().toString()!=null)&&(!(lastName.getText().toString().equals("")))){
        	requestFields.put("lastName", lastName.getText().toString());
        }
        obj.setFields(requestFields);
        ArrayList<SObject> objs = new ArrayList<SObject>();
        objs.add(obj);
    	Salesforce.create(objs, new CreateResponseListener());
	}
	
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}

    public class CreateResponseListener extends BaseResponseListener{
	    @Override
        public void onComplete(final Object cresults) {
	    	ArrayList<SaveResult> resultArray=(ArrayList<SaveResult>) cresults;
			StringBuffer collateResults=new StringBuffer();
			for (SaveResult sr: resultArray){
				if (sr.isSuccess()){
					collateResults=collateResults.append("Record ").append(sr.getId()).append(" created successfully.\n");
				}else{
					collateResults=collateResults.append("Record ").append(sr.getId()).append(" creation failed.\n");
					if (sr.getErrors()!=null){
						collateResults.append("Error Message: ").append(sr.getErrors().get(0).getMessage()).append("\n");
						collateResults.append("Status Code: ").append(sr.getErrors().get(0).getStatusCode().getValue()).append("\n");
					}
				}
			}
	    	final String display=collateResults.toString();
	    	SforceCreateContact.this.runOnUiThread(new Runnable() {
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
	      	SforceCreateContact.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	  }
}
