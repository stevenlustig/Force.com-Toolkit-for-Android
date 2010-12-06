package com.sforce.android.sample;

import java.util.ArrayList;
import java.util.List;
import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.fault.ExceptionCode;
import com.sforce.android.soap.partner.sobject.SObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SforceQuery extends Activity implements OnClickListener{
	TextView queryString;
	Button queryButton;
	Button doneButton;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.query_layout);
		setTitle("Sforce Toolkit Demo - Query");
		context=getApplicationContext();
		queryString = (TextView)this.findViewById(R.id.queryString);
		queryButton = (Button)this.findViewById(R.id.queryButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
		queryButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
        Salesforce.query(queryString.getText().toString(), new QueryResponseListener());
	}
    
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
		}
	}
	
	  public class QueryResponseListener extends BaseResponseListener{
	      @Override
		  public void onComplete(Object sObjects) {
				List<SObject> sObjectList=(ArrayList<SObject>) sObjects;
		    	StringBuffer collateRecords=new StringBuffer();
		    	for (SObject hm:sObjectList){
		    		collateRecords.append(hm.toString());
		    	}

		    	final String display=collateRecords.toString();
		    	SforceQuery.this.runOnUiThread(new Runnable() {
		            public void run() {
		                mText.setText(display);
		            }
		        });
	      }

	      @Override
	      public void onSforceError(ApiFault apiFault){
	      	StringBuffer sb=new StringBuffer();
	      	String msg = apiFault.getExceptionMessage();
	      	String code = apiFault.getExceptionCode().getValue();
	      	if (code.equals(ExceptionCode._INVALID_FIELD))
	      	{	
	      		System.out.println("Invalid field");
	      	}
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
	      	SforceQuery.this.runOnUiThread(new Runnable() {
	            public void run() {
	                mText.setText(display);
	            }
	        });
	      }
	  }
}
