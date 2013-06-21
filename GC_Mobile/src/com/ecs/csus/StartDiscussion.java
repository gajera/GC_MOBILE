package com.ecs.csus;

import java.util.Collections;

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
import android.widget.Button;
import android.widget.EditText;

public class StartDiscussion extends Activity {

	EditText discussion_title_txt,discussion_desc_txt;
	Button start_discussion_btn,cancel_discussion_btn;
	String groupname,username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_discussion);
		groupname = getIntent().getStringExtra("groupname");
		username = getIntent().getStringExtra("username");
		discussion_title_txt=(EditText)findViewById(R.id.discussion_title_txt);
		discussion_desc_txt=(EditText)findViewById(R.id.discussion_desc_txt);
		start_discussion_btn=(Button)findViewById(R.id.startnew_discussion_btn);
		cancel_discussion_btn=(Button)findViewById(R.id.cancel_discussion_btn);
		
		start_discussion_btn.setOnClickListener(new View.OnClickListener() 
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
  	            		discussion_title_txt.getText().toString()+"/"+groupname+"/"+username+"/"+discussion_desc_txt.getText().toString();
  	             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
  	              Log.v("in2",url);
  	             String result = restTemplate.postForObject(url, null, String.class);
  	          Log.v("in3",result);
  	          finish();
  	        Intent groupactivity = new Intent(getApplicationContext(),GroupHome.class);
            groupactivity.putExtra("discussionname",discussion_title_txt.getText().toString());
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
		
		cancel_discussion_btn.setOnClickListener(new View.OnClickListener() 
		  {
		    
			public void onClick(View v) 
	            {
				finish();
		        }
		  }
		  );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start_discussion, menu);
		return true;
	}

}
