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

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Group extends Activity {

	String username;
	private ListView group_lstvw;
	private ListView group_menu_lstvw;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> subadapter;
	private Button[] groupname_btn;
	List<String> list=new ArrayList<String>();
	List<String> sublist=new ArrayList<String>();
	String[] list1;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		username = getIntent().getStringExtra("username");
		Log.v("username",username);	
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/user_group/"+username;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("resultgrp",result);
         JSONArray Jarray = new JSONArray(result);
         group_lstvw=(ListView) findViewById(R.id.group_lstvw);
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("for", Jasonobject.getString("groupName"));
        	 temp=Jasonobject.getString("groupName").toString();
        	// groupname_btn[i]= new Button(this);
        	// groupname_btn[i].setTextColor();
        	 //groupname_btn[i].setText(Jasonobject.getString("groupName").toString());
        	 //Log.v("temp", temp);
        	 list.add(i, temp);
        	
         }
         
         adapter = new ArrayAdapter<String>(Group.this, R.layout.list_view_style,R.id.label, list);
         group_lstvw.setAdapter(adapter);
         group_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
        	//@Override
			/*public void onItemClick(AdapterView<?> parent, View arg1, int position,	long id) 
			{
				final String[] items = { "A", "B", "C", "D" };
				adapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//group_menu_lstvw=(ListView) findViewById(R.id)
				//group_menu_lstvw.setAdapter(adapter);
				
			}*/

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				//final String[] items = { "A", "B", "C", "D" };
				//subadapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//ListView group_menu_lstvw =new ListView(null);
				//group_menu_lstvw.setAdapter(subadapter);
				String groupname = (String) group_lstvw.getItemAtPosition(position);
				Intent groupactivity = new Intent(getApplicationContext(),GroupHome.class);
			    groupactivity.putExtra("groupname",groupname);
			    groupactivity.putExtra("username",username);
	            startActivity(groupactivity);
			}
        }
        );
        /* group_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
        	 
        	 
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,	long id) 
			{
				final String[] items = { "A", "B", "C", "D" };
				adapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//group_menu_lstvw=(ListView) findViewById(R.id)
				//group_menu_lstvw.setAdapter(adapter);
				
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
		getMenuInflater().inflate(R.menu.activity_group, menu);
		return true;
	}

}
