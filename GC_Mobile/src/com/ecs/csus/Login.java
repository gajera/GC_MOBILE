package com.ecs.csus;

import java.util.Collections;

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
import android.widget.*;



@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class Login extends Activity {
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	private Button login_btn,forgot_btn;
    private EditText username_txt,password_txt;
    private TextView tv;
	
	@Override
	protected void onCreate(Bundle icicle) {
		
		StrictMode.setThreadPolicy(policy); 
		super.onCreate(icicle);
		setContentView(R.layout.activity_login);
		username_txt=(EditText) findViewById(R.id.username_txt);
		password_txt=(EditText) findViewById(R.id.password_txt);
		
  		
  		
		login_btn=(Button) findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new View.OnClickListener() 
		  {
		    
			public void onClick(View v) 
	            {
		    	try
		    	{
		    		

		    		
			    	 Log.v("login", username_txt.getText().toString()+" "+password_txt.getText().toString());
			    	 RestTemplate restTemplate = new RestTemplate();
			    	 HttpHeaders requestHeaders = new HttpHeaders();
 			       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
 			      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
 			      Log.v("in1","in1");
   	                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
   	             String url ="http://10.0.2.2:8082/csus/login/"+username_txt.getText().toString()+"/"+password_txt.getText().toString();
   	             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
   	              Log.v("in2",url);
   	             String result = restTemplate.postForObject(url, null, String.class);
   	          Log.v("in3",result);
   	             if(result.equals("1"))
   	             {
	                 Intent groupactivity = new Intent(getApplicationContext(),MemberHome.class);
	                 groupactivity.putExtra("username",username_txt.getText().toString());
	                 groupactivity.putExtra("password",password_txt.getText().toString());
		             startActivity(groupactivity);
		             password_txt.setText("");
		       		 username_txt.setText("");
   	             }
   	             else
   	             {
   	            	// tv=(TextView) findViewById(R.id.invalid_lbl);
   	            	
   	            //	 tv.setText("Invalid Username or Password !");
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
		
		
		forgot_btn=(Button) findViewById(R.id.forgot_btn);
		forgot_btn.setOnClickListener(new View.OnClickListener() 
		  {
		    
			public void onClick(View v) 
	            {
		    	    Intent forgotactivity = new Intent(getApplicationContext(),Forgot.class);
		    	   // forgotactivity.putExtra("username",username_txt.getText().toString());
		    	   // forgotactivity.putExtra("password",password_txt.getText().toString());
		             startActivity(forgotactivity);
	            }
		  });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
