package com.wei.wimagepreviewlib;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.wimagepreviewlib.activity.ImagePreviewFragmentActivity;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.transformer.PageTransformer;
import com.wei.wimagepreviewlib.utils.KeyConst;
import com.wei.wimagepreviewlib.utils.WeakDataHolder;

import java.util.List;

/**
 * 图片预览构建类
 */
public class WImagePreviewBuilder {

    private Context mContext;
    private Intent intent;

    private WImagePreviewBuilder(@NonNull Context context) {
        this.mContext = context;
        intent = new Intent();
    }

    /**
     * 加载预览组件
     *
     * @param context
     * @return
     */
    public static WImagePreviewBuilder load(@NonNull Context context) {
        return new WImagePreviewBuilder(context);
    }

    /**
     * 加载预览组件
     *
     * @param fragment
     * @return
     */
    public static WImagePreviewBuilder load(@NonNull Fragment fragment) {
        return new WImagePreviewBuilder(fragment.getContext());
    }

    /**
     * 设置图片集合数据
     *
     * @param imgList 图片集合
     * @return
     */
    public <T> WImagePreviewBuilder setData(List<T> imgList) {
        if (imgList == null || imgList.isEmpty()) {
            return this;
        }

        // 改用WeakReference存储图片路径集合，防止因图片太多导致intent在传输时崩溃
        WeakDataHolder.getInstance().saveData(KeyConst.IMAGE_URI_LIST, imgList);

        return this;
    }

    /**
     * 设置图片下标定位
     *
     * @param position
     * @return
     */
    public WImagePreviewBuilder setPosition(int position) {
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
    public WImagePreviewBuilder setOrientation(int orientation) {
        intent.putExtra(KeyConst.VIEWPAGER2_ORIENTATION, orientation);
        return this;
    }

    /**
     * 设置是否允许滑动ViewPager2
     *
     * @param isAllowImage true：允许；false：不允许
     * @return
     */
    public WImagePreviewBuilder setAllowMove(boolean isAllowImage) {
        intent.putExtra(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, isAllowImage);
        return this;
    }

    /**
     * 设置是否全屏
     *
     * @param isFullscreen 是否全屏
     * @return GPreviewBuilder
     */
    public WImagePreviewBuilder setFullscreen(boolean isFullscreen) {
        intent.putExtra(KeyConst.IS_FULLSCREEN, isFullscreen);
        return this;
    }

    /**
     * 是否显示关闭按钮
     *
     * @param isShowClose
     * @return
     */
    public WImagePreviewBuilder setShowClose(boolean isShowClose) {
        intent.putExtra(KeyConst.IS_SHOW_CLOSE, isShowClose);
        return this;
    }

    /**
     * 设置ViewPager2页面间距 <br/>
     * <b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，<br/>
     * 如果两个都设置，则只有<b>setPageTransformer()</b>生效
     *
     * @param pageMargin
     * @return
     */
    public WImagePreviewBuilder setPageMargin(int pageMargin) {
        intent.putExtra(KeyConst.VIEW_PAGER2_PAGE_MARGIN, pageMargin);
        return this;
    }

    /**
     * 设置页面切换动画<br/>
     * <b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，<br/>
     * 如果两个都设置，则只有<b>setPageTransformer()</b>生效
     *
     * @param pageTransformer 动画类型;详见{@link PageTransformer}中的静态变量
     * @return
     */
    public WImagePreviewBuilder setPageTransformer(int pageTransformer) {
        setPageTransformer(PageTransformer.init(pageTransformer));
        return this;
    }

    /**
     * 设置页面切换动画<br/>
     * <b>setPageMargin()</b>和<b>setPageTransformer()</b>只能设置其中一个，<br/>
     * 如果两个都设置，则只有<b>setPageTransformer()</b>生效
     *
     * @param pageTransformer 动画类型;详见{@link PageTransformer}，
     *                        或者可以通过实现 {@link ViewPager2.PageTransformer}接口进行自定义动画
     * @return
     */
    public WImagePreviewBuilder setPageTransformer(ViewPager2.PageTransformer pageTransformer) {
        WeakDataHolder.getInstance().saveData(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER, pageTransformer);
        return this;
    }

    /**
     * 监听器
     *
     * @param listener
     * @return
     */
    public WImagePreviewBuilder setOnPageListener(OnPageListener listener) {
        WeakDataHolder.getInstance().saveData(KeyConst.ON_PAGE_LISTENER, listener);
        return this;
    }

    public void start() {
        intent.setClass(mContext, ImagePreviewFragmentActivity.class);
        mContext.startActivity(intent);
        intent = null;
        mContext = null;
    }

}
