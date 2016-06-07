package cc.zengye.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cc.zengye.gank.R;
import cc.zengye.gank.model.GankModel;

/**
 * Created by fanny on 16/6/5.
 */
public class GankMainAdapter extends RecyclerView.Adapter<GankMainAdapter.ViewHolderItem>{


    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {

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
        public ViewHolderNormal(View itemView){
            super(itemView);
            mDesc = (TextView) itemView.findViewById(R.id.desc);
            mPublishTime = (TextView) itemView.findViewById(R.id.publishedAt);
            mWho = (TextView) itemView.findViewById(R.id.who);
        }
        @Override
        void bindItem(Context context, GankModel gankModel) {

        }
    }
}
