package com.ecs.csus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MemberDiscussion extends Activity {

	ListView discussiontitle_user_lstvw;
	String username;
	List<String> list =new ArrayList<String>();
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_discussion);
		username = getIntent().getStringExtra("username");
		//Log.v("discussiongroupname",groupname);	
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/discussion_name_user/"+username;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("resultgrp",result);
         JSONArray Jarray = new JSONArray(result);
         discussiontitle_user_lstvw=(ListView) findViewById(R.id.discussiontitle_user_lstvw);
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("for", Jasonobject.getString("groupName"));
        	 temp=Jasonobject.getString("discussion_title").toString();
        	// groupname_btn[i]= new Button(this);
        	// groupname_btn[i].setTextColor();
        	 //groupname_btn[i].setText(Jasonobject.getString("groupName").toString());
        	 //Log.v("temp", temp);
        	 list.add(i, temp);
        	
         }
         
         adapter = new ArrayAdapter<String>(MemberDiscussion.this, R.layout.list_view_style,R.id.label, list);
         discussiontitle_user_lstvw.setAdapter(adapter);
         /*discussiontitle_lstvw.setOnItemClickListener(new OnItemClickListener()
         {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				String discussionname = (String) discussiontitle_lstvw.getItemAtPosition(position);
				Intent discusssionactivity = new Intent(getApplicationContext(),Discussion.class);
				discusssionactivity.putExtra("discussionname",discussionname);
				discusssionactivity.putExtra("groupname",groupname);
				discusssionactivity.putExtra("username",username);
	            startActivity(discusssionactivity);
			}
        }
        );*/
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
		getMenuInflater().inflate(R.menu.activity_member_discussion, menu);
		return true;
	}

}
