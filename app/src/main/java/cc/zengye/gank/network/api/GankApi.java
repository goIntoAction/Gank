package cc.zengye.gank.network.api;

import java.util.List;

import cc.zengye.gank.model.GankData;
import cc.zengye.gank.model.GankModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by fanny on 16/6/5.
 */
public interface GankApi {
    @GET("/api/day/{year}/{month}/{day}")
    Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("/api/data/{type}/{pagesize}/{page}")
    Observable<List<GankModel>> getSingleType(@Path("type")String type, @Path("pagesize")int pageSize
            ,@Path("page")int page);
}
