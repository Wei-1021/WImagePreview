package com.wei.wimagepreviewlib.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.wight.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览ViewPager2适配器
 *
 * @author weizhanjie
 */
public class ImagePreviewAdapter extends RecyclerView.Adapter<ImagePreviewAdapter.imagePreviewAdapterHolder> {

    private Context mContext;

    private List<Object> mImageList = new ArrayList<>();

    public ImagePreviewAdapter(Context context, List<Object> imageList) {
        this.mImageList = imageList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public imagePreviewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new imagePreviewAdapterHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(R.layout.w_item_image_preview, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull imagePreviewAdapterHolder holder, int position) {
        Glide.with(mContext)
                .load(mImageList.get(position))
                .into(holder.mImageView);

        holder.mImageView.setOnLongClickListener(v -> {
            Log.i("TAG", "onLongClick: ");
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return mImageList == null ? 0 : mImageList.size();
    }

    static class imagePreviewAdapterHolder extends RecyclerView.ViewHolder {
        ZoomImageView mImageView;

        RelativeLayout mContainer;

        public imagePreviewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.item_image_preview_container);
            mImageView = itemView.findViewById(R.id.item_image_preview_view);
        }
    }

}
