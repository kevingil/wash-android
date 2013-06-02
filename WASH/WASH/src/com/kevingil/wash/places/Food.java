package com.kevingil.wash.places;

import com.kevingil.wash.R;
import com.kevingil.wash.R.layout;
import com.kevingil.wash.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Food extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_generic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_food, menu);
		return true;
	}

}
