package com.sforce.android.sample;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SF8 extends ListActivity {
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	String[] mainMenuItems = getResources().getStringArray(R.array.items);
        context=getApplicationContext();
        setTitle("Sforce Toolkit Demo - Main Menu");
        setListAdapter(new ArrayAdapter<String>(this, R.layout.main, mainMenuItems));
        ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			 Intent intent=new Intent();
		     if (id==0){
		    	 intent.setClass(context, com.sforce.android.sample.SforceLogin.class);
		     } else if (id==1){
		    	 intent.setClass(context, com.sforce.android.sample.SforceQuery.class);
		     } else if (id==2){
		    	 intent.setClass(context, com.sforce.android.sample.SforceRetrieve.class);
		     } else if (id==3){
		    	 intent.setClass(context, com.sforce.android.sample.SforceCreate.class);
		     } else if (id==4){
		    	 intent.setClass(context, com.sforce.android.sample.SforceUpdate.class);
		     } else if (id==5){
		    	 intent.setClass(context, com.sforce.android.sample.SforceDelete.class);
		     } else if (id==6){
		    	 intent.setClass(context, com.sforce.android.sample.SforceUpsertAccount.class);
		     } else if (id==7){
		    	 intent.setClass(context, com.sforce.android.sample.SforceQueryMoreExample.class);
		     } else if (id==8){
		    	 intent.setClass(context, com.sforce.android.sample.SforceQueryAllExample.class);
		     } else if (id==9){
		    	 intent.setClass(context, com.sforce.android.sample.SforceLogout.class);
		     }
		     startActivity(intent);
			}
		});
    }
}