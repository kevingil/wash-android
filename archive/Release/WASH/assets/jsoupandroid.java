package pete.android.study;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 

public class JSoupStudyActivity extends Activity {

    // blog url

    static final String BLOG_URL = "http://xjaphx.wordpress.com/";

 

    @Override

    public void onCreate(Bundle savedInstanceState) {

        // set layout view

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

 
    // process

        try {

            ((TextView)findViewById(R.id.tv)).setText(getBlogStats());

        } catch (Exception ex) {

            ((TextView)findViewById(R.id.tv)).setText("Error");

        }

    }

 

    protected String getBlogStats() throws Exception {

        String result = "";

        // get html document structure
     Document document = Jsoup.connect(BLOG_URL).get();

        // selector query

        Elements nodeBlogStats = document.select("div#blog-stats ul li");

        // check results

        if(nodeBlogStats.size() > 0) {
            // get value

            result = nodeBlogStats.get(0).text();

        }

 

        // return
        return result;

    }

}
