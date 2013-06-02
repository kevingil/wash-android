package com.kevingil.wash.more;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.ui.SeparatedListAdapter;
import com.kevingil.wash.Bulletin;
import com.kevingil.wash.Main;
import com.kevingil.wash.News;
import com.kevingil.wash.Places;
import com.kevingil.wash.R;
import com.kevingil.wash.Schedule;
import com.kevingil.wash.Schoolloop;
import com.kevingil.wash.Social;
import com.kevingil.wash.R.anim;
import com.kevingil.wash.R.dimen;
import com.kevingil.wash.R.id;
import com.kevingil.wash.R.layout;
import com.kevingil.wash.R.style;
import com.slidingmenu.lib.SlidingMenu;



public class TermsOfService extends SherlockActivity {

	
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_in_right, 0);
		setTitle("terms of service");
		setContentView(R.layout.webview_generic);
		setupActionBar();	
		setUpWebView();

		ImageButton btw_left = (ImageButton) findViewById(R.id.btw_slidemenutoggle_main_left);
		btw_left.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		     Intent i = new Intent(TermsOfService.this, Info.class);
		     startActivity(i);
		     finish();
		    }
		});

	}
    
	void setUpWebView() {
		
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        mWebViewProgress = ProgressDialog.show(this, "", "loading...", true);
        mWebView.loadUrl("http://kevingil.github.com/wash/terms-of-service/");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        } else {
        	Intent i = new Intent(TermsOfService.this, Main.class);
        	startActivity(i);
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
	void setupActionBar() {
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.ab_terms_of_service);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
	}
    
}
