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
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Discussion extends Activity {

	String discussionname,username,groupname;
	EditText discussion_txt;
	TableLayout lTableLayout;
	Button discussion_send_btn;
	
	TextView tv1[];
	//List<EditText> discussionlst = new ArrayList<EditText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discussion);
		RelativeLayout mLinearLayout = new RelativeLayout(this);
		mLinearLayout = (RelativeLayout)findViewById(R.layout.activity_discussion);
	//	RelativeLayout.LayoutParams params = 
	 //   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
		//RelativeLayout.LayoutParams.WRAP_CONTENT);
		discussionname = getIntent().getStringExtra("discussionname");
		groupname = getIntent().getStringExtra("groupname");
		username = getIntent().getStringExtra("username");
		Log.v("discussionname",discussionname);	
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/discussion_group/"+discussionname;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("resultgrp",result);
         JSONArray Jarray = new JSONArray(result);
       
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String user_name,discussion_desc;
        // discussion=new EditText[Jarray.length()];
         tv1=new TextView[Jarray.length()];
         lTableLayout = (TableLayout)findViewById(R.id.tblayout);
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 user_name=Jasonobject.getString("user_name").toString();
        	 discussion_desc=Jasonobject.getString("discussion_desc").toString();
        	// Log.v("for", Jasonobject.getString("discussion_desc"));
        	 TableRow newRow = new TableRow(this);
        	// discussion[i]= new EditText(this);
        	// discussion[i].
        	 //discussion[i].setText(username+ "  "+discussion_desc);
        	 tv1[i]= new TextView(this);
             tv1[i].setText(user_name+ "\n"+discussion_desc);
             tv1[i].setTextColor(Color.WHITE);
             if((i%2)==0)
             {
            	 Log.v("if", discussion_desc);
            	 //tv1[i].layout(30, 10, 0, 10);
            	// params.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
            	 //tv1[i].setLayoutParams(params);
            	
            	 tv1[i].setPadding(20, 10, 230, 10);
            	 tv1[i].setWidth(230);
            	 //tv1[i].setBackgroundColor();
            	
            	 
             }
            if((i%2)!=0)
             {
            	 Log.v("else", discussion_desc);
            	//tv1[i].setpadd
            	 //
            	 tv1[i].setPadding(230, 10, 20, 10);
            	 tv1[i].setWidth(460);
            	 
            	/// tv1[i].setpa
             }
        	 newRow.addView(tv1[i]);
             lTableLayout.addView(newRow);
        	 
        	/* discussion = new EditText(Discussion.this);
        	 discussionlst.add(discussion);
        	 //discussion.setBackgroundResource(R.color.blackOpacity);
        	 //discussion.setId(id);  
        	 discussion.setText(username+ "  "+discussion_desc);
        	 mLinearLayout.addView(discussion);*/
        	 
         }
         
         discussion_txt=(EditText)findViewById(R.id.discussion_txtbox);
         discussion_send_btn=(Button)findViewById(R.id.discussion_send_btn);
         discussion_send_btn.setOnClickListener(new View.OnClickListener() 
		  {
		    public void onClick(View v) 
	            {
		    	try
		    	{
			    	 RestTemplate restTemplate = new RestTemplate();
			    	 HttpHeaders requestHeaders = new HttpHeaders();
			       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
			      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
			      Log.v("in1","in1");
  	                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
  	             String url ="http://10.0.2.2:8082/csus/discussion_insert_group/"+
  	            		discussionname+"/"+groupname+"/"+username+"/"+discussion_txt.getText().toString();
  	             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
  	              Log.v("in2",url);
  	             String result = restTemplate.postForObject(url, null, String.class);
  	          Log.v("in3",result);
  	          finish();
  	        Intent groupactivity = new Intent(getApplicationContext(),Discussion.class);
            groupactivity.putExtra("discussionname",discussionname);
            groupactivity.putExtra("groupname",groupname);
            groupactivity.putExtra("username",username);
            startActivity(groupactivity);
  	        
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
		getMenuInflater().inflate(R.menu.activity_discussion, menu);
		return true;
	}

}
