package com.kevingil.gwhs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.webkit.WebView;




public class DailyBulletin extends SherlockActivity  { 
	 
	Document doc;
	WebView cardapio;
	Elements elements;
	static final String BLOG_URL = "";
    public void onCreate(Bundle savedInstanceState) {
    	if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
	     this.setTheme(com.actionbarsherlock.R.style.Theme_Kevin);}
    	// that is to enable theme in older phones
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_bulletin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // this is  test of jsoup 
        // this changes the background color of the action bar
        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.black_textured));
        // set layout view
    // process
        try {
            ((WebView)findViewById(R.id.webviewDailyBulletin)).loadData(getBlogStats(), "text/html; charset=UTF-8", null);
        } catch (Exception ex) {
            ((WebView)findViewById(R.id.webviewDailyBulletin)).loadData("Error", "text; charset=UTF-8", null);
        }

    }
    protected String getBlogStats() throws Exception {
        String result = "";
        // get html document structure
        Document document = Jsoup.connect(BLOG_URL).get();
        // selector query
        Elements nodeBlogStats = document.select("td.tableheader");
        // check results
        if(nodeBlogStats.size() > 0) {
            // get value
            result = nodeBlogStats.get(0).text();
        }

        // return
        return result;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
 


}
