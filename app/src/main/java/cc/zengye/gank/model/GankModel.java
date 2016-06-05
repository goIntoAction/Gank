package cc.zengye.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by fanny on 16/6/5.
 */
public class GankModel extends BaseModel {
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String type;
    public String url;
    public boolean used;
    public String who;

}
