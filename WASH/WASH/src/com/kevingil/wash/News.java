package com.kevingil.wash;

/*

public class News extends FeedActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   }
		   
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		setTitle("News");
		
		// RSS Feed for Eagle News 
		ImageButton btw_left = (ImageButton) findViewById(R.id.btw_back);
		btw_left.setOnClickListener(new OnClickListener() {
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
	public boolean isImageVisible() {
		return true;
	}

	@Override
	public boolean isDateVisible() {
		return true;
	}

	@Override
	public String getFeedUrl() {
		return "http://theeaglenews.org/feed/";
	}
}

*/

import com.tinymission.rss.FeedActivity;
import com.tinymission.rss.Item;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class News extends FeedActivity {
	
	ImageButton btn_left;
	Intent postIntent;
	Uri postUrl;
	final Context context = this;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
		   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
		        this.setTheme(R.style.Theme_Kevin);
		   } else {
			   getActionBar().hide();
		   }
		   
		   btn_left = (ImageButton) findViewById(R.id.btw_back);
		   btn_left.setOnClickListener(new OnClickListener() {
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
	protected void onFeedItemClick(Item item) {
		postUrl = Uri.parse(item.getLink());
		if (postUrl != null) {
			/*
			Intent intent = new Intent(Intent.ACTION_VIEW, url);
			startActivity(intent);
			*/
			
			// method from http://www.androidsnippets.com/how-to-pass-a-dynamic-url-to-a-webview-activity
            postIntent = new Intent(context, Post.class);
            postIntent.setData(Uri.parse(item.getLink()));
            startActivity(postIntent);
		}
	}
	
	@Override
	public int getListLayoutId() {
		return R.layout.feed;
	}
	
	@Override
	public int getItemLayoutId() {
		return R.layout.feed_list_item;
	}
	
	@Override
	public boolean isImageVisible() {
		return true;
	}

	@Override
	public boolean isDateVisible() {
		return true;
	}
	
	@Override
    public int showTitleAuthorOrBoth() {
        return 2;
    }

	@Override
	public String getFeedUrl() {
		return "http://theeaglenews.org/feed/";
	}
}