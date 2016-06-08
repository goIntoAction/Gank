package cc.zengye.gank.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import cc.zengye.gank.R;
import cc.zengye.gank.model.GankModel;
import cc.zengye.gank.presenter.MainPresenter;
import cc.zengye.gank.ui.adapter.GankMainAdapter;
import cc.zengye.gank.ui.view.IMainView;

public class MainActivity extends SwipeRefreshActivty implements IMainView<GankModel> {
    private RecyclerView mRecycleView;
    private GankMainAdapter mAdapter;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this, this);
    }

    private void initView() {
        initRecycleView();
    }

    private void initRecycleView() {
        mRecycleView = (RecyclerView) findViewById(R.id.rv_gank);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new GankMainAdapter(this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!mSwipeRefreshLayout.isRefreshing()) {
                    mPresenter.getMoreData();
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onSwipeRefresh() {
        getData();
    }


    @Override
    public void appendData(List<GankModel> data) {
        mAdapter.appendData(data);
    }

    @Override
    public void loadData(List<GankModel> data) {
        mAdapter.setData(data);
    }

    @Override
    public void getDataCompleted() {
        stopRefresh();
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

    @Override
    public void noMoreData() {
        Snackbar.make(mRecycleView, R.string.no_more_data, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mRecycleView.getLayoutManager()).smoothScrollToPosition(mRecycleView, null, 0);
                    }
                })
                .show();
    }

    @Override
    public void latestData() {
        stopRefresh();
        Toast.makeText(this,R.string.latest_data,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
            }
        }, 568);
        getData();
    }

    public void getData() {
        mPresenter.getData(new Date());
    }

    public abstract class EndlessRecyclerOnScrollListener extends
            RecyclerView.OnScrollListener {

        private int previousTotal = 0;
        private boolean loading = true;
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(
                LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading
                    && (totalItemCount - visibleItemCount) <= firstVisibleItem) {

                onLoadMore();
                loading = true;
            }
        }

        public abstract void onLoadMore();
    }
}
