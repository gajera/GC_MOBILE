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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GroupMember extends Activity {
	
	String groupname,username;
	ListView groupmemeber_lstvw,remove_lstvw; 
	List<String> list=new ArrayList<String>();
	List<String> removelist=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> removeadapter;
	String manager="0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_member);
		username = getIntent().getStringExtra("username");
		groupname = getIntent().getStringExtra("groupname");
		Log.v("discussiongroupname",groupname);	
		try
    	{
			// MANAGER NAME 
			
			 RestTemplate restTemplate1 = new RestTemplate();
	    	 HttpHeaders requestHeaders1 = new HttpHeaders();
		       requestHeaders1.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders1.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("userin1",username);
               HttpEntity<?> requestEntity1 = new HttpEntity<Object>(requestHeaders1);
            String url1 ="http://10.0.2.2:8082/csus/groupmanager/"+username+"/"+groupname;
            restTemplate1.getMessageConverters().add(new StringHttpMessageConverter());
            // Log.v("url1",url1);
             String result1 = restTemplate1.postForObject(url1, null, String.class);
             //Log.v("url2",result1);
             //JSONArray Jarray1 = new JSONArray(result1);
             if(result1.equals("[{\"user_name\":\""+username+"\"}]"))
             {
            	
            	 manager="1";
            	 Log.v("managerif", manager);
             }
			// MANAGER NAME END
			
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/group_user/"+groupname;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
             Log.v("resultgrp",result);
             JSONArray Jarray = new JSONArray(result);
         groupmemeber_lstvw=(ListView) findViewById(R.id.groupmemeber_lstvw);
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String temp;
         int j=0;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("for", Jasonobject.getString("groupName"));
        	 if(Jasonobject.getString("user_name").toString().equals(username))
        	 {
        		 Log.v("match", "match");
        		 temp=Jasonobject.getString("user_name").toString();
        	 }
        	 else
        	 {
        		 temp=Jasonobject.getString("user_name").toString();
            	 list.add(j, temp);
            	 if(manager.equals("1"))
            	 {
            		 Log.v("manager", temp);
            		 removelist.add(j, "Remove");
            	 }
            	 j++;
        	 }
        	
         }
         
         adapter = new ArrayAdapter<String>(GroupMember.this, R.layout.list_view_style,R.id.label, list);
         groupmemeber_lstvw.setAdapter(adapter);
         groupmemeber_lstvw.setOnItemClickListener(new OnItemClickListener()
         {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				String username = (String) groupmemeber_lstvw.getItemAtPosition(position);
				Intent memberprofileactivity = new Intent(getApplicationContext(),MemberProfile.class);
				//discusssionactivity.putExtra("discussionname",discussionname);
				//discusssionactivity.putExtra("groupname",groupname);
				memberprofileactivity.putExtra("username",username);
	            startActivity(memberprofileactivity);
			}
        }
        );
         
         remove_lstvw=(ListView) findViewById(R.id.remove_lstvw);
         removeadapter = new ArrayAdapter<String>(GroupMember.this, R.layout.list_view_style,R.id.label, removelist);
         remove_lstvw.setAdapter(removeadapter);
         remove_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				try
				{
				 RestTemplate restTemplate2 = new RestTemplate();
		    	 HttpHeaders requestHeaders2 = new HttpHeaders();
			       requestHeaders2.setAccept(Collections.singletonList(new MediaType("application","json")));
			      requestHeaders2.setAcceptEncoding(ContentCodingType.GZIP);
			     // Log.v("in1","in1");
	               HttpEntity<?> requestEntity2 = new HttpEntity<Object>(requestHeaders2);
	               String memberremove_name = (String) groupmemeber_lstvw.getItemAtPosition(position);
	            String url2 ="http://10.0.2.2:8082/csus/user_remove/"+memberremove_name+"/"+groupname;
	            restTemplate2.getMessageConverters().add(new StringHttpMessageConverter());
	            Log.v("url2",url2);
	            	
	             String result2 = restTemplate2.postForObject(url2, null, String.class);
	             Log.v("result2",result2);
	             Toast.makeText(getApplicationContext(),"Member removed from the Group !!", Toast.LENGTH_LONG).show();
                 finish();
			}
		    catch(Exception e)
		          {          
		          String error=Log.getStackTraceString(e);
		           Log.e("Error", error);
		 	   }
			
			}
        }
        );
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
		getMenuInflater().inflate(R.menu.activity_group_discussion, menu);
		return true;
	}

}
