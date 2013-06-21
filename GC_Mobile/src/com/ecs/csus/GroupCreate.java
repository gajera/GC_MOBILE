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
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class GroupCreate extends Activity {
	
	//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	EditText groupname_txt,groupsummary_txt,groupdescription_txt,groupwebsite_txt,groupemail_txt;
	RadioGroup groupaccess,grouptype;
	RadioButton radiogroupacess,radiogrouptype;
	Button opengroup_btn,closegroup_btn;
	TextView returngroup_lbl;
	String username,access;
	//int duration = Toast.LENGTH_SHORT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_create);
		final Context context = getApplicationContext();
		//CharSequence text = "Hello toast!";
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.activity_group_create,
                (ViewGroup) findViewById(R.id.group_create_layout));
		username = getIntent().getStringExtra("username");
		Log.v("username_group", username);
		groupname_txt=(EditText) findViewById(R.id.groupname_txt);
		groupsummary_txt=(EditText) findViewById(R.id.groupsummary_txt);
		groupdescription_txt=(EditText) findViewById(R.id.groupdescription_txt);
		groupwebsite_txt=(EditText) findViewById(R.id.groupwebsite_txt);
		groupemail_txt=(EditText) findViewById(R.id.groupemail_txt);
		opengroup_btn=(Button) findViewById(R.id.opengroup_btn);
		grouptype=(RadioGroup)findViewById(R.id.radiogrouptype);
		groupaccess=(RadioGroup)findViewById(R.id.radioacccess);
		opengroup_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				try
		    	{
					if(groupname_txt.getText().toString().equals("") ||
							groupemail_txt.getText().toString().equals("") ||
		   	                groupwebsite_txt.getText().toString().equals("") ||
		   	                groupdescription_txt.getText().toString().equals("") ||
		   	               groupsummary_txt.getText().toString().equals(""))
					{
						Toast.makeText(getApplicationContext(),"All fields are mandatory !!", Toast.LENGTH_LONG).show();
					
						
					}
					else
					{
					int grouptypeid=grouptype.getCheckedRadioButtonId();
					radiogrouptype=(RadioButton) findViewById(grouptypeid);
					int groupaccessid=groupaccess.getCheckedRadioButtonId();
					radiogroupacess=(RadioButton) findViewById(groupaccessid);
					if(radiogroupacess.getText().toString().equals("Open Group"))
					{
						access="2";
						Log.v("access", access);
					}
					else
				    {
						access="1";
						Log.v("access", access);
			     	}
		    	    		
			    	 //Log.v("login", username_txt.getText().toString()+" "+password_txt.getText().toString());
			    	 RestTemplate restTemplate = new RestTemplate();
			    	 HttpHeaders requestHeaders = new HttpHeaders();
 			       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
 			      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
 			      Log.v("in1",radiogrouptype.getText().toString());
   	                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
   	             String url ="http://10.0.2.2:8082/csus/group_create/"
   	                +groupname_txt.getText().toString()
   	                +"/"+radiogrouptype.getText().toString()
   	                +"/"+username
   	                +"/"+groupemail_txt.getText().toString()
   	                +"/"+groupwebsite_txt.getText().toString()
   	                +"/"+groupdescription_txt.getText().toString()
   	                +"/"+groupsummary_txt.getText().toString()
   	                +"/"+access;
   	             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
   	              Log.v("in2",url);
   	             String result = restTemplate.postForObject(url, null, String.class);
   	          Log.v("in3",result);
   	             if(result.equals("Group already exists. Please select another name for the group !"))
   	             {
   	            	 Log.v("if", "if");
   	           // 	Toast toast = new Toast(getApplicationContext());
   	            //	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
   	            //	toast.setDuration(Toast.LENGTH_LONG);
   	           // 	toast.setView(layout);
   	           // 	toast.show();
   	            	//returngroup_lbl=(TextView) findViewById(R.id.returngroup_lbl);
   	            	//returngroup_lbl.setText("Group already exists. Please select another name for the group !");
   	            	Toast.makeText(getApplicationContext(),"Group already exists. Please select another name for the group !" +
   	            			"", Toast.LENGTH_LONG).show();
		             
   	             }
   	             else
   	             {
   	            	Toast.makeText(getApplicationContext(),"Group Created !!" +
   	            			"", Toast.LENGTH_LONG).show();
   	            	 Intent memberhomeactivity = new Intent(getApplicationContext(),MemberHome.class);
   	            	memberhomeactivity.putExtra("username","username");
   	            	//memberhomeactivity.putExtra("password",password_txt.getText().toString());
		             startActivity(memberhomeactivity);
   	             }
			   }
		     }  
   	           catch(Exception e)
   	           {           
  	            String error=Log.getStackTraceString(e);
   	            Log.e("Error", error);
         	   }
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group_create, menu);
		return true;
	}

}
