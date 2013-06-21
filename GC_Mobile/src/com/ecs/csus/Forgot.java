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
import android.widget.*;

public class Forgot extends Activity {

	Button forgot_send_btn;
	TextView forgot_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot);
		
		forgot_txt=(EditText)findViewById(R.id.forgot_txt);
		
		forgot_send_btn=(Button) findViewById(R.id.forgot_send_btn);
		forgot_send_btn.setOnClickListener(new View.OnClickListener() 
		  {
		    
			public void onClick(View v) 
	            {
		    	try
		    	{
		    		

		    		
			    	// Log.v("login", username_txt.getText().toString()+" "+password_txt.getText().toString());
			    	 RestTemplate restTemplate = new RestTemplate();
			    	 HttpHeaders requestHeaders = new HttpHeaders();
 			       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
 			      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
 			      Log.v("in1","in1");
   	                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
   	             String url ="http://10.0.2.2:8082/csus/forgot/"+forgot_txt.getText().toString();
   	             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
   	              Log.v("in2",url);
   	             String result = restTemplate.postForObject(url, null, String.class);
   	            Log.v("in3",result);
   	             if(result.equals("Username and Password is Sent to your email!!"))
   	             {
   	            	Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
   	            	finish();
   	             }
   	             else
   	             {
   	            	Toast.makeText(getApplicationContext(),"Invalid Username or Password !!", Toast.LENGTH_LONG).show();
   	             }
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_forgot, menu);
		return true;
	}

}
