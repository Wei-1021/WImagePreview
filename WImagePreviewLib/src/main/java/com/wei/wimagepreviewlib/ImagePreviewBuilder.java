package com.wei.wimagepreviewlib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wei.wimagepreviewlib.activity.ImagePreviewFragmentActivity;
import com.wei.wimagepreviewlib.utils.KeyConst;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览构建类
 */
public class ImagePreviewBuilder {

    private Context mContext;
    private Intent intent;

    private ImagePreviewBuilder(@NonNull Context context) {
        this.mContext = context;
        intent = new Intent();
    }

    /**
     * 加载预览组件
     *
     * @param context
     * @return
     */
    public static ImagePreviewBuilder load(@NonNull Context context) {
        return new ImagePreviewBuilder(context);
    }

    /**
     * 加载预览组件
     *
     * @param fragment
     * @return
     */
    public static ImagePreviewBuilder load(@NonNull Fragment fragment) {
        return new ImagePreviewBuilder(fragment.getContext());
    }

    /**
     * 设置图片集合数据
     *
     * @param imgList 图片集合
     * @return
     */
    public <T> ImagePreviewBuilder setData(List<T> imgList) {
        if (imgList == null || imgList.isEmpty()) {
            return this;
        }

        if (imgList.get(0) instanceof Uri) {
            intent.putParcelableArrayListExtra(KeyConst.IMAGE_URI_LIST, new ArrayList<Parcelable>((List<Uri>) imgList));
        } else if (imgList.get(0) instanceof String) {
            List<Uri> uriList = new ArrayList<>();
            for (T img : imgList) {
                uriList.add(Uri.parse((String) img));
            }
            intent.putParcelableArrayListExtra(KeyConst.IMAGE_URI_LIST, new ArrayList<Parcelable>(uriList));
        }

        return this;
    }

    /**
     * 设置图片下标定位
     *
     * @param position
     * @return
     */
    public ImagePreviewBuilder setPosition(int position) {
        intent.putExtra(KeyConst.VIEWPAGER2_ITEM_POSITION, position);
        return this;
    }

    /**
     * 设置滚动方向
     *
     * @param orientation 方向； <br/>
     *                    水平滚动：{@code ViewPager2.ORIENTATION_HORIZONTAL}；<br/>
     *                    垂直滚动：{@code ViewPager2.ORIENTATION_VERTICAL}
     * @return
     */
    public ImagePreviewBuilder setOrientation(int orientation) {
        intent.putExtra(KeyConst.VIEWPAGER2_ORIENTATION, orientation);
        return this;
    }

    /**
     * 设置是否允许滑动ViewPager2
     *
     * @param isAllowImage true：允许；false：不允许
     * @return
     */
    public ImagePreviewBuilder setAllowMove(boolean isAllowImage) {
        intent.putExtra(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, isAllowImage);
        return this;
    }

    /**
     * 设置是否全屏
     *
     * @param isFullscreen 单位毫秒
     * @return GPreviewBuilder
     */
    public ImagePreviewBuilder setFullscreen(boolean isFullscreen) {
        intent.putExtra(KeyConst.IS_FULLSCREEN, isFullscreen);
        return this;
    }

    /**
     * 是否显示关闭按钮
     *
     * @param isShowClose
     * @return
     */
    public ImagePreviewBuilder setShowClose(boolean isShowClose) {
        intent.putExtra(KeyConst.IS_SHOW_CLOSE, isShowClose);
        return this;
    }

    /**
     * 设置ViewPager2页面间距
     *
     * @param pageTransformer
     * @return
     */
    public ImagePreviewBuilder setPageTransformer(int pageTransformer) {
        intent.putExtra(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER, pageTransformer);
        return this;
    }

    public void start() {
        intent.setClass(mContext, ImagePreviewFragmentActivity.class);
        mContext.startActivity(intent);
        intent = null;
        mContext = null;
    }

}
