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
import android.widget.TextView;

public class MemberProfile extends Activity {

	String username;
	TextView userfullname_lbl,memberemailid_lbl,memberinterest_lbl,membercurrent_lbl;
	TextView userfullname_lbl_ans,memberemailid_lbl_ans,memberinterest_lbl_ans,membercurrent_lbl_ans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_profile);
		username = getIntent().getStringExtra("username");
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/user_information/get/"+username;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("resultgrp",result);
         JSONArray Jarray = new JSONArray(result);
         //userfullname_lbl=(TextView)findViewById(R.id.userfullname_lbl);
         //grouptype_lbl=(TextView)findViewById(R.id.grouptype_lbl);
        // groupdescription_lbl=(TextView)findViewById(R.id.groupdescription_lbl);
         //groupwebsite_lbl=(TextView)findViewById(R.id.groupwebsite_lbl);
         userfullname_lbl_ans=(TextView)findViewById(R.id.userfullname_lbl_ans);
         memberemailid_lbl_ans=(TextView)findViewById(R.id.memberemailid_lbl_ans);
         memberinterest_lbl_ans=(TextView)findViewById(R.id.memberinterest_lbl_ans);
         membercurrent_lbl_ans=(TextView)findViewById(R.id.membercurrent_lbl_ans);
         
         String temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 temp=Jasonobject.getString("first_name").toString();//Log.v("for", Jasonobject.getString("groupName"));
        	 userfullname_lbl_ans.setText(temp +"  "+Jasonobject.getString("last_name").toString());
        	 memberemailid_lbl_ans.setText(Jasonobject.getString("email_id").toString());
        	 memberinterest_lbl_ans.setText(Jasonobject.getString("interest").toString());
             membercurrent_lbl_ans.setText(Jasonobject.getString("currently").toString());
        	
        	
        	
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
		getMenuInflater().inflate(R.menu.activity_member_profile, menu);
		return true;
	}

}
