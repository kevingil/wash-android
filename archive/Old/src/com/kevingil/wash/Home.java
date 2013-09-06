package com.kevingil.wash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.kevingil.wash.R;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;


public class Home extends SherlockActivity implements ISideNavigationCallback {

	private SideNavigationView sideNavigationView;
	private WebView myWebView;
	ProgressBar progressBar;
	private WebSettings WebViewSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);
		sideNavigationView = (SideNavigationView) findViewById(R.id.home_side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
		sideNavigationView.setMenuClickCallback(this);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		
        myWebView = (WebView) findViewById(R.id.webViewHome);
        final ProgressDialog webViewProgress = ProgressDialog.show(this, "", "loading...", true);
        myWebView.loadUrl("file:///android_asset/home.html");  
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // settings 
        WebViewSettings = myWebView.getSettings();
        WebViewSettings.setSavePassword(true);WebViewSettings.setSaveFormData(true);
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
		
	}


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = this.getSupportMenuInflater();
       inflater.inflate(R.menu.menu_main, menu);
       return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_about:
        	 Intent i = new Intent(this, AboutDeveloper.class);
             this.startActivity(i);
             break;
        case android.R.id.home:
            sideNavigationView.toggleMenu();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

	@Override
	public void onSideNavigationItemClick(int itemId) {
		switch (itemId) {
        case R.id.snm1:
            Intent snm1 = new Intent(this, Home.class);
            this.startActivity(snm1);
            break;
        case R.id.snm2:
            Intent snm2 = new Intent(this, SchoolloopActivity.class);
            this.startActivity(snm2);
            break;
        case R.id.snm3:
            Intent snm3 = new Intent(this, DailyBulletin.class);
            this.startActivity(snm3);
            break;
        case R.id.snm4:
            Intent snm4 = new Intent(this, BellScheduleActivity.class);
            this.startActivity(snm4);
            break;
        case R.id.snm5:
            Intent snm5 = new Intent(this, Social.class);
            this.startActivity(snm5);
            break;
        case R.id.snm6:
            Intent snm6 = new Intent(this, AboutDeveloper.class);
            this.startActivity(snm6);
            break;
			
		default:	
			return;
		}
		finish();
	}

	@Override
	public void onBackPressed() {
		// hide menu if it shown
		if (sideNavigationView.isShown()) {
			sideNavigationView.hideMenu();
		} else {
			super.onBackPressed();
		}
	}



}