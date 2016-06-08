package cc.zengye.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by fanny on 16/6/5.
 */
public class GankModel extends BaseModel {
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;
}
