package cc.zengye.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fanny on 16/6/5.
 */
public class GankData extends BaseData {
    public @SerializedName("category") List<String> category;
    public @SerializedName("results") Result results;

    public class Result {
        @SerializedName("Android") public List<GankModel> androidList;
        @SerializedName("休息视频") public List<GankModel> restList;
        @SerializedName("iOS") public List<GankModel> iOSList;
        @SerializedName("福利") public List<GankModel> welfareList;
//        @SerializedName("拓展资源") public List<GankModel> extendResList;
//        @SerializedName("瞎推荐") public List<GankModel> recommendList;
//        @SerializedName("App") public List<GankModel> appList;
        @SerializedName("前端") public List<GankModel> webList;
    }
}
