package com.kevingil.wash;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.utils.Slide_List;
import com.kevingil.utils.Slide_List_Adapter;
import com.slidingmenu.lib.SlidingMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class Social extends SherlockActivity {

	
	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		
		setTitle("social");
		
		setContentView(R.layout.webview_generic);
		
		setupActionBar();
		
		setUpSlideMenu();

		setUpSlideList();
		
		setUpWebView();

        
	}
    
	void setUpWebView() {
		
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        
        mWebViewProgress = ProgressDialog.show(this, "", "loading...", true);
        
        mWebView.loadUrl("file:///android_asset/social.html");
        
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        mWebView.setWebViewClient(new WebViewClient(){
        	
            @Override
            public void onPageFinished(WebView view, String url){
            	mWebViewProgress.dismiss();
             }
        });
        
        // settings 
        mWebViewSettings = mWebView.getSettings();
        
        mWebViewSettings.setSavePassword(true);
        
        mWebViewSettings.setSaveFormData(true);
        
        mWebViewSettings.setJavaScriptEnabled(true);
		
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	mSlideMenu.toggle();
            return true;
        }
        return true;
    }
    
    
	void setupActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.actionbar_centered_title_social);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
	}
	
	
    void setUpSlideMenu() {
		mSlideMenu = new SlidingMenu(this); 
        
		mSlideMenu.setMode(SlidingMenu.LEFT);
        
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
        
		mSlideMenu.setShadowDrawable(R.drawable.shadow);
        
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		
		mSlideMenu.setBehindScrollScale(0.0f);
        
		mSlideMenu.setFadeDegree(0.35f);
        
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        
		mSlideMenu.setMenu(R.layout.slide_menu_main);
    }
    
	void setUpSlideList(){
		
		Slide_List values[] = new Slide_List[]
                {
                    new Slide_List(R.drawable.dashboard_logo_small, "home"),
                    new Slide_List(R.drawable.schoolloop_logo_small, "schoolloop"),
                    new Slide_List(R.drawable.bulletin_small, "bulletin"),
                    new Slide_List(R.drawable.clock_small, "schedule"),
                    new Slide_List(R.drawable.groups_logo_small, "social"),
                    new Slide_List(R.drawable.about_logo_small, "about")
                };
                
                Slide_List_Adapter adapter = new Slide_List_Adapter(this, 
                        R.layout.list_adapter, values);
                
                
                mListView = (ListView)findViewById(R.id.slide_menu_wash);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) // arrays start with 0. So 0 = 1, 1 = 2 and so on.
                { 
               	 Intent i = new Intent(view.getContext(), Main.class);
               	 startActivity(i);
                	mSlideMenu.toggle();
                }

                if(position == 1)
                {  
                	 Intent i = new Intent(view.getContext(), Schoolloop.class);
                	 startActivity(i);
                	 mSlideMenu.toggle();
                }
                
                if(position == 2)
                {  
                         Intent mintent2 = new Intent(view.getContext(), Bulletin.class);
                             startActivityForResult(mintent2, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 3)
                {  
                         Intent mintent3 = new Intent(view.getContext(), Schedule.class);
                             startActivityForResult(mintent3, 0);
                             mSlideMenu.toggle();
                }
                
                if(position == 4)
                {  
                             mSlideMenu.toggle();
                }

                if(position == 5)
                {  
                         Intent mintent5 = new Intent(view.getContext(), Info.class);
                             startActivityForResult(mintent5, 0);
                             mSlideMenu.toggle();
                }
                
                    }
                  });    
		
	}
    
    
}
