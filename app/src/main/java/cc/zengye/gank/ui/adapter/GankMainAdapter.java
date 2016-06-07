package cc.zengye.gank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cc.zengye.gank.R;
import cc.zengye.gank.model.GankModel;
import cc.zengye.gank.ui.activity.BaseActivity;
import cc.zengye.gank.utils.Utils;

/**
 * Created by fanny on 16/6/5.
 */
public class GankMainAdapter extends RecyclerView.Adapter<GankMainAdapter.ViewHolderItem> {
    public static final int ITEM_TYPE_IMAGE = 0;
    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_CATEGORY = 2;
    private BaseActivity mActivity;
    private List<GankModel> mModels;
    public GankMainAdapter(BaseActivity baseActivity){
        mActivity = baseActivity;
        mModels = new ArrayList<>();
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        GankModel gankModel = mModels.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    abstract static class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }

        abstract void bindItem(Context context, GankModel gankModel);
    }



    class ViewHolderNormal extends ViewHolderItem {
        private TextView mDesc;
        private TextView mPublishTime;
        private TextView mWho;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            mDesc = (TextView) itemView.findViewById(R.id.desc);
            mPublishTime = (TextView) itemView.findViewById(R.id.publishedAt);
            mWho = (TextView) itemView.findViewById(R.id.who);
        }

        @Override
        void bindItem(Context context, GankModel gankModel) {
            mDesc.setText(gankModel.desc);
            mPublishTime.setText(Utils.formatDate(gankModel.publishedAt));
            mWho.setText(gankModel.who);
        }
    }

    class ViewHolderCategory extends ViewHolderItem {
        private TextView mCategory;

        public ViewHolderCategory(View itemView) {
            super(itemView);
            mCategory = (TextView) itemView.findViewById(R.id.tv_category);
        }

        @Override
        void bindItem(Context context, GankModel gankModel) {
            mCategory.setText(gankModel.type);
        }
    }

    class ViewHolderImage extends ViewHolderItem {
        private ImageView mImageView;
        private TextView mImageName;

        public ViewHolderImage(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            mImageName = (TextView) itemView.findViewById(R.id.tv_image_name);
        }

        @Override
        void bindItem(Context context, GankModel gankModel) {
            RequestManager rm = null;
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                rm = Glide.with(activity);
            } else {
                rm = Glide.with(context);
            }

            rm.load(gankModel.url).into(mImageView);
            mImageName.setText(Utils.formatDate(gankModel.publishedAt));
        }
    }

}
