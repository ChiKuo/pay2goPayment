package chikuo.tw.pay2gopayment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by edward_chiang on 15/5/12.
 */
public class WebViewActivity extends ActionBarActivity {

    /**
     * Test credit cardNo.
     * 4000-2222-2222-2222
     */

    private WebView webView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        webView = (WebView)findViewById(R.id.web_view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        // Let the webView can zoom
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                updateRefreshItem(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                updateRefreshItem(false);
            }
        });

        String urlIntent = getIntent().getStringExtra("webUrl");
        String htmlIntent = getIntent().getStringExtra("webHtml");

        if (urlIntent != null && urlIntent.length() > 0) {
            webView.loadUrl(Uri.parse(urlIntent).toString());
        }
        if (htmlIntent != null && htmlIntent.length() > 0){
            webView.loadDataWithBaseURL(null, htmlIntent, "text/html", "UTF-8", null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web, menu);
        this.menu = menu;
        return true;
    }

    public void updateRefreshItem(boolean isLoading) {
        if (menu != null) {
            MenuItem refreshItem = menu.findItem(R.id.action_reload);
            if (isLoading) {
                MenuItemCompat.setActionView(refreshItem, R.layout.indeterminate_progress_action);
            } else {
                MenuItemCompat.setActionView(refreshItem, null);
            }
        }
    }
}
