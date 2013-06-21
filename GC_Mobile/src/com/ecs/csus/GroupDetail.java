package com.ecs.csus;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.*;

public class GroupDetail extends Activity {

	String groupname;
	TextView groupname_lbl,grouptype_lbl,groupdescription_lbl,groupwebsite_lbl;
	TextView groupname_lbl_ans,grouptype_lbl_ans,groupdescription_lbl_ans,groupwebsite_lbl_ans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_detail);
		groupname = getIntent().getStringExtra("groupname");
		Log.v("detailgroupname",groupname);	
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/group/"+groupname;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("resultgrp",result);
         JSONArray Jarray = new JSONArray(result);
         groupname_lbl=(TextView)findViewById(R.id.groupname_lbl);
         grouptype_lbl=(TextView)findViewById(R.id.grouptype_lbl);
         groupdescription_lbl=(TextView)findViewById(R.id.groupdescription_lbl);
         groupwebsite_lbl=(TextView)findViewById(R.id.groupwebsite_lbl);
         groupname_lbl_ans=(TextView)findViewById(R.id.groupname_lbl_ans);
         grouptype_lbl_ans=(TextView)findViewById(R.id.grouptype_lbl_ans);
         groupdescription_lbl_ans=(TextView)findViewById(R.id.groupdescription_lbl_ans);
         groupwebsite_lbl_ans=(TextView)findViewById(R.id.groupwebsite_lbl_ans);
         
         String temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("for", Jasonobject.getString("groupName"));
        	 groupname_lbl_ans.setText(Jasonobject.getString("group_name").toString());
        	 grouptype_lbl_ans.setText(Jasonobject.getString("type").toString());
        	 groupdescription_lbl_ans.setText(Jasonobject.getString("description").toString());
        	 groupwebsite_lbl_ans.setText(Jasonobject.getString("website").toString());
        	
        	
        	
         }
         
        
        }  
          catch(Exception e)
          {          
          String error=Log.getStackTraceString(e);
           Log.e("Error", error);
 	   }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group_detail, menu);
		return true;
	}

}
