package cc.zengye.gank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import cc.zengye.gank.R;
import cc.zengye.gank.model.GankModel;
import cc.zengye.gank.ui.view.IMainView;

public class MainActivity extends SwipeRefreshActivty implements IMainView<GankModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onRefresh() {

    }


    @Override
    public void appendData(List<GankModel> data) {

    }

    @Override
    public void loadData(List<GankModel> data) {

    }

    @Override
    public void getDataCompleted() {

    }

    @Override
    public void finishLoadMore() {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void hideRefresh() {

    }
}
