package cc.zengye.gank.common;

import cc.zengye.gank.model.GankData;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zengye on 16/6/7.
 */
public class ThreadTransformer implements Observable.Transformer<GankData, GankData> {
    @Override
    public Observable<GankData> call(Observable<GankData> gankDataObservable) {
        return gankDataObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
