package com.kevingil.wash;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.kevingil.ui.ListAdapter;
import com.kevingil.ui.ListItem;
import com.kevingil.wash.more.Info;
import com.slidingmenu.lib.SlidingMenu;
//import com.kevingil.utils.Slide_List;
//import com.kevingil.utils.Slide_List_Adapter;

public class Main extends SherlockActivity {
	// ends slide list ints
	SlidingMenu rSlideMenu;
	WebView mWebView;
	ProgressDialog mWebViewProgress;
	WebSettings mWebViewSettings;
	public ListView mListView;
    ImageButton btn_schedule_back;
    TextView mTitle;
    ActionBar ab;
    LayoutInflater inflator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.scale_in, 0);
		setTitle("home");
		setContentView(R.layout.activity_main);
		setupActionBar();
		setUpSlideMenu();
		listItems();
		
	}

    
    void setUpSlideMenu() 
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
		
		btn_schedule_back = (ImageButton) findViewById(R.id.btn_schedule_back);
		btn_schedule_back.setOnClickListener(new View.OnClickListener() {
		    @Override
            public void onClick(View view) {
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
    
	void setupActionBar() {
		ab = getSupportActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.ab_main);
		ab.setDisplayHomeAsUpEnabled(true);
	}

	
	@SuppressLint("NewApi")
	void listItems(){
        ListItem item_data[] = new ListItem[]
        {
            new ListItem(R.drawable.ic_schoolloop, "Schoolloop"),
            new ListItem(R.drawable.db, "Eagle News"),
            new ListItem(R.drawable.clock, "Schedule"),
            new ListItem(R.drawable.db, "Bulletin"),
            new ListItem(R.drawable.groups, "Social"),
            new ListItem(R.drawable.about_logo, "Places"),
            new ListItem(R.drawable.about_logo, "About Us")
        };
        
        ListAdapter adapter = new ListAdapter(this, 
                R.layout.home_list_item, item_data);
        
        TextView textPadding = new TextView(this);
        mListView = (ListView)findViewById(R.id.home_listview);
        textPadding.setHeight(15); // Can only specify in pixels unfortunately. No DIP :-(
        mListView.addHeaderView(textPadding);
        mListView.addFooterView(textPadding);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
				
				// position 0 is the TextView used as padding, TextView textPadding = new TextView(this);
				
				if(position == 1){
                	Intent i = new Intent(view.getContext(), Schoolloop.class);
                	startActivity(i);
					}
                if(position == 2){
                	Intent i = new Intent(view.getContext(), News.class);
                	startActivity(i);
                	}
                if(position == 3){
                	Intent i = new Intent(view.getContext(), Schedule.class);
                    startActivity(i);
                	//rSlideMenu.toggle();
                	}
                if(position == 4){
                    Intent i = new Intent(view.getContext(), Bulletin.class);
                    startActivity(i);
                         }
                if(position == 5){
                    Intent i = new Intent(view.getContext(), Social.class);
                    startActivity(i);
                         }
                if(position == 6){
                	Intent i = new Intent(view.getContext(), Places.class);
                    startActivity(i);
                	}
                if(position == 7){
                    Intent i = new Intent(view.getContext(), Info.class);
                    startActivity(i);
                             }
				}
		});
   }
	
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(0, R.anim.scale_out);
	}
	
}
