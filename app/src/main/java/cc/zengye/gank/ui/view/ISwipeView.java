package cc.zengye.gank.ui.view;

import java.util.List;

import cc.zengye.gank.model.BaseModel;

/**
 * Created by zengye on 16/6/8.
 */
public interface ISwipeView<T extends BaseModel> extends IBaseView<T> {
    void getDataCompleted();
    void finishLoadMore();
    void showError(Throwable throwable);
    void showRefresh();
    void hideRefresh();
}
