package com.kevingil.gwhs;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SchoolloopWebclientActivity extends WebViewClient {
    
    private Context context;

    public SchoolloopWebclientActivity(Context context) {
        this.context = context;
    }
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	// the url can be as random as "kevin://whatever" this url has
    	//to be the url that the html link(that will open the activity) has
        if(url.equals("http://www.gwhs-sfusd-ca.schoolloop.com/")){
            Intent i = new Intent(context, Home.class);
            context.startActivity(i);
            return true;
        }
        if(url.equals("https://www.gwhs-sfusd-ca.schoolloop.com/")){
            Intent i = new Intent(context, Home.class);
            context.startActivity(i);
            return true;
        }
        if(url.equals("https://www.gwhs-sfusd-ca.schoolloop.com/portal/logout")){
            Intent i = new Intent(context, Home.class);
            context.startActivity(i);
            return true;
        }
        
        return super.shouldOverrideUrlLoading(view, url);
        
    }
    
    
}
