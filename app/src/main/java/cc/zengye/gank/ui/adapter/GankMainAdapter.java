package cc.zengye.gank.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

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
    private OnItemClickListener mOnItemClickListener;

    public GankMainAdapter(BaseActivity baseActivity, OnItemClickListener onItemClickListener){
        mActivity = baseActivity;
        mModels = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_TYPE_NORMAL:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_gank_normal_linear,parent, false);
                return new ViewHolderNormal(view, mOnItemClickListener);
            case ITEM_TYPE_CATEGORY:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_gank_category, parent, false);
                return new ViewHolderCategory(view);
            case ITEM_TYPE_IMAGE:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_gank_image, parent, false);
                return new ViewHolderImage(view, mOnItemClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        GankModel gankModel = mModels.get(position);
        holder.bindItem(mActivity, gankModel);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    abstract static class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }

        abstract void bindItem(Context context, GankModel gankModel);
    }

    @Override
    public int getItemViewType(int position) {
        GankModel model = mModels.get(position);
        if("福利".equals(model.type)) {
            return ITEM_TYPE_IMAGE;
        } else if("category".equals(model.type)) {
            return ITEM_TYPE_CATEGORY;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    class ViewHolderNormal extends ViewHolderItem implements View.OnClickListener {
        private TextView mDesc;
        private TextView mPublishTime;
        private TextView mWho;
        private GankModel mGankModel;
        private OnItemClickListener mOnItemClickListener;

        public ViewHolderNormal(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDesc = (TextView) itemView.findViewById(R.id.desc);
            mPublishTime = (TextView) itemView.findViewById(R.id.publishedAt);
            mWho = (TextView) itemView.findViewById(R.id.who);
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        void bindItem(Context context, GankModel gankModel) {
            mGankModel = gankModel;
            mDesc.setText(gankModel.desc);
            mPublishTime.setText(Utils.formatDate(gankModel.publishedAt));
            mWho.setText(gankModel.who);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null && mGankModel != null) {
                mOnItemClickListener.onItemClickListener(mGankModel,v);
            }
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
            mCategory.setText(gankModel.desc);
        }
    }

    class ViewHolderImage extends ViewHolderItem  implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mImageName;
        private GankModel mGankModel;
        private OnItemClickListener mOnItemClickListener;

        public ViewHolderImage(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_image);
            mImageName = (TextView) itemView.findViewById(R.id.tv_image_name);
            mImageView.setOnClickListener(this);
            mOnItemClickListener = onItemClickListener;
        }

        @Override
        void bindItem(Context context, GankModel gankModel) {
            mGankModel = gankModel;
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

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null && mGankModel != null) {
                mOnItemClickListener.onItemClickListener(mGankModel,v);
            }
        }
    }

    public void setData(List<GankModel> models){
        mModels.clear();
        handleData(models);
    }

    public void appendData(List<GankModel> models){
        handleData(models);

    }

    public void handleData(List<GankModel> models) {
        String lastCategory = "";
        for (GankModel model : models) {
            if (!"福利".equals(model.type) && !lastCategory.equals(model.type)) {
                GankModel category = new GankModel();
                lastCategory = model.type;
                category.type = "category";
                category.desc = model.type;
                mModels.add(category);
            }
            mModels.add(model);
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClickListener(GankModel gank,View viewImage);
    }
}
