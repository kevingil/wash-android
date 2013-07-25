package com.kevingil.wash;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.slidingmenu.lib.SlidingMenu;

import com.kevingil.ui.ListAdapter;
import com.kevingil.ui.ListItem;

public class Schoolloop extends SherlockActivity {

	SlidingMenu mSlideMenu;
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog webViewProgress;
	ListView mListView;
	Bundle translate_up;
	ImageButton btn_back;
	Intent downloadIntent;
	Uri downloadUrl;
	ListAdapter adapter;
	ActionBar actionbar;
	TextView mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		   {
		        this.setTheme(R.style.Theme_Kevin);
		   }
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		
		if (isTablet(true)){
			setContentView(R.layout.activity_schoolloop_tablet);
		} else {
			setContentView(R.layout.webview_generic);
			setUpSlideMenu();	
		}
		
		setUpSlideList();
		setUpWebView();
		setupActionBar();
	}
    
	void setUpWebView() {
		
		if(isTablet(true)){
			mWebView  = (WebView) findViewById(R.id.webview_divided);
		} else {
			mWebView  = (WebView) findViewById(R.id.generic_webview);
		}
		/*
		webViewProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "okay", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	webViewProgress.dismiss();
		    }
		});
		
		webViewProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		});
		*/
        //webViewProgress = ProgressDialog.show(this, "", "loading...", true);
        
        webViewProgress = new ProgressDialog(this);
        webViewProgress.setMessage("Loading...");
        webViewProgress.setCancelable(false);
        webViewProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        webViewProgress.show();
        
        
        mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/login");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient(){
        	
            @Override
            public void onPageFinished(WebView view, String url){
                webViewProgress.dismiss();
             }
            
        });
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                downloadUrl = Uri.parse(url);
                downloadIntent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                    startActivity(downloadIntent);
            }
    });
        // settings 
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setSavePassword(true);
        mWebViewSettings.setSaveFormData(true);
        mWebViewSettings.setJavaScriptEnabled(true);
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        } 
        else {
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
	void setupActionBar() {
		actionbar = getSupportActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		actionbar.setCustomView(R.layout.actionbar);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		mTitle = (TextView) findViewById(R.id.ab_title);
		mTitle.setText("schoolloop");
		
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setImageResource(R.drawable.ic_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
	}
	
	public void setUpSlideMenu(){
		mSlideMenu = new SlidingMenu(this); 
		mSlideMenu.setMode(SlidingMenu.LEFT);
		mSlideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlideMenu.setShadowWidthRes(R.dimen.shadow_width);
		//mSlideMenu.setShadowDrawable(R.drawable.shadow);
		mSlideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlideMenu.setBehindScrollScale(0.0f);
		mSlideMenu.setFadeDegree(0.45f);
		mSlideMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		mSlideMenu.setMenu(R.layout.slidemenu_schoolloop);
	}
	
public void setUpSlideList(){
	
    ListItem item_data[] = new ListItem[]
            {
                new ListItem(R.drawable.ic_grades, "grades"),
                new ListItem(R.drawable.ic_assignments, "assignments"),
                new ListItem(R.drawable.ic_calendar, "calendar"),
                new ListItem(R.drawable.ic_loopmail, "loopmail"),
                new ListItem(R.drawable.ic_logout, "logout"),
                new ListItem(R.drawable.ic_fullsite, "full site")
            };
            
            adapter = new ListAdapter(this, R.layout.listview_item_dark, item_data);
            
            TextView textPadding = new TextView(this);
            if (isTablet(true)){
            	mListView = (ListView)findViewById(R.id.listview_divided);
            } else {
            	mListView = (ListView)findViewById(R.id.schoolloop_listview);
            }
            textPadding.setHeight(15); // Can only specify in pixels unfortunately. No DIP :-(
            mListView.addHeaderView(textPadding);
            mListView.addFooterView(textPadding);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new OnItemClickListener()
    		{
    			@Override
    			public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
    				
    				if (isTablet(true)){
    					// will avoid forece close on tablets because there's no slide menu in tablets
    				} else {
    					mSlideMenu.toggle(); // will close for all options
    				}
    				
    				if (position == 1){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/index");
    				}
    				if (position == 2){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/student_current_assignments");
    				}
    				if (position == 3){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/calendar_week");
    				}
    				if (position == 4){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/mobile/mail_inbox");
    				}
    				if (position == 5){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/portal/logout");
    				}
    				if (position == 6){
    					mWebView.loadUrl("https://gwhs-sfusd-ca.schoolloop.com/portal/index");
    				}
    			}
    		});
	}
	
	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
	
    // return true if is tablet
	private boolean isTablet(boolean b) {
	    return (getBaseContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
	    		>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	
}
