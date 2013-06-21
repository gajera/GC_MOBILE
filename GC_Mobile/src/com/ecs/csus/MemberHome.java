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
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MemberHome extends Activity {
	
	String username,groupname;
	private TextView welcome_lbl;
	private Button logout_btn;
	private ImageButton group_img_btn,event_img_btn,search_img_btn,discussionmem_img_btn,creategroup_img_btn,imageButton7;
	private ListView groups_lstvw;
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems=new ArrayList<String>();
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	android.app.Notification mBuilder;
	private static final int NOTIFY_ME_ID=1337;
	
	android.app.Notification mBuilder2;
	private static final int NOTIFY_ME_ID_2=1338;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memberhome);
		username = getIntent().getStringExtra("username");
		NotificationManager notificationManager = (NotificationManager) 
				  getSystemService(NOTIFICATION_SERVICE); 
		NotificationManager notificationManager2 = (NotificationManager) 
				  getSystemService(NOTIFICATION_SERVICE); 
		
		Intent notification = new Intent(this, Notification.class);
		notification.putExtra("username",username);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, notification, 0);
		
		Intent notification2 = new Intent(this, NotificationApprove.class);
		notification2.putExtra("username",username);
		notification2.putExtra("groupname",groupname);
		PendingIntent pIntent2 = PendingIntent.getActivity(this, 0, notification2, 0);
		
		try
      	{
			
	
	    	 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		    //  Log.v("in1",radiogrouptype.getText().toString());
             HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
          String url ="http://10.0.2.2:8082/csus/notification_info/"+username;
          restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
           Log.v("in2",url);
          String result = restTemplate.postForObject(url, null, String.class);
           Log.v("in3",result);
           JSONArray Jarray = new JSONArray(result);
          
          

           String temp;
           int count=Jarray.length();
           System.out.println("count"+count);
           int i;
         //  Log.isLoggable("count", count);
           for (i = 0; i < Jarray.length(); i++) 
           {
          	 JSONObject Jasonobject = Jarray.getJSONObject(i);
          	 //Log.v("loop", Jasonobject.getString("notification_title").toString());
          	 //Log.v("loop", "loop");
          	//String groupname=Jasonobject.getString("group_name").toString();
          	 if(Jasonobject.getString("notification_title").toString().equals("Request to join Group"))
          	 {
          		Log.v("else", Jasonobject.getString("notification_title").toString());
            	 mBuilder2  = new NotificationCompat.Builder(this)
               .setContentTitle(Jasonobject.getString("notification_title").toString())
               .setContentText(Jasonobject.getString("notification_subject").toString().toString()+"\n"+ "Accept Request")
               .setSmallIcon(R.drawable.icon)
               .setContentIntent(pIntent2)
               .build();
            	 
            	 notificationManager2.notify(NOTIFY_ME_ID_2, mBuilder2);
            	 //mBuilder.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
          	 }
          	 else
          	 {
          		 Log.v("if", Jasonobject.getString("notification_title").toString());
            	 mBuilder  = new NotificationCompat.Builder(this)
               .setContentTitle(Jasonobject.getString("notification_title").toString())
               .setContentText(Jasonobject.getString("notification_subject")+Jasonobject.getString("group_name"))
               .setSmallIcon(R.drawable.icon)
               .setContentIntent(pIntent)
               .build();
            	 
            	 notificationManager.notify(NOTIFY_ME_ID, mBuilder);
          	 }
          	
          	 //mBuilder.number=i;
          	
          	 
          	
          	 //notificationManager.notifyAll();
            //mBuilder2.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
           }
           
           
           
  	     }	
		 catch(Exception e)
	        {           
	        String error=Log.getStackTraceString(e);
	         Log.e("Error", error);
		   }
       
		
		welcome_lbl=(TextView) findViewById(R.id.welcome_lbl);
		welcome_lbl.setText("Welcome "+username);
		logout_btn=(Button) findViewById(R.id.logout_btn);
		logout_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
				Log.v("logout","logout");
				finish();
			}
			
		});
		
		group_img_btn=(ImageButton) findViewById(R.id.group_img_btn);
		group_img_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 Log.v("grouphome","grouphome");
		     Intent groupactivity = new Intent(getApplicationContext(),Group.class);
		     groupactivity.putExtra("username",username);
             startActivity(groupactivity);
				
			}
			
		});
		
		event_img_btn=(ImageButton) findViewById(R.id.event_img_btn);
		event_img_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 Log.v("eventimgclck","eventimgclck");
		     Intent eventactivity = new Intent(getApplicationContext(),Event.class);
		     eventactivity.putExtra("username",username);
             startActivity(eventactivity);
				
			}
			
		});
		
		search_img_btn=(ImageButton) findViewById(R.id.search_image_btn);
		search_img_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 Log.v("searchimgclck","searchimgclck");
		     Intent searchactivity = new Intent(getApplicationContext(),Search.class);
		     searchactivity.putExtra("username",username);
             startActivity(searchactivity);
				
			}
			
		});
		
		discussionmem_img_btn=(ImageButton) findViewById(R.id.discussionmem_img_btn);
		discussionmem_img_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 Log.v("searchimgclck","searchimgclck");
		     Intent discussionmemactivity = new Intent(getApplicationContext(),MemberDiscussion.class);
		     discussionmemactivity.putExtra("username",username);
             startActivity(discussionmemactivity);
				
			}
			
		});
		
		creategroup_img_btn=(ImageButton) findViewById(R.id.creategroup_img_btn);
		creategroup_img_btn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 Log.v("creategroup_img_btn","creategroup_img_btn");
		     Intent creategroup_activity = new Intent(getApplicationContext(),GroupCreate.class);
		     creategroup_activity.putExtra("username",username);
             startActivity(creategroup_activity);
				
			}
			
		});
		
		imageButton7=(ImageButton) findViewById(R.id.imageButton7);
		imageButton7.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View arg0) {
			 //Log.v("creategroup_img_btn","creategroup_img_btn");
		     Intent creategroup_activity = new Intent(getApplicationContext(),NotificationApprove.class);
		     creategroup_activity.putExtra("username",username);
             startActivity(creategroup_activity);
             //finish();
				
			}
			
		});
  	  
		//finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memberhome, menu);
		return true;
	}

}
