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
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends Activity {
	
	Button search_btn;
	EditText search_txt;
	String username;
	ListView search_auto;
	ArrayAdapter adapter;
	List<String> sublist=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		username = getIntent().getStringExtra("username");
		search_txt=(EditText) findViewById(R.id.search_txt);
		search_btn=(Button) findViewById(R.id.search_btn);
		search_auto=(ListView)findViewById(R.id.search_auto);
		String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

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
         //groupname_btn=(Button[]) findViewById(R.id.groupname_btn);
         String temp;
         for (int i = 0; i < Jarray.length(); i++) 
         {
        	 JSONObject Jasonobject = Jarray.getJSONObject(i);
    	 //Log.v("for", Jasonobject.getString("groupName"));
        	 temp=Jasonobject.getString("groupName").toString();
    	// groupname_btn[i]= new Button(this);
    	// groupname_btn[i].setTextColor();
    	 //groupname_btn[i].setText(Jasonobject.getString("groupName").toString());
    	 //Log.v("temp", temp);
        	 sublist.add(i, temp);
         }
		
		
		 adapter = new ArrayAdapter<String>(this, R.layout.list_view_style, R.id.label, sublist);
		 search_auto.setAdapter(adapter);
		 
		 search_txt.addTextChangedListener(new TextWatcher() {
             
	            @Override
	            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	                // When user changed the Text
	                Search.this.adapter.getFilter().filter(cs);   
	            }
	             
	            @Override
	            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	                    int arg3) {
	                // TODO Auto-generated method stub
	                 
	            }

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
	             
	          
	        });
		 
		 
		search_btn.setOnClickListener(new View.OnClickListener() 
		  {

			@Override
			public void onClick(View arg0) {
				
				
				 Intent searchresult = new Intent(getApplicationContext(),SearchResult.class);
				 searchresult.putExtra("search_txt",search_txt.getText().toString());
				 searchresult.putExtra("username",username);
	             startActivity(searchresult);     
	             
			}
		
		  });
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
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

}
