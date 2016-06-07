package cc.zengye.gank.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cc.zengye.gank.network.api.GankApi;
import cc.zengye.gank.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by fanny on 16/6/5.
 */
public class ApiManager {
    final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
    final GankApi mGankApi;

    private ApiManager () {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.GANK_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mGankApi = retrofit.create(GankApi.class);
    }

    public static final ApiManager getInstance(){
        return Holder.INSTANCE;
    }

    static class Holder {
        private static ApiManager INSTANCE = new ApiManager();
    }

    public GankApi getGankApi() {
        return mGankApi;
    }
}
