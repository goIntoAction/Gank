package cc.zengye.gank.network;

/**
 * Created by fanny on 16/6/5.
 */
public class ApiManager {

    private ApiManager () {

    }

    public ApiManager getInstance(){
        return Holder.INSTANCE;
    }

    static class Holder {
        private static ApiManager INSTANCE = new ApiManager();
    }
}
