package cc.zengye.gank.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import cc.zengye.gank.R;

/**
 * Created by fanny on 16/6/5.
 */
public abstract class SwipeRefreshActivty extends BaseActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        if(mSwipeRefreshLayout == null) {
            throw new RuntimeException("you must have a SwipeRefreshLayout");
        }
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
    }

    public abstract void onSwipeRefresh();

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    public void stopRefreshDelay(){
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefresh();
            }
        },1000);
    }

    public void stopRefresh(){
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void startRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
