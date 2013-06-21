package com.ecs.csus;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GroupHome extends TabActivity {

	String groupname,username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_home);
		groupname = getIntent().getStringExtra("groupname");
		username = getIntent().getStringExtra("username");
		try
		{
		TabHost tabHost = getTabHost();
		 
        // Tab for Detail
        TabSpec groupdetail = tabHost.newTabSpec("Detail");
        // setting Title and Icon for the Tab
        groupdetail.setIndicator("Detail");
        Intent groupdetailIntent = new Intent(this, GroupDetail.class);
       // Log.v("homegroupname", groupname);
        groupdetailIntent.putExtra("groupname",groupname);
        groupdetailIntent.putExtra("username",username);
        groupdetail.setContent(groupdetailIntent);
 
        // Tab for Discussion
        TabSpec groupdiscussion = tabHost.newTabSpec("Discussion");
        groupdiscussion.setIndicator("Discussion");
        Intent groupdiscussionIntent = new Intent(this, GroupDiscussion.class);
       // Log.v("homegroupname", groupname);
        groupdiscussionIntent.putExtra("groupname",groupname);
        groupdiscussionIntent.putExtra("username",username);
        groupdiscussion.setContent(groupdiscussionIntent);
 
        // Tab for Member
        TabSpec groupmember = tabHost.newTabSpec("Member");
        groupmember.setIndicator("Member");
        Intent groupmemberIntent = new Intent(this, GroupMember.class);
       // Log.v("homegroupname", groupname);
        groupmemberIntent.putExtra("groupname",groupname);
        groupmemberIntent.putExtra("username",username);
        groupmember.setContent(groupmemberIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(groupdetail); // Adding photos tab
        tabHost.addTab(groupdiscussion); // Adding songs tab
        tabHost.addTab(groupmember); 
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
		getMenuInflater().inflate(R.menu.activity_group_home, menu);
		return true;
	}

}
