package com.ecs.csus;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Notification_background extends Service {
	
	String username="csc258";
	android.app.Notification mBuilder;
	//NotificationManager notificationManager;
	private static final int NOTIFY_ME_ID=1337;
	NotificationManager notificationManager;

	@Override
    public void onCreate() {
		notificationManager= (NotificationManager) 
				  getSystemService(NOTIFICATION_SERVICE); 

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

	
	@Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		Log.v("return", "return");
		return START_STICKY;
	}
	
	
	private void showNotification() {
		try
		{
			Intent intent = new Intent(this, Notification.class);
			//intent.putExtra("username", username);
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
			//username ="csc258";
	
			//Log.v("service", "service");
		   
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
        int i;
        for (i = 0; i < Jarray.length(); i++) 
        {
       	 JSONObject Jasonobject = Jarray.getJSONObject(i);
       	 mBuilder  = new NotificationCompat.Builder(this)
            .setContentTitle(Jasonobject.getString("group_name").toString()+"  " +
           		 Jasonobject.getString("notification_title").toString())
            .setContentText(Jasonobject.getString("notification_subject").toString())
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pIntent)
            .build();
       	 
       	 //mBuilder.number=i;
       	notificationManager.notify(NOTIFY_ME_ID, mBuilder);
       	mBuilder.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
       	
        }  
		}
		catch(Exception e)
		{
			
		}
	}   
		
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
