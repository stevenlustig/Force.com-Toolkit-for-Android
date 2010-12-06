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
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class SforceQueryMoreExample extends Activity implements OnClickListener{
	TextView sObjectType;
	TextView type;
    TextView mText;
	TextView queryString;
	TextView queryLocator;
	Button queryButton;
	Button queryMoreButton;
	Button doneButton;
	Context context;
	OnClickListener doneOcl, queryMoreOcl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.querymore_example_layout);
		setTitle("Sforce Toolkit Demo - QueryMore Example");
		context=getApplicationContext();
		sObjectType = (TextView)this.findViewById(R.id.sObjectType);
		type= (TextView)this.findViewById(R.id.type);
		type.setText("Account");
		queryString = (TextView)this.findViewById(R.id.queryString);
		queryLocator = (TextView)this.findViewById(R.id.queryLocator);
		queryButton = (Button)this.findViewById(R.id.queryButton);
		queryMoreButton = (Button)this.findViewById(R.id.queryMoreButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
        mText.setText("Results Displayed Here");
		doneOcl=new DoneButtonClickListener();
		doneButton.setOnClickListener(doneOcl);
		queryMoreOcl=new QueryMoreButtonClickListener();
		queryMoreButton.setOnClickListener(queryMoreOcl);
		queryButton.setOnClickListener(this);
		Salesforce.init(context);
	}
	

	public void onClick(View v) {
        Salesforce.query(queryString.getText().toString().trim(),new QueryResponseListener());
	}
	
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
	
	public class QueryMoreButtonClickListener implements OnClickListener{
		public void onClick(View v) {
	        String qLocator=queryLocator.getText().toString();
	    	Salesforce.queryMore(qLocator, new QueryMoreResponseListener());
		}
	}
	  public class QueryResponseListener extends BaseResponseListener{
	      @Override
		  public void onComplete(Object qresults) {
	    	    HashMap<String, Object> queryResults=(HashMap<String, Object>) qresults;
				List<SObject> sObjectList=(List<SObject>) queryResults.get("records");
				final String qLocator=(String)queryResults.get("queryLocator");
		    	StringBuffer collateRecords=new StringBuffer();
		    	collateRecords.append("Number of Records Retrieved:").append(Integer.toString(sObjectList.size()));
		    	final String display=collateRecords.toString();
		    	SforceQueryMoreExample.this.runOnUiThread(new Runnable() {
		            public void run() {
		            	queryLocator.setText(qLocator);
		                mText.setText(display);
		            }
		        });
	      }
	      @Override
	      public void onSforceError(ApiFault apiFault){
	      	StringBuffer sb=new StringBuffer();
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
	      	SforceQueryMoreExample.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	  }
    public class QueryMoreResponseListener extends BaseResponseListener{

        public void onComplete(final Object qmresponse) {
        	HashMap<String, Object> qmResult=(HashMap<String, Object>)qmresponse;
			List<SObject> records=(ArrayList<SObject>) qmResult.get("resultArray");
	    	final String qLocator=(String) qmResult.get("queryLocator");
	    	final String size=(String)qmResult.get("size");
	    	final String isDone=(String)qmResult.get("isDone");
			int numberOfRecords=records.size();

			final String display=Integer.toString(numberOfRecords);
	    	SforceQueryMoreExample.this.runOnUiThread(new Runnable() {
	            public void run() {
	            	queryLocator.setText(qLocator);
	                mText.setText("Number of records retrieved:"+display+"\nSize="+size+"\nDone="+isDone);
	            }
	        });
        }
        
	    @Override
	    public void onSforceError(ApiFault apiFault){
	    	StringBuffer sb=new StringBuffer();
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
	      	SforceQueryMoreExample.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }

  }
}