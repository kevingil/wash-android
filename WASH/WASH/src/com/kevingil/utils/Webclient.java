package com.kevingil.utils;

import com.kevingil.wash.Schedule;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webclient extends WebViewClient {
    
    private Context context;

    public Webclient(Context context) {
        this.context = context;
    }
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	// the url can be as random as "kevin://whatever" this url has
    	//to be the url that the html link(that will open the activity) has
    	
        if(url.equals("kevin://schedule")){
            Intent i = new Intent(view.getContext(), Schedule.class);
            context.startActivity(i);
            return true;
        }
        
        return super.shouldOverrideUrlLoading(view, url);
        
    }
    
    
}
