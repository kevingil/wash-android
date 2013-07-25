package com.kevingil.wash;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockActivity;

public class Post extends SherlockActivity{

	WebView webView;
	ImageButton btn_back;
	ProgressDialog mWebViewProgress;
	WebSettings mWebViewSettings;
	
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")

@Override
public void onCreate(Bundle savedInstanceState) {
	
	   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	        this.setTheme(R.style.Theme_Kevin);
	   }
         super.onCreate(savedInstanceState);
         overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
         setContentView(R.layout.post_webview); 
         
         getActionBar().hide();
         
        webView = (WebView) findViewById(R.id.post_webview);
        
        mWebViewProgress = new ProgressDialog(this);
        mWebViewProgress.setMessage("Loading...");
        mWebViewProgress.setCancelable(false);
        mWebViewProgress.setButton(DialogInterface.BUTTON_NEGATIVE, "Hide", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mWebViewProgress.show();
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(this.getIntent().getDataString());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
            	mWebViewProgress.dismiss();
             }
        });
        // webview settings 
        mWebViewSettings = webView.getSettings();
        mWebViewSettings.setJavaScriptEnabled(true);
         
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

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
    	webView.goBack();
        return true;
    } else {
    	finish();
    }
    return super.onKeyDown(keyCode, event);
}


}
/*


public class Post extends SherlockActivity {

	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ImageButton btn_back;
	ActionBar ab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("social");
		setContentView(R.layout.webview_generic);
		setupActionBar();
		setUpWebView();
        
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
	}
    
	void setUpWebView() 
	{
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        mWebViewProgress = ProgressDialog.show(this, "", "", true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.loadUrl(this.getIntent().getDataString());
        mWebView.setWebViewClient(new Webclient(this){
            @Override
            public void onPageFinished(WebView view, String url){
            	mWebViewProgress.dismiss();
             }
        });
        // webview settings 
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setJavaScriptEnabled(true);
		
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        } else {
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
	void setupActionBar() {
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.ab_post);
		ab.setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.image));
	}
    
@Override
public void finish(){
	super.finish();
	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
}    

}
*/
