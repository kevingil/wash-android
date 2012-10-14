package com.kevingil.gwhs;




import com.actionbarsherlock.app.SherlockActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Home extends SherlockActivity  {
    WebView myWebView;
	ProgressBar progressBar;
	WebSettings WebViewSettings;
	TextView titleTV;
	
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	     this.setTheme(com.actionbarsherlock.R.style.Theme_Kevin);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // this changes the background color of the action bar
        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.black_textured));
        myWebView = (WebView) findViewById(R.id.webViewHome);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "loading...", true);
        //myWebView.setBackgroundColor(0);
        //myWebView.setBackgroundResource(R.drawable.lightgray3);
        myWebView.loadUrl("file:///android_asset/home.html");  
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setSavePassword(true);
        WebViewSettings.setSaveFormData(true);
        WebViewSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient(this){
    	   @Override
    	   public void onPageFinished(WebView view, String url){
    		   webViewProgress.dismiss();
    	    }
       });
        myWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                            String contentDisposition, String mimetype,
                            long contentLength) {

                                          Uri uri = Uri.parse(url);
           Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
            }
    });
        /*
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_text, null);

        titleTV = (TextView) customView.findViewById(R.id.action_custom_title);
        titleTV.setGravity(Gravity.CENTER);
        // you can apply a custom typeface here or do sth else...

        getSupportActionBar().setCustomView(customView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        */
        

    }
    @Override // this is to use go back buttom to navigate in webview history, to exit the webview people will use the back button
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null && url.startsWith("http://kevingil.github.com/dl")) {
            view.getContext().startActivity(
                new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        } else {
            return false;
        }
    }
}
