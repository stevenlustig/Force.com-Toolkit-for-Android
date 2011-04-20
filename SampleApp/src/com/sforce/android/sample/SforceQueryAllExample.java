package com.sforce.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.DeleteResult;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.SaveResult;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class SforceQueryAllExample extends Activity implements OnClickListener{
	TextView sObjectType;
	TextView type;
	TextView name;
	TextView ids;
    TextView mText;
	TextView queryString;
   
	Button createButton;
	Button deleteButton;
	Button queryAllButton;
	Button doneButton;
	Context context;
	OnClickListener doneOcl, deleteOcl, queryAllOcl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.queryall_example_layout);
		setTitle("Sforce Toolkit Demo - QueryAll Example");
		context=getApplicationContext();
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		type= (TextView)this.findViewById(R.id.type);
		type.setText("Account");
		name = (TextView)this.findViewById(R.id.name);
		ids = (TextView)this.findViewById(R.id.ids);
		queryString = (TextView)this.findViewById(R.id.queryString);

		createButton = (Button)this.findViewById(R.id.createButton);
		deleteButton = (Button)this.findViewById(R.id.deleteButton);
		queryAllButton = (Button)this.findViewById(R.id.queryAllButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
        mText.setText("Results Displayed Here");
		createButton.setOnClickListener(this);
		doneOcl=new DoneButtonClickListener();
		doneButton.setOnClickListener(doneOcl);
		deleteOcl=new DeleteButtonClickListener();
		deleteButton.setOnClickListener(deleteOcl);
		queryAllOcl=new QueryAllButtonClickListener();
		queryAllButton.setOnClickListener(queryAllOcl);
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
		SObject obj = new SObject();
		obj.setType(type.getText().toString().trim());
		HashMap<String, String> requestFields=new HashMap<String, String>();
        requestFields.put("name", name.getText().toString());
        obj.setFields(requestFields);
        ArrayList<SObject> objs = new ArrayList<SObject>();
        objs.add(obj);
    	Salesforce.create(objs, new CreateResponseListener());
	}
    
	public class DeleteButtonClickListener implements OnClickListener{
		public void onClick(View v) {
	        String deleteIds=ids.getText().toString();
			String[] ids = deleteIds.split(",");
	    	Salesforce.delete(ids, new DeleteResponseListener());
		}
	}
	
	public class QueryAllButtonClickListener implements OnClickListener{
		public void onClick(View v) {
	        String qString=queryString.getText().toString();
	    	Salesforce.queryAll(qString, new QueryAllResponseListener());
		}
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
	    	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
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
	      	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	      
	      @Override
	      public void onException(Exception e){
	      }	      

	  }
    
    public class DeleteResponseListener extends BaseResponseListener{
        public void onComplete(final Object dresults) {
			final ArrayList<DeleteResult> resultArray=(ArrayList<DeleteResult>)dresults;
			StringBuffer collateResults=new StringBuffer();
			for (DeleteResult dr: resultArray){
				if (dr.isSuccess()){
					collateResults=collateResults.append("Record ").append(dr.getId()).append(" deleted successfully.\n");
				}else{
					collateResults.append("Record ").append(dr.getId()).append(" deletion failed.\n");
					if (dr.getErrors()!=null){
						collateResults.append("Error Message: ").append(dr.getErrors().get(0).getMessage()).append("\n");
						collateResults.append("Status Code: ").append(dr.getErrors().get(0).getStatusCode().getValue()).append("\n");
					}
				}
			}

	    	final String display=collateResults.toString();
	    	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
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
	      	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	      
	      @Override
	      public void onException(Exception e){
	      }	      

    }
    public class QueryAllResponseListener extends BaseResponseListener{

        public void onComplete(final Object qaresponse) {
	    	List<SObject> records=(ArrayList<SObject>) qaresponse;
	    	String collateRecords="";
	    	int index=1;
	    	
	    	for (SObject hm:records)
	    	{
	    		collateRecords=collateRecords+hm.getType()+index+"\n ID:"+hm.getId()+"\n Type:"+hm.getType()+"\n"+"\n-------------------\n";
	    		index++;
	    	}

	    	final String display=collateRecords;
	    	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
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
      	SforceQueryAllExample.this.runOnUiThread(new Runnable() {
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