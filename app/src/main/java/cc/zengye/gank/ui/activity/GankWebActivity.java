package cc.zengye.gank.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import cc.zengye.gank.R;
import cc.zengye.gank.common.Constants;

public class GankWebActivity extends SwipeRefreshActivty {
    private WebView mGankWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(Constants.WEB_VIEW_URL);
        mGankWebView = (WebView) findViewById(R.id.wv_gank);
        mGankWebView.loadUrl(url);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_gank_web;
    }

    @Override
    public void onSwipeRefresh() {

    }
}
