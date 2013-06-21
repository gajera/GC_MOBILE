package com.ecs.csus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GroupJoin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_join);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group_join, menu);
		return true;
	}

}
