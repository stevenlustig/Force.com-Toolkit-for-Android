package com.sforce.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SforceUpdate extends Activity implements OnClickListener{
	TextView fieldList;
	TextView sObjectType;
	TextView ids;
	Button retrieveForUpdateButton;
	Button doneButton;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.retrieve_for_update_layout);
		setTitle("Sforce Toolkit Demo - Retrieve for Update");
		context=getApplicationContext();
		fieldList = (TextView)this.findViewById(R.id.fieldList);
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		ids = (TextView)this.findViewById(R.id.ids);
		retrieveForUpdateButton = (Button)this.findViewById(R.id.retrieveForUpdateButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
		retrieveForUpdateButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
    	Salesforce.retrieve(fieldList.getText().toString().split(","), sObjectType.getText().toString(), 
				ids.getText().toString().split(","), new RetrieveResponseListener());
	}
    
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
    public class RetrieveResponseListener extends BaseResponseListener{

        public void onComplete(final Object rresponse) {
	    	ArrayList<SObject> records=(ArrayList<SObject>) rresponse;
	    	int index=1;
	    	String recordType=null;
	    	String id=null;
	    	SObject obj = null;
	    	//There is only one record getting updated in the current application 
	    	//otherwise the following update activity would be inside the loop.
	    	
	    	if (records.size()==0){
		    	final String display="Please specify correct parameters to retrieve the record for update";
		    	SforceUpdate.this.runOnUiThread(new Runnable() {
		            public void run() {
		                mText.setText(display);
		            }
		        });
	    	} else {
		    	for (SObject hm:records)
		    	{
		        	recordType=hm.getType();
		        	id=hm.getId();
		        	obj = hm;
		    		index++;
		    	}
		    	
		    	
		    	Intent intent=new Intent();
		    	int requestCode=0;
		    	if (recordType.equals("")){
		    		
		    	} else if (recordType.equals("Contact")){ 
			    	intent.setClass(context, com.sforce.android.sample.SforceUpdateContact.class);
			    	requestCode=0;
			    	intent.putExtra("type", recordType);
			    	intent.putExtra("FirstName", obj.getField("FirstName"));
			    	intent.putExtra("LastName", obj.getField("LastName"));
			    	intent.putExtra("Id", id);
			    	startActivityForResult(intent, requestCode);
		    	} else if (recordType.equals("Account")){ 
			    	intent.setClass(context, com.sforce.android.sample.SforceUpdateAccount.class);
			    	intent.putExtra("type", recordType);
			    	intent.putExtra("Name", obj.getField("Name"));
			    	intent.putExtra("NumberOfEmployees", obj.getField("NumberOfEmployees"));
			    	intent.putExtra("Fax", obj.getField("Fax"));
			    	intent.putExtra("Id", id);
			    	requestCode=1;
			    	startActivityForResult(intent, requestCode);
		    	}
	    	}
        }
        
      @Override
      public void onSforceError(ApiFault apiFault){
      	StringBuffer sb=new StringBuffer();
      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
      	final String display=sb.toString();
      	SforceUpdate.this.runOnUiThread(new Runnable() {
            public void run() {
                mText.setText(display);
            }
        });
      }
      
      @Override
      public void onException(Exception e){
      }	      

  }
}