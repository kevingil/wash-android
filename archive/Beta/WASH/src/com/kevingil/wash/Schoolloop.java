package com.kevingil.wash;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.kevingil.utils.Slide_List;
import com.kevingil.utils.Slide_List_Adapter;
import com.slidingmenu.lib.SlidingMenu;

public class Schoolloop extends Activity {

	
	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ListView mListView;
	Button homeAsUp;
	ActionBar ab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("schoolloop");
		
		setContentView(R.layout.webview_generic);
		
		setTitle("loop");
		
		mSlideMenu = new SlidingMenu(this); 
        
		mSlideMenu.setMode(SlidingMenu.LEFT);
        
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
        
		mSlideMenu.setShadowDrawable(R.drawable.shadow);
        
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        
		mSlideMenu.setFadeDegree(0.35f);
        
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        
		mSlideMenu.setMenu(R.layout.slide_menu_main);
		
	
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        
        mWebViewProgress = ProgressDialog.show(this, "", "loading...", true);
        
        mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/login");
        
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
                
                
                mListView = (ListView)findViewById(R.id.slide_menu_schoolloop);
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
                         Intent mintent4 = new Intent(view.getContext(), Social.class);
                             startActivityForResult(mintent4, 0);
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

                mWebView.setDownloadListener(new DownloadListener() {
                    public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype,
                                    long contentLength) {

                                                  Uri uri = Uri.parse(url);
                   Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                            startActivity(intent);
                    }
            });
                

                setUpCustomActionBar();
        
	}
	
	void setUpCustomActionBar() {
		ab = getActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.actionbar_centered_title_schoolloop);
		ab.setDisplayHomeAsUpEnabled(true);

    }
    
    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button on actionbar
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

}
