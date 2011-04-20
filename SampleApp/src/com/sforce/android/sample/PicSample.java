package com.sforce.android.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.sforce.android.soap.partner.BaseResponseListener;
import com.sforce.android.soap.partner.Salesforce;
import com.sforce.android.soap.partner.SaveResult;
import com.sforce.android.soap.partner.fault.ApiFault;
import com.sforce.android.soap.partner.sobject.SObject;

public class PicSample extends Activity {
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_layout);

		SurfaceView preview=(SurfaceView)findViewById(R.id.preview);
		previewHolder=preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_CAMERA ||
				keyCode==KeyEvent.KEYCODE_SEARCH) {
			takePicture();

			return(true);
		}

		return(super.onKeyDown(keyCode, event));
	}

	private void takePicture() {
		camera.takePicture(null, null, photoCallback);
	}

	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			camera=Camera.open();

			try {
				camera.setPreviewDisplay(previewHolder);
			}
			catch (Throwable t) {
				Log.e("PictureDemo-surfaceCallback",
							"Exception in setPreviewDisplay()", t);
				Toast
					.makeText(PicSample.this, t.getMessage(), Toast.LENGTH_LONG)
					.show();
			}
		}

		public void surfaceChanged(SurfaceHolder holder,
									int format, int width,
									int height) {
			Camera.Parameters parameters=camera.getParameters();

			parameters.setPreviewSize(width, height);
			parameters.setPictureFormat(PixelFormat.JPEG);

			camera.setParameters(parameters);
			camera.startPreview();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();
			camera.release();
			camera=null;
		}
	};

	Camera.PictureCallback photoCallback=new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePhotoTask().execute(data);
			camera.startPreview();
			setResult(RESULT_OK);
	        finish();
		}
	};

	class SavePhotoTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... jpeg) {
	        SharedPreferences savedSession =
	            getApplicationContext().getSharedPreferences("SObject-Id", Context.MODE_PRIVATE);
	        String sobjectId = savedSession.getString("Id", null);
			Salesforce.init(getApplicationContext());
			SObject attach = new SObject();
			attach.setType("Attachment");
			attach.setField("parentId", sobjectId);
			String image = Base64.encodeToString(jpeg[0], Base64.DEFAULT);
			attach.setField("body", image);
			attach.setField("ContentType", "image/jpg");
			attach.setField("Name", "SamplePicture");
	        ArrayList<SObject> objs = new ArrayList<SObject>();
	        objs.add(attach);
	        
	        Salesforce.create(objs, new CreateResponseListener());

			return(null);
		}
	}

    public class CreateResponseListener extends BaseResponseListener{
	    @Override
        public void onComplete(final Object cresults) {
	    	ArrayList<SaveResult> resultArray=(ArrayList<SaveResult>) cresults;
			StringBuffer collateResults=new StringBuffer();
			for (SaveResult sr: resultArray){
				if (sr.isSuccess()){
				}else{
					collateResults.append(sr.getErrors());
				}
			}
	    	final String display=collateResults.toString();
        }        	      
	      @Override
	      public void onSforceError(ApiFault apiFault){
	      	StringBuffer sb=new StringBuffer();
	      	sb.append("Exception message:").append(apiFault.getExceptionMessage()).append("\n").append("Exception Code:").append(apiFault.getExceptionCode().toString());
	      	final String display=sb.toString();
	      }
	      
	      @Override
	      public void onException(Exception e){
	      }	      
	  }
}
