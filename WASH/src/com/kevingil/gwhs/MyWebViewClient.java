package com.kevingil.gwhs;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    
    private Context context;

    public MyWebViewClient(Context context) {
        this.context = context;
    }
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	// the url can be as random as "kevin://whatever" this url has
    	//to be the url that the html link(that will open the activity) has
        if(url.equals("kevin://openSchoolloopActivity")){
            Intent i = new Intent(context, SchoolloopActivity.class);
            context.startActivity(i);
            return true;
        }
        if(url.equals("kevin://openBellScheduleActivity")){
            Intent i = new Intent(context, BellScheduleActivity.class);
            context.startActivity(i);
            return true;
        }
        
        if(url.equals("kevin://openDailyBulletinActivity")){
            Intent i = new Intent(context, DailyBulletin.class);
            context.startActivity(i);
            return true;
        }
        
        if(url.equals("kevin://openFacebookGroupsActivity")){
            Intent i = new Intent(context, FacebookGroupsActivity.class);
            context.startActivity(i);
            return true;
        }
        if(url.equals("kevin://openAboutDeveloperActivity")){
            Intent i = new Intent(context, AboutDeveloper.class);
            context.startActivity(i);
            return true;
        }
        if(url.equals("http://www.gwhs-sfusd-ca.schoolloop.com/")){
            Intent i = new Intent(context, Home.class);
            context.startActivity(i);
            return true;
        }
        
        return super.shouldOverrideUrlLoading(view, url);
        
    }
    
    
}
