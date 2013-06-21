package com.ecs.csus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NotificationApprove extends Activity {
	
	String username,groupname;
	private ArrayAdapter<String> adapter;
	ArrayAdapter<String> acceptadapter;
	List<String> list=new ArrayList<String>();
	List<String> accept=new ArrayList<String>();
	private static final int NOTIFY_ME_ID_2=1338;
	private ListView notification_approve_lstvw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_approve);
		username = getIntent().getStringExtra("username");
		//groupname = getIntent().getStringExtra("groupname");
		Log.v("username", username);
		NotificationManager notificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE); 
		notificationManager.cancel(NOTIFY_ME_ID_2);
		
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
             notification_approve_lstvw=(ListView) findViewById(R.id.notification_approve_lstvw);
           //  notification_accept_lstvw=(ListView) findViewById(R.id.notification_accept_lstvw);
            String temp;
            //int i=0;
            int j=0;
            for (int i=0; i < Jarray.length(); i++) 
             {
            	JSONObject Jasonobject = Jarray.getJSONObject(i);
            	 if(Jasonobject.getString("notification_title").toString().equals("Request to join Group"))
            	 {
            		
            		 temp=//Jasonobject.getString("notification_title").toString()
                			 //+ "\n"+
            				 Jasonobject.getString("notification_subject").toString()+"\""+
            				 Jasonobject.getString("group_name").toString()+"\" \n"+ "Accept Request";
            		 Log.v("temp",temp);
            		 list.add(j, temp);
            		 j++;
            	 }
            	 
            	// accept[i]= new Button(this);
            	 //accept[i].setText("Accept");
            	// accept[i].setTextColor(Color.WHITE);
            	// lr1.addView(accept[i]);
            	 //accept.add(i, "Accept Request");
            	
            	
             }
             adapter = new ArrayAdapter<String>(NotificationApprove.this, R.layout.list_view_style,R.id.label, list);
             notification_approve_lstvw.setAdapter(adapter);
             notification_approve_lstvw.setOnItemClickListener(new OnItemClickListener()
             {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
    			{
    				String groupname = null;
    				try
    				{
    					
    			     String discussionname = (String) notification_approve_lstvw.getItemAtPosition(position);
    			     Log.v("output", discussionname);
    			     
    			     Pattern p = Pattern.compile("\"([^\"]*)\"");
    			     Matcher m = p.matcher(discussionname);
    			     while (m.find()) {
    			    	 groupname =m.group(1);
    			     }
    			     
    				RestTemplate restTemplate1 = new RestTemplate();
    		    	 HttpHeaders requestHeaders1 = new HttpHeaders();
    			       requestHeaders1.setAccept(Collections.singletonList(new MediaType("application","json")));
    			      requestHeaders1.setAcceptEncoding(ContentCodingType.GZIP);
    			     Log.v("username2",username+groupname);
    	             HttpEntity<?> requestEntity1 = new HttpEntity<Object>(requestHeaders1);
    	            String url1 ="http://10.0.2.2:8082/csus/notification_approve/"+username+"/"+groupname;
    	            restTemplate1.getMessageConverters().add(new StringHttpMessageConverter());
    	            Log.v("in2",url1);
    	            String result1 = restTemplate1.postForObject(url1, null, String.class);
    	            Log.v("in3",result1);
    	            //JSONArray Jarray1 = new JSONArray(result1);
    	          
    	           String temp1;
    	           //int count=Jarray.length();
    	         //  System.out.println("count"+count);
    	          
    	         //  Log.isLoggable("count", count);
    	           if(result1.equals("Request Approval Send"))
    	           {
    	        	   Toast.makeText(getApplicationContext(),"Request Approved", Toast.LENGTH_LONG).show();
    	        	   finish();
    	           }
    			}
    				catch(Exception e)
    		        {           
    		        String error=Log.getStackTraceString(e);
    		         Log.e("Error", error);
    			   }
    			
    			}
             });
            // acceptadapter = new ArrayAdapter<String>(Notification.this, R.layout.list_view_style,R.id.label, accept);
             //notification_accept_lstvw.setAdapter(acceptadapter);
             
             
       
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
		getMenuInflater().inflate(R.menu.activity_notification__accept, menu);
		return true;
	}

}
