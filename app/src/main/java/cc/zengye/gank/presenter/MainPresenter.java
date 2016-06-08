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
import cc.zengye.gank.utils.LogUtils;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zengye on 16/6/7.
 */
public class MainPresenter extends IBasePresenter<IMainView> {
    private Date mGankDate;//获取此日期的gank数据
    private Date mHistoryDate;
    private List<GankModel> mGankModels = new ArrayList<>();
    private int mNoDataDay = 0;

    public MainPresenter(Activity activity, IMainView baseView) {
        super(activity, baseView);
    }

    public void getData(final Date date) {
        if(compareDate(date)) {
            mBaseView.latestData();
            return;
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                mGankDate = calendar.getTime();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e);
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

    public void getMoreData(){
        if(mNoDataDay > 5) {
            mBaseView.noMoreData();
            return;
        }
        final Calendar calendar = Calendar.getInstance();
        if(mHistoryDate != null) {
            calendar.setTime(mHistoryDate);
        } else {
            calendar.setTime(mGankDate);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                mHistoryDate = calendar.getTime();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(e);
            }

            @Override
            public void onNext(List<GankModel> gankModels) {
                if (gankModels.isEmpty()) {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    mNoDataDay++;
                    getData(calendar.getTime());
                } else {
                    mNoDataDay = 0;
                    mBaseView.appendData(gankModels);
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
        if (results.webList != null) mGankModels.addAll(results.webList);
//        if (results.webList != null) mGankModels.addAll(results.appList);
//        if (results.extendResList != null) mGankModels.addAll(results.extendResList);
//        if (results.recommendList != null) mGankModels.addAll(results.recommendList);
        if (results.restList != null) mGankModels.addAll(results.restList);
        return mGankModels;
    }

    private boolean compareDate(Date date) {
        if(mGankDate == null) return false;

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(mGankDate);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
}
