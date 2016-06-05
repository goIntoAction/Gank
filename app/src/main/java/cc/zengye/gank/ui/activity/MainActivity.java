package cc.zengye.gank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cc.zengye.gank.R;

public class MainActivity extends SwipeRefreshActivty {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public int getContentView() {
        return 0;
    }

    @Override
    public void onRefresh() {

    }
}
