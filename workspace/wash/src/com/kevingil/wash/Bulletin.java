package com.kevingil.wash;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class Bulletin extends SherlockActivity {
	
	WebView mWebView;
	WebSettings mWebViewSettings;
	ProgressDialog mWebViewProgress;
	ActionBar ab;
	//Toast stillloading;
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("bulletin");
		setContentView(R.layout.webview_generic);
		setUpBulletinWebView();
		setupActionBar();
		
		ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) { // action bar left button
		    	finish();
		    }
		});
		
	}
    
	void setupActionBar() {
		ab = getSupportActionBar();
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		ab.setCustomView(R.layout.ab_bulletin);
		ab.setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yourimage));
    }
	
	void setUpBulletinWebView(){
        mWebView  = (WebView) findViewById(R.id.generic_webview);
        mWebViewProgress = ProgressDialog.show(this, null, "loading hun!", true);
        mWebView.loadUrl("http://gwhs-sfusd-ca.schoolloop.com/bulletin");
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient(){
        	
        	/*
        	public boolean onTouch(View v, MotionEvent event) {
        	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	    	Context context = getApplicationContext();
        	    	CharSequence text = "page will finish loading";
        	    	int duration = Toast.LENGTH_SHORT;
        	    	stillloading = Toast.makeText(context, text, duration);
        	    	stillloading.show();

        	        return true;
        	    }
        	    return false;
        	}   
        	*/
            @Override
            public void onPageFinished(WebView view, String url){
            /*	
     		   mWebView.loadUrl("javascript:"
       		   		+ "$('head link, head style').remove();"
       		   		+ "document.body.style.background=\"white\";"
       		   		+ "document.getElementById('page_title').style.display=\"none\";"
       		   	    + "document.getElementById('skip').style.display=\"none\";"
       		   	    + "document.getElementById('container_footer').style.display=\"none\";"
       		   	    + "document.getElementById('block_standard_left').style.display=\"none\";"
       		   	    + "document.getElementById('container_header').style.display=\"none\";" 
       		   	    + "document.getElementsByClass('block_text').style.width=\"100%\";" 
       		   	    + "document.getElementByTagName('div').setAttribute(\"style\",\"width:100%\");" 
       		   	    + "document.getElementByTagName('span').setAttribute(\"style\",\"width:100%\");" 
       		   	    + "document.getElementByTagName('p').setAttribute(\"style\",\"font-family:'Arial'\");" 
       		   	    + "document.getElementByTagName('font').setAttribute(\"style\",\"width:100%\");" 
       		   	    + "document.getElementByTagName('span').setAttribute(\"style\",\"font-family:'Arial'\");" 
       		   	    + "docmunet.getElementsByClass('block_content_main').setAttribute(\"style\", \"width:100%;\");" 
       		   	    + "document.getElementById('container_page').setAttribute(\"style\", \"width:100%;\");" 
       		   	    + "document.getElementById('content_margin_right').setAttribute(\"style\", \"width:100%;\");"
       		   	    + "document.getElementByTagName('table').setAttribute(\"style\",\"font-family:'Arial'\");"
       		   	    + "document.getElementByTagName('tbody').setAttribute(\"style\",\"width:100%\");"
       		   	    + "document.getElementByTagName('tr').setAttribute(\"style\",\"width:100%\");"
       		   	    + "document.getElementById('block_wide_main').setAttribute(\"style\", \"width:100%;\");" 
          		   		);
     		   */
            	mWebViewProgress.dismiss();
             }
        });
        
        mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                            String contentDisposition, String mimetype,
                            long contentLength) {
                                          Uri uri = Uri.parse(url);
           Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(i);
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
        	finish();
        }
        return super.onKeyDown(keyCode, event);
    }
	
@Override
public void finish(){
	super.finish();
	overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
}

}
