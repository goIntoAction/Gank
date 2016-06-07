package cc.zengye.gank.presenter;

import android.app.Activity;

import cc.zengye.gank.network.ApiManager;
import cc.zengye.gank.network.api.GankApi;
import cc.zengye.gank.ui.view.IBaseView;

/**
 * Created by zengye on 16/6/7.
 */
public class IBasePresenter<T extends IBaseView> {
    public Activity mActivity;
    public T mBaseView;
    public GankApi mGankApi;
    public IBasePresenter(Activity activity, T baseView) {
        this.mBaseView = baseView;
        this.mActivity = activity;
        mGankApi = ApiManager.getInstance().getGankApi();
    }
}
