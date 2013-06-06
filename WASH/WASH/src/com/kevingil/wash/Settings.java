package com.kevingil.wash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.kevingil.ui.SeparatedListAdapter;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
//import com.kevingil.utils.Slide_List;
//import com.kevingil.utils.Slide_List_Adapter;

public class Settings extends SherlockActivity {
	// starts slide list ints
	
	public  final static String   ITEM_TITLE        = "title";
	public  final static String   ITEM_CAPTION      = "caption";
	private final static String[] items_data        = new String[]{"Delete web data"};
	private final static String[] items_theme       = new String[]{"dark", "light"};
	private String version; // print the app version as a string in the list view
	private SeparatedListAdapter adapter;
	private ListView mListView;
	public Map<String, ?> createItem(String title, String caption)
		{
			Map<String, String> item = new HashMap<String, String>();
			item.put(ITEM_TITLE, title);
			item.put(ITEM_CAPTION, caption);
			return item;
		}
	ActionBar ab;
    ImageButton btn_back;
    Intent feedback;
    Intent shareapp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("settings");
		setContentView(R.layout.activity_settings);
		setupActionBar();
		setUpSettingsItems();
		
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	
	public void setUpSettingsItems(){
		
		try {
		   version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
		    Log.e("tag", e.getMessage());
		}
		
		final String[] items_support     = new String[]{"Version " + version , "Send us Feedback", "Terms of Service", "Privacy Policy", "Share our app!"};
		
		adapter = new SeparatedListAdapter(this);
		ArrayAdapter<String> listadapter_schoolloop = new ArrayAdapter<String>(this, R.layout.list_item, items_data);
		ArrayAdapter<String> listadapter_wash = new ArrayAdapter<String>(this, R.layout.list_item, items_theme);
		ArrayAdapter<String> listadapter_info = new ArrayAdapter<String>(this, R.layout.list_item, items_support);
				adapter.addSection("Data", listadapter_schoolloop);
				adapter.addSection("Themes"      , listadapter_wash);
				adapter.addSection("Support"      , listadapter_info);
				
				TextView textPadding = new TextView(this); // text for padding, no other use
				mListView = (ListView) this.findViewById(R.id.list_settings);
				textPadding.setHeight(20);
		        //mListView.addHeaderView(textPadding);
		        mListView.addFooterView(textPadding);
		        mListView.setAdapter(adapter);
		        mListView.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
					
					if(position == 1){
						try{
							Process process = Runtime.getRuntime().exec("adb shell pm clear com.kevingil.wash");
							BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader( process.getInputStream() )
							);
							
							Toast.makeText(getApplicationContext(), "data has been deleted", Toast.LENGTH_SHORT).show();
						}
						catch (Exception e) {
							Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();
						}
						
					}
					
					if(position == 3){
						Toast.makeText(getApplicationContext(), "theme already set", Toast.LENGTH_LONG).show();
					}
					if(position == 4){
						Toast.makeText(getApplicationContext(), "sorry! not yet :/", Toast.LENGTH_LONG).show();
					}
					
					if(position == 6){
						Toast.makeText(getApplicationContext(), "yeah! v" + version + " ftw! :D", Toast.LENGTH_LONG).show();
					}
					
					if(position == 7){ // send email for feedback
						
						feedback = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kevingil.wash@gmail.com"));
						feedback.putExtra(Intent.EXTRA_SUBJECT, "Feedback on WASH app");
						feedback.putExtra(Intent.EXTRA_TEXT, "Hey! Just wanna give some feedback on your app:");
						startActivity(Intent.createChooser(feedback, "Send us feedback!"));
						
						}
					if(position == 8 || position == 9){
						Toast.makeText(getApplicationContext(), "wait for next update", Toast.LENGTH_LONG).show();
					}
					
					if(position ==10){ // share the app to other sources
						
						shareapp = new Intent(Intent.ACTION_SEND);
						shareapp.setType("text/plain");
						shareapp.putExtra(Intent.EXTRA_TEXT, "Check out WASH app, it's pretty awesome! https://play.google.com/store/apps/details?id=com.kevingil.wash");
						shareapp.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out WASH app!");
	                      startActivity(Intent.createChooser(shareapp, "Share our app!"));
						
						}
					}
			});
	}
    
	void setupActionBar() {
		ab = getSupportActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.ab_settings);
		ab.setDisplayHomeAsUpEnabled(true);
	}
}
