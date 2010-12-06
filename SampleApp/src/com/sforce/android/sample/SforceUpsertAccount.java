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
import com.sforce.android.soap.partner.UpsertResult;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class SforceUpsertAccount extends Activity implements OnClickListener{
	TextView sObjectType;
	TextView type;
	TextView name;
	TextView numberOfEmployees;
	TextView fax;
	TextView externalId;
    TextView mText;
	Button upsertButton;
	Button doneButton;
	Context context;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.upsert_account_layout);
		setTitle("Sforce Toolkit Demo - Upsert Account");
		context=getApplicationContext();
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		type= (TextView)this.findViewById(R.id.type);
		type.setText("Account");
		externalId = (TextView)this.findViewById(R.id.externalId);
		name = (TextView)this.findViewById(R.id.name);
		numberOfEmployees = (TextView)this.findViewById(R.id.numberOfEmployees);
		fax = (TextView)this.findViewById(R.id.fax);
		upsertButton = (Button)this.findViewById(R.id.upsertButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
		upsertButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
		SObject obj = new SObject();
		obj.setType(type.getText().toString().trim());
        HashMap<String, String> requestFields=new HashMap<String, String>();
        requestFields.put("name", name.getText().toString());
        requestFields.put("numberOfEmployees", numberOfEmployees.getText().toString());
        requestFields.put("fax", fax.getText().toString());
        requestFields.put("FDIC_No__c", externalId.getText().toString());

        ArrayList<String> fields2Null = new ArrayList<String>();
        fields2Null.add("Ownership");
        obj.setFieldsToNull(fields2Null);
        
        obj.setFields(requestFields);
        ArrayList<SObject> objs = new ArrayList<SObject>();
        objs.add(obj);
        Salesforce.upsert("FDIC_No__c", objs, new UpsertResponseListener());
	}
    
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
    public class UpsertResponseListener extends BaseResponseListener{

        public void onComplete(final Object uresults) {
	    	ArrayList<UpsertResult> resultArray=(ArrayList<UpsertResult>) uresults;
	    	StringBuffer collateResults=new StringBuffer();
			for (UpsertResult ur: resultArray){
		    	if (ur.isSuccess()){
		    		if (!ur.isCreated()){
		    			collateResults.append("Record ").append(ur.getId()).append(" updated successfully.");
		    		} else {
		    			collateResults.append("Record ").append(ur.getId()).append(" created successfully.");
		    		}
		    	}else {
		    		collateResults.append("Record upsert failed");
					if (ur.getErrors()!=null){
						collateResults.append("Error Message: ").append(ur.getErrors().get(0).getMessage()).append("\n");
						collateResults.append("Status Code: ").append(ur.getErrors().get(0).getStatusCode().getValue()).append("\n");
					}

		    	}
			}
			final String display=collateResults.toString();
	    	SforceUpsertAccount.this.runOnUiThread(new Runnable() {
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
	      	SforceUpsertAccount.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }

  }
}