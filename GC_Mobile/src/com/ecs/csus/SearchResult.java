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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

//@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SearchResult extends Activity {

	String search_txt,username,result1;
	 ListView searchresult_lstvw,join_lstvw;
	 ArrayAdapter<String> subadapter;
	 ArrayAdapter<String> joinadapter;
	List<String> sublist=new ArrayList<String>();
	List<String> join=new ArrayList<String>();
	String[] list1={"A","B"};
	//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		search_txt = getIntent().getStringExtra("search_txt");
		username = getIntent().getStringExtra("username");
		Log.v("search_txt",search_txt);	
		try
    	{
    		 RestTemplate restTemplate = new RestTemplate();
	    	 HttpHeaders requestHeaders = new HttpHeaders();
		       requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		      requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
		      Log.v("in1","in1");
               HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            String url ="http://10.0.2.2:8082/csus/search/"+username;
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            // Log.v("in2",url);
             String result = restTemplate.postForObject(url, null, String.class);
             
         Log.v("resultsearch",result);
         JSONArray Jarray = new JSONArray(result);
         searchresult_lstvw=(ListView) findViewById(R.id.searchresult_lstvw);
         join_lstvw=(ListView) findViewById(R.id.join_lstvw);
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String temp;
         int j=0;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
        	 //Log.v("for", Jasonobject.getString("groupName"));
        	 temp=Jasonobject.getString("groupName").toString();
        	 Log.v("tempr",temp);
        	 if(temp.equals(search_txt))
        	 {
        	  Log.v("iftemp",temp);
             sublist.add(j, temp);
        	 int count=sublist.get(j).length();
        	 if(count<14)
        	 {
        		 join.add(j, "+ Join Group");
        	 }
        	 else if(count>29 || count >17)
        	 {
        		 join.add(j, "+ \n"+" Join Group ");
        	 }
        	 else if(count>44)
        	 {
        		join.add(j, "+ \n "+" Join Group ");
        	 }
        	 j++;
        	 }
        	
         }
         joinadapter = new ArrayAdapter<String>(SearchResult.this, R.layout.list_view_style,R.id.label, join);
         join_lstvw.setAdapter(joinadapter);
         join_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				//final String[] items = { "A", "B", "C", "D" };
				//subadapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//ListView group_menu_lstvw =new ListView(null);
				//group_menu_lstvw.setAdapter(subadapter);
				//String groupname = (String) searchresult_lstvw.getItemAtPosition(position);
				//Intent searchresultactivity = new Intent(getApplicationContext(),GroupJoin.class);
				//searchresultactivity.putExtra("groupname",groupname);
	            //startActivity(searchresultactivity);
				try
				{
				 RestTemplate restTemplate1 = new RestTemplate();
		    	 HttpHeaders requestHeaders1 = new HttpHeaders();
			       requestHeaders1.setAccept(Collections.singletonList(new MediaType("application","json")));
			      requestHeaders1.setAcceptEncoding(ContentCodingType.GZIP);
			     // Log.v("in1","in1");
	               HttpEntity<?> requestEntity1 = new HttpEntity<Object>(requestHeaders1);
	               String groupname = (String) searchresult_lstvw.getItemAtPosition(position);
	            String url ="http://10.0.2.2:8082/csus/group_join/"+username+"/"+groupname;
	            restTemplate1.getMessageConverters().add(new StringHttpMessageConverter());
	            // Log.v("in2",url);
	            	
	             result1 = restTemplate1.postForObject(url, null, String.class);
	            
	         //Log.v("resultsearch",result1);
	         //JSONArray Jarray1 = new JSONArray(result1);
	         //String temp1;
	         //for (int i = 0; i < Jarray1.length(); i++) 
	         //{
	        //	 JSONObject Jasonobject = Jarray1.getJSONObject(i);
	        	 //Log.v("for", Jasonobject.getString("groupName"));
	        //	 temp1=Jasonobject.getString("groupName").toString();
	       //  }
	         
			}
		    catch(Exception e)
		          {          
		          String error=Log.getStackTraceString(e);
		           Log.e("Error", error);
		 	   }
				Toast.makeText(getApplicationContext(),result1, Toast.LENGTH_LONG).show();
				 finish(); 
			}
        }
        );
    	
         
         subadapter = new ArrayAdapter<String>(SearchResult.this, R.layout.list_view_style,R.id.label, sublist);
         searchresult_lstvw.setAdapter(subadapter);
         searchresult_lstvw.setOnItemClickListener(new OnItemClickListener()
         {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) 
			{
				
				Log.v("clicklist", "clicklist");
				//final String[] items = { "A", "B", "C", "D" };
				//subadapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//ListView group_menu_lstvw =new ListView(null);
				//group_menu_lstvw.setAdapter(subadapter);
				String groupname = (String) searchresult_lstvw.getItemAtPosition(position);
				Intent searchresultactivity = new Intent(getApplicationContext(),GroupHome.class);
				searchresultactivity.putExtra("groupname",groupname);
	            startActivity(searchresultactivity);
			}
        }
        );
        /* searchresult_lstvw.setOnItemClickListener(new OnItemClickListener()
         {
        	 
        	 
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,	long id) 
			{
				final String[] items = { "A", "B", "C", "D" };
				adapter = new ArrayAdapter<String>(Group.this, android.R.layout.simple_list_item_1, items);
				//group_menu_lstvw=(ListView) findViewById(R.id)
				//group_menu_lstvw.setAdapter(adapter);
				
			}

			
			
         }
        );*/
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
		getMenuInflater().inflate(R.menu.activity_group, menu);
		return true;
	}

}
