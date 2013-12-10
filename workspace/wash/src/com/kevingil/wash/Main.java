package com.kevingil.wash;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;
import com.kevingil.camera.*;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.kevingil.ui.GridViewAdapter;
import com.kevingil.ui.ListAdapter;
import com.kevingil.ui.ListItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.slidingmenu.lib.SlidingMenu;

public class Main extends SherlockActivity {
	// ends slide list ints
	GridView gridView;
	SlidingMenu rSlideMenu;
	WebView mWebView;
	ProgressDialog mWebViewProgress;
	WebSettings mWebViewSettings;
	public ListView mListView;
    ImageButton btn_back_sliding;
    TextView mTitle;
    TextView sTitle;
    ActionBar actionbar;
    LayoutInflater inflator;
	static final String[] gridViewItems = new String[] 
			{ 
		"Schoolloop",
		"Eagle News",
		"Schedule",
		"Capture",
		"Bulletin",
		"Social",
		"Places",
		"Settings"
		};
	
	OnItemClickListener itemListeners = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
		{
			// position 0 is the TextView used as padding, TextView textPadding = new TextView(this);
			if(position == 1)
			{
            	Intent i = new Intent(getBaseContext(), Schoolloop.class);
            	startActivity(i);
				}
            if(position == 2)
            {
            	Intent i = new Intent(getBaseContext(), News.class);
            	startActivity(i);
            	}
            if(position == 3)
            {
            	Intent i = new Intent(getBaseContext(), Schedule.class);
                startActivity(i);
            	}
            if(position == 4)
            {
                Intent i = new Intent(getBaseContext(), CameraActivity.class);
                startActivity(i);
             }
            if(position == 5)
            {
                Intent i = new Intent(getBaseContext(), Bulletin.class);
                startActivity(i);
                     }

            if(position == 6)
            {
                Intent i = new Intent(getBaseContext(), Social.class);
                startActivity(i);
                     }
            if(position == 7)
            {
            	Intent i = new Intent(getBaseContext(), Places.class);
                startActivity(i);
            	}
            if(position == 8)
            {
                Intent i = new Intent(getBaseContext(), Settings.class);
                startActivity(i);
                         }
			}
	};
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		 
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		   {
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		//overridePendingTransition(R.anim.scale_in, 0);
		setTitle("The Eagle");
		
		if(isTablet(true))
		{
			setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
			setContentView(R.layout.activity_main_tablet);
			makeGridView();
			
		} else
		{
			setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
			setContentView(R.layout.activity_main);
			setupActionBar();
			makeListView();
		}
		
		setupActionBar();
		setUpSlideSchedule();
	}

    // return true if is tablet
	private boolean isTablet(boolean b)
	{
	    return (getBaseContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
	    		>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	// actionabr for both phone and tablet
	void setupActionBar()
	{
		actionbar = getSupportActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionbar.setCustomView(R.layout.actionbar);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		mTitle = (TextView) findViewById(R.id.ab_title);
		mTitle.setText("WASH");
		
	}
	
	//for both phone and tablet
    void setUpSlideSchedule() 
    {
		rSlideMenu = new SlidingMenu(this); 
		rSlideMenu.setMode(SlidingMenu.RIGHT);
		rSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
		//mSlideMenu.setShadowDrawable(R.drawable.shadow);
		//rSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		rSlideMenu.setBehindScrollScale(1.0f);
		rSlideMenu.setFadeDegree(0.0f);
		rSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		rSlideMenu.setMenu(R.layout.slidemenu_schedule);
		
		sTitle = (TextView) findViewById(R.id.ab_title_sliding);
		sTitle.setText("schedule");
		
		
		btn_back_sliding = (ImageButton) findViewById(R.id.btn_back_sliding);
		btn_back_sliding.setImageResource(R.drawable.ic_back);
		btn_back_sliding.setOnClickListener(new View.OnClickListener()
		{
		    @Override
            public void onClick(View view)
		    {
              rSlideMenu.toggle();
            }
        });
		
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        mWebView.loadUrl("file:///android_asset/bellschedule.html");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setSavePassword(true);
        mWebViewSettings.setSaveFormData(true);
        //mWebViewSettings.setJavaScriptEnabled(true);
         
         
        
    }   

	
	@SuppressLint("NewApi")
	void makeListView(){
        ListItem item_data[] = new ListItem[]
        {
            new ListItem(R.drawable.ic_schoolloop, "Schoolloop"),
            new ListItem(R.drawable.ic_eaglenews, "Eagle News"),
            new ListItem(R.drawable.ic_schedule, "Schedule"),
            new ListItem(R.drawable.ic_capture, "Capture"),
            new ListItem(R.drawable.ic_bulletin, "Bulletin"),
            new ListItem(R.drawable.ic_social, "Social"),
            new ListItem(R.drawable.ic_places, "Places"),
            new ListItem(R.drawable.ic_settings, "Settings")
        };
        
        ListAdapter adapter = new ListAdapter(this, R.layout.home_list_item, item_data);
        
        TextView textPadding = new TextView(this);
        mListView = (ListView)findViewById(R.id.home_listview);
        textPadding.setHeight(15); // Can only specify in pixels unfortunately. No DIP :-(
        mListView.addHeaderView(textPadding);
        mListView.addFooterView(textPadding);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener( itemListeners );
   }

	public void makeGridView(){
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new GridViewAdapter(this, gridViewItems));
		gridView.setOnItemClickListener( itemListeners );
 
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(0, R.anim.scale_out);
	}
	
}
