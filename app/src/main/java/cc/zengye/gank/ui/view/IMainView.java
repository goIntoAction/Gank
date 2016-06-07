package cc.zengye.gank.ui.view;

import java.util.List;

import cc.zengye.gank.model.BaseModel;

/**
 * Created by zengye on 16-6-7.
 */
public interface IMainView<T extends BaseModel> extends IBaseView {

    public void appendData(List<T> data);
}
