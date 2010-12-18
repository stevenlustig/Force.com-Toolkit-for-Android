package com.sforce.android.sample;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class SforceCreateAccount extends Activity implements OnClickListener{
	TextView sObjectType;
	TextView type;
	TextView name;
	TextView numberOfEmployees;
	TextView fax;
    TextView mText;
	Button createButton;
	Button doneButton;
	Button updtButton;
	Context context;
	OnClickListener ocl;
	String accountId;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.create_account_layout);
		setTitle("Sforce Toolkit Demo - Create Contact");
		context=getApplicationContext();
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		type= (TextView)this.findViewById(R.id.type);
		type.setText("Account");
		name = (TextView)this.findViewById(R.id.name);
		numberOfEmployees = (TextView)this.findViewById(R.id.numberOfEmployees);
		fax = (TextView)this.findViewById(R.id.fax);
		createButton = (Button)this.findViewById(R.id.createButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
		updtButton = (Button)this.findViewById(R.id.updtButton);
        mText = (TextView) this.findViewById(R.id.txt);
		createButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		updtButton.setOnClickListener(new UpdtButtonClickListener());
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
		SObject obj = new SObject();
		obj.setType(type.getText().toString().trim());
        HashMap<String, String> requestFields=new HashMap<String, String>();
        if ((name.getText().toString()!=null)&&(!(name.getText().toString().equals("")))){
        	requestFields.put("name", (name.getText().toString()).trim());
        }
        if ((numberOfEmployees.getText().toString()!=null)&&(!(numberOfEmployees.getText().toString().equals("")))){
        	requestFields.put("numberOfEmployees", (numberOfEmployees.getText().toString()).trim());
        }
        if ((fax.getText().toString()!=null)&&(!(fax.getText().toString().equals("")))){
        	requestFields.put("fax", (fax.getText().toString()).trim());
        }
        obj.setFields(requestFields);
        ArrayList<SObject> objs = new ArrayList<SObject>();
        objs.add(obj);
        
        Salesforce.create(objs , new CreateResponseListener());
	}

	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}

	public class UpdtButtonClickListener implements OnClickListener{
		public void onClick(View v) {
	    	Intent intent=new Intent();
	    	intent.setClass(context, com.sforce.android.sample.SforceUpdateAccount.class);
	    	intent.putExtra("type", type.getText().toString().trim());
	    	intent.putExtra("Name", name.getText().toString().trim());
	    	intent.putExtra("NumberOfEmployees", numberOfEmployees.getText().toString().trim());
	    	intent.putExtra("Fax", fax.getText().toString().trim());
	    	intent.putExtra("Id", accountId);
	    	startActivityForResult(intent, 1);
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
					accountId = sr.getId();
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
	    	SforceCreateAccount.this.runOnUiThread(new Runnable() {
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
	      	SforceCreateAccount.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	  }
}
