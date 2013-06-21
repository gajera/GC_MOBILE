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
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Notification extends Activity {

	String username;
	android.app.Notification mBuilder;
	private static final int NOTIFY_ME_ID=1337;
	private ListView notification_lstvw,notification_accept_lstvw;
	private ArrayAdapter<String> adapter;
	ArrayAdapter<String> acceptadapter;
	List<String> list=new ArrayList<String>();
	List<String> accept=new ArrayList<String>();
	//Button accept[];
	RelativeLayout lr1;
	 //NotificationManager notificationManager = 
		//		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		//lr1 = new RelativeLayout(this);
		username = getIntent().getStringExtra("username");
		Log.v("username", username);
		NotificationManager notificationManager = (NotificationManager) 
				  getSystemService(NOTIFICATION_SERVICE); 
		notificationManager.cancel(NOTIFY_ME_ID);
		
		try
    	{
			
	
	    	 RestTemplate restTemplate1 = new RestTemplate();
	    	 HttpHeaders requestHeaders1 = new HttpHeaders();
		       requestHeaders1.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders1.setAcceptEncoding(ContentCodingType.GZIP);
		    //  Log.v("in1",radiogrouptype.getText().toString());
               HttpEntity<?> requestEntity1 = new HttpEntity<Object>(requestHeaders1);
            String url ="http://10.0.2.2:8082/csus/notification_info/"+username;
            restTemplate1.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("in2",url);
            String demo = restTemplate1.postForObject(url, null, String.class);
             Log.v("in3",demo);
             JSONArray Jarray1 = new JSONArray(demo);
             notification_lstvw=(ListView) findViewById(R.id.notification_lstvw);
           //  notification_accept_lstvw=(ListView) findViewById(R.id.notification_accept_lstvw);
             String temp;
             int i;
             int j=0;
             for (i = 0; i < Jarray1.length(); i++) 
             {
            	 JSONObject Jasonobject1 = Jarray1.getJSONObject(i);
            	 if(Jasonobject1.getString("notification_title").toString().equals("Request to join Group"))
              	 {
            		 Log.v("if","if");
              	 }
            	 else
            	 {
            	 temp=Jasonobject1.getString("notification_title").toString()
            			 + "\n"+Jasonobject1.getString("notification_subject").toString();
            	// accept[i]= new Button(this);
            	 //accept[i].setText("Accept");
            	// accept[i].setTextColor(Color.WHITE);
            	// lr1.addView(accept[i]);
            	 //accept.add(i, "Accept Request");
            	 Log.v("temp2",temp);
            	 list.add(j, temp);
            	 j++;
            	 }
             }
             adapter = new ArrayAdapter<String>(Notification.this, R.layout.list_view_style,R.id.label, list);
             notification_lstvw.setAdapter(adapter);
             
            // acceptadapter = new ArrayAdapter<String>(Notification.this, R.layout.list_view_style,R.id.label, accept);
             //notification_accept_lstvw.setAdapter(acceptadapter);
             RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		     //Log.v("username2",username+groupname);
             HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
             String url2 ="http://10.0.2.2:8082/csus/notification_update/"+username;
             restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
              Log.v("url2",url2);
             restTemplate.postForObject(url2, null, String.class);
             
        }  
          catch(Exception e)
          {           
          String error=Log.getStackTraceString(e);
           Log.e("Error", error);
 	   }
		
		
		
				// Hide the notification after its selected
		//mBuilder.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

				
				//notificationManager.notify(0, mBuilder2);
		
		Log.v("end", "end");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notification, menu);
		return true;
	}

}
