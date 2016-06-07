package cc.zengye.gank.presenter;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cc.zengye.gank.common.ThreadTransformer;
import cc.zengye.gank.model.GankData;
import cc.zengye.gank.model.GankModel;
import cc.zengye.gank.ui.view.IMainView;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zengye on 16/6/7.
 */
public class MainPresenter extends IBasePresenter<IMainView> {
    private Date mGankDate;//获取此日期的gank数据
    private List<GankModel> mGankModels = new ArrayList<>();
    private int mNoDataDay = 0;
    public MainPresenter(Activity activity, IMainView baseView) {
        super(activity, baseView);
    }

    public void getData(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mGankDate = date;
        mGankApi.getGankData(year, month, day)
                .compose(new ThreadTransformer())
                .map(new Func1<GankData, List<GankModel>>() {
                    @Override
                    public List<GankModel> call(GankData gankData) {
                        return handleResult(gankData.results);
                    }
                }).subscribe(new Subscriber<List<GankModel>>() {
                    @Override
                    public void onCompleted() {
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        mGankDate = calendar.getTime();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<GankModel> gankModels) {
                        if (gankModels.isEmpty()) {
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            getData(calendar.getTime());
                        } else {
                            mNoDataDay = 0;
                            mBaseView.loadData(gankModels);
                        }
                        mBaseView.getDataCompleted();
                    }
                });
    }

    private List<GankModel> handleResult(GankData.Result results) {
        mGankModels.clear();

        if (results.welfareList != null) mGankModels.addAll(0, results.welfareList);
        if (results.androidList != null) mGankModels.addAll(results.androidList);
        if (results.iOSList != null) mGankModels.addAll(results.iOSList);
        if (results.appList != null) mGankModels.addAll(results.appList);
        if (results.extendResList != null) mGankModels.addAll(results.extendResList);
        if (results.recommendList != null) mGankModels.addAll(results.recommendList);
        if (results.restList != null) mGankModels.addAll(results.restList);
        return mGankModels;
    }
}
