package com.sforce.android.sample;

import java.util.ArrayList;
import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.DeleteResult;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.fault.ApiFault;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SforceDelete extends Activity implements OnClickListener{
	TextView ids;
	Button deleteButton;
	Button doneButton;
	Context context;
    TextView mText;
	OnClickListener ocl;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.delete_layout);
		setTitle("Sforce Toolkit Demo - Delete");
		context=getApplicationContext();
		ids = (TextView)this.findViewById(R.id.ids);
		deleteButton = (Button)this.findViewById(R.id.deleteButton);
		doneButton = (Button)this.findViewById(R.id.doneButton);
        mText = (TextView) this.findViewById(R.id.txt);
		deleteButton.setOnClickListener(this);
		ocl=new DoneButtonClickListener();
		doneButton.setOnClickListener(ocl);
		Salesforce.init(context);
	}
	
	public void onClick(View v) {
		String deleteIds=ids.getText().toString();
		String[] ids = deleteIds.split(",");
    	Salesforce.delete(ids, new DeleteResponseListener());
	}
    
	public class DoneButtonClickListener implements OnClickListener{
		public void onClick(View v) {
			setResult(RESULT_OK);
	        finish();
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
	    	SforceDelete.this.runOnUiThread(new Runnable() {
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
	      	SforceDelete.this.runOnUiThread(new Runnable() {
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
