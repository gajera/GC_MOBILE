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
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Event extends Activity {
	
	private TextView eventname_lbl[];
	private ListView event_lstvw;
	private ListView event_lstvw_dt;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> dateadapter;
	List<String> list=new ArrayList<String>();
	List<String> list_dt=new ArrayList<String>();
	
	String username;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		username = getIntent().getStringExtra("username");
		Log.v("eventusername",username);	
		try{
			
			 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("event1","event1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/event/"+username;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
             Log.v("event2",url);
             String result = restTemplate.postForObject(url, null, String.class);
         Log.v("event3",result);
         JSONArray Jarray = new JSONArray(result);
         event_lstvw=(ListView) findViewById(R.id.event_lstvw);
         
         String eventname_temp;
         String eventdttm_temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 //eventname_lbl[i]= new TextView(this);
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("jsonobj", Jasonobject.getString("event_name"));
        	 //Log.v("jsonobjdt", Jasonobject.getString("event_dt_tm"));
        	 eventname_temp=Jasonobject.getString("event_name").toString();
        	 eventdttm_temp=Jasonobject.getString("event_dt_tm").toString();
        	 Log.v("output", eventdttm_temp +" "+eventname_temp );
        	// eventname_lbl[i].setText(eventname_temp +" \t "+eventdttm_temp );
        	 list.add(i, eventdttm_temp+"\n"+eventname_temp );
        	 //list_dt.add(i, eventdttm_temp );
        	
         }
         //dateadapter = new ArrayAdapter<String>(Event.this, R.layout.list_view_style,R.id.label, list_dt);
         //event_lstvw_dt.setAdapter(dateadapter);
         adapter = new ArrayAdapter<String>(Event.this, R.layout.list_view_style,R.id.label, list);
         event_lstvw.setAdapter(adapter);
         
         event_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
        	//@Override
			/*public void onItemClick(AdapterView<?> parent, View arg1, int position,	long id) 
			{
				final String[] items = { "A", "B", "C", "D" };
				adapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//group_menu_lstvw=(ListView) findViewById(R.id)
				//group_menu_lstvw.setAdapter(adapter);
				
			}*/

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				Log.v("clicklist", "clicklist");
				String item = (String) event_lstvw.getItemAtPosition(position);
				Log.v("select", item);
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
		getMenuInflater().inflate(R.menu.activity_event, menu);
		return true;
	}

}
