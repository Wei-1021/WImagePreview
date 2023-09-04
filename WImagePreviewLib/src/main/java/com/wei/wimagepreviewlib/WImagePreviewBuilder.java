package com.wei.wimagepreviewlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.wimagepreviewlib.activity.ImagePreviewFragmentActivity;
import com.wei.wimagepreviewlib.exception.WImagePreviewException;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.transformer.PageTransformer;
import com.wei.wimagepreviewlib.utils.KeyConst;
import com.wei.wimagepreviewlib.utils.WAnim;
import com.wei.wimagepreviewlib.utils.WeakDataHolder;

import java.util.List;

/**
 * 图片预览构建类
 */
public class WImagePreviewBuilder {

    private Context mContext;
    private Intent intent;
    private int imgListSize = 0;
    private int enterAnim;
    private int exitAnim;
    private final WeakDataHolder weakDataHolder;

    private WImagePreviewBuilder(@NonNull Context context) {
        this.mContext = context;
        this.intent = new Intent();
        this.weakDataHolder = WeakDataHolder.getInstance();
    }

    /**
     * 加载预览组件
     *
     * @param context
     * @return WImagePreviewBuilder
     */
    public static WImagePreviewBuilder load(@NonNull Context context) {
        return new WImagePreviewBuilder(context);
    }

    /**
     * 加载预览组件
     *
     * @param fragment
     * @return WImagePreviewBuilder
     */
    public static WImagePreviewBuilder load(@NonNull Fragment fragment) {
        return new WImagePreviewBuilder(fragment.getContext());
    }

    /**
     * 设置图片集合数据
     *
     * @param imgList 图片集合
     * @return WImagePreviewBuilder
     */
    public <T> WImagePreviewBuilder setData(List<T> imgList) {
        if (imgList == null || imgList.isEmpty()) {
            return this;
        }

        imgListSize = imgList.size();
        weakDataHolder.saveData(KeyConst.IMAGE_URI_LIST, imgList);

        return this;
    }

    /**
     * 设置图片下标定位
     *
     * @param position
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setPosition(int position) {
        if (position < 0) {
            String msg = mContext.getString(R.string.exception_array_out_of_bounds);
            String indexMsg = mContext.getString(R.string.exception_array_index, position);
            throw new WImagePreviewException(msg + indexMsg, new ArrayIndexOutOfBoundsException(-1));
        } else if (position > imgListSize) {
            String msg = mContext.getString(R.string.exception_array_out_of_bounds);
            String indexMsg = mContext.getString(R.string.exception_array_index_size, position, imgListSize);
            throw new WImagePreviewException(msg + indexMsg, new ArrayIndexOutOfBoundsException());
        }

        weakDataHolder.saveData(KeyConst.VIEWPAGER2_ITEM_POSITION, position);
        return this;
    }

    /**
     * 设置是否显示数字指示器
     *
     * @param isShowNumIndicator
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setShowNumIndicator(boolean isShowNumIndicator) {
        weakDataHolder.saveData(KeyConst.VIEWPAGER2_SHOW_NUM_INDICATOR, isShowNumIndicator);
        return this;
    }

    /**
     * 设置滚动方向
     *
     * @param orientation 方向； <br/>
     *                    水平滚动：{@link androidx.viewpager2.widget.ViewPager2#ORIENTATION_HORIZONTAL}；<br/>
     *                    垂直滚动：{@link androidx.viewpager2.widget.ViewPager2#ORIENTATION_VERTICAL}
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setOrientation(int orientation) {
        if (orientation != ViewPager2.ORIENTATION_HORIZONTAL && orientation != ViewPager2.ORIENTATION_VERTICAL) {
            String msg = mContext.getString(R.string.exception_illegal_argument, String.valueOf(orientation));
            throw new WImagePreviewException(msg, new IllegalArgumentException(String.valueOf(orientation)));
        }

        weakDataHolder.saveData(KeyConst.VIEWPAGER2_ORIENTATION, orientation);
        return this;
    }

    /**
     * 设置是否允许滑动ViewPager2
     *
     * @param isAllowImage true：允许；false：不允许
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setAllowMove(boolean isAllowImage) {
        weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, isAllowImage);
        return this;
    }

    /**
     * 设置是否全屏
     *
     * @param isFullscreen 是否全屏
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setFullscreen(boolean isFullscreen) {
        weakDataHolder.saveData(KeyConst.IS_FULLSCREEN, isFullscreen);
        return this;
    }

    /**
     * 是否显示关闭按钮
     *
     * @param isShowClose
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setShowClose(boolean isShowClose) {
        weakDataHolder.saveData(KeyConst.IS_SHOW_CLOSE, isShowClose);
        return this;
    }

    /**
     * 设置是否允许无限循环滑动
     *
     * @param isInfiniteLoop 是否允许无限循环滑动
     * @return
     */
    public WImagePreviewBuilder setInfiniteLoop(boolean isInfiniteLoop) {
        weakDataHolder.saveData(KeyConst.VIEWPAGER2_IS_INFINITE_LOOP, isInfiniteLoop);
        return this;
    }

    /**
     * 设置ViewPager2页面间距。<br/>
     * {@link WImagePreviewBuilder#setPageMargin}和
     * {@link WImagePreviewBuilder#setPageTransformer}只能设置其中一个，
     * 如果两个都设置，则只有{@link WImagePreviewBuilder#setPageTransformer}生效
     *
     * @param pageMargin
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setPageMargin(int pageMargin) {
        weakDataHolder.saveData(KeyConst.VIEW_PAGER2_PAGE_MARGIN, pageMargin);
        return this;
    }

    /**
     * 设置页面切换动画。<br/>
     * {@link WImagePreviewBuilder#setPageMargin}和
     * {@link WImagePreviewBuilder#setPageTransformer}只能设置其中一个，
     * 如果两个都设置，则只有{@link WImagePreviewBuilder#setPageTransformer}生效
     *
     * @param pageTransformer 动画类型;详见{@link com.wei.wimagepreviewlib.transformer.PageTransformer}中的常量
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setPageTransformer(int pageTransformer) {
        setPageTransformer(PageTransformer.initPageTransformer(pageTransformer));
        return this;
    }

    /**
     * 设置页面切换动画。<br/>
     * {@link WImagePreviewBuilder#setPageMargin}和
     * {@link WImagePreviewBuilder#setPageTransformer}只能设置其中一个，
     * 如果两个都设置，则只有{@link WImagePreviewBuilder#setPageTransformer}生效
     *
     * @param pageTransformer 动画类型;详见{@link com.wei.wimagepreviewlib.transformer.PageTransformer}，
     *                        或者可以通过实现 {@link ViewPager2.PageTransformer}接口进行自定义动画
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setPageTransformer(ViewPager2.PageTransformer pageTransformer) {
        weakDataHolder.saveData(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER, pageTransformer);
        return this;
    }

    /**
     * 设置预加载个数
     *
     * @param offscreenPageLimit 预加载个数
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setOffscreenPageLimit(int offscreenPageLimit) {
        weakDataHolder.saveData(KeyConst.VIEWPAGER2_OFFSCREEN_PAGE_LIMIT, offscreenPageLimit);
        return this;
    }

    /**
     * 组件进出场动画。预设了八种进出场动画的组合，
     * 若这八种预设的动画效果不满足您的需求，可以使用{@link WImagePreviewBuilder#setInAnim}和
     * {@link WImagePreviewBuilder#setOutAnim}自由搭配。<br/>
     * <table class="striped">
     * <thead>
     *     <tr style="vertical-align:top">
     *         <th scope="col">常量参数</th>
     *         <th scope="col">描述</th>
     *     </tr>
     * </thead>
     * <tbody>
     *     <tr><th scope="row">ALL_BOTTOM_IN_TOP_OUT</th><td>下进上出</td></tr>
     *     <tr><th scope="row">ALL_TOP_IN_BOTTOM_OUT</th><td>上进下出</td></tr>
     *     <tr><th scope="row">ALL_LEFT_IN_RIGHT_OUT</th><td>左进右出</td></tr>
     *     <tr><th scope="row">ALL_RIGHT_IN_LEFT_OUT</th><td>右进左出</td></tr>
     *     <tr><th scope="row">ALL_CENTER_ZOOM</th><td>中间缩放</td></tr>
     *     <tr><th scope="row">ALL_OUTSIDE_SCALE</th><td>外围缩放（从外往里）</td></tr>
     *     <tr><th scope="row">ALL_LTOP_IN_RBOTTOM_OUT</th><td>左上进，右下出</td></tr>
     *     <tr><th scope="row">ALL_ROTATE_IN_FADE_OUT</th><td>旋转缩放进，透明淡出</td></tr>
     * </tbody>
     * </table>
     *
     * @param anim 进出场动画;详见{@link WAnim}类中ALL_*格式的常量
     */
    public WImagePreviewBuilder setAnim(int anim) {
        switch (anim) {
            case WAnim.ALL_BOTTOM_IN_TOP_OUT:
                setInAnim(WAnim.IN_BOTTOM_TO_TOP, WAnim.OUT_BOTTOM_TO_TOP);
                setOutAnim(WAnim.IN_TOP_TOP_BOTTOM, WAnim.OUT_TOP_TO_BOTTOM);
                break;
            case WAnim.ALL_TOP_IN_BOTTOM_OUT:
                setInAnim(WAnim.IN_TOP_TOP_BOTTOM, WAnim.OUT_TOP_TO_BOTTOM);
                setOutAnim(WAnim.IN_BOTTOM_TO_TOP, WAnim.OUT_BOTTOM_TO_TOP);
                break;
            case WAnim.ALL_LEFT_IN_RIGHT_OUT:
                setInAnim(WAnim.IN_LEFT_TO_RIGHT, WAnim.OUT_LEFT_TO_RIGHT);
                setOutAnim(WAnim.IN_RIGHT_TO_LEFT, WAnim.OUT_RIGHT_TO_LEFT);
                break;
            case WAnim.ALL_RIGHT_IN_LEFT_OUT:
                setInAnim(WAnim.IN_RIGHT_TO_LEFT, WAnim.OUT_RIGHT_TO_LEFT);
                setOutAnim(WAnim.IN_LEFT_TO_RIGHT, WAnim.OUT_LEFT_TO_RIGHT);
                break;
            case WAnim.ALL_CENTER_ZOOM:
                setInAnim(WAnim.IN_CENTER_ZOOM, WAnim.OUT_FADE);
                setOutAnim(WAnim.IN_OUTSIDE_SCALE, WAnim.OUT_CENTER_ZOOM);
                break;
            case WAnim.ALL_OUTSIDE_SCALE:
                setInAnim(WAnim.IN_OUTSIDE_SCALE, WAnim.OUT_FADE);
                setOutAnim(WAnim.IN_OUTSIDE_SCALE, WAnim.OUT_CENTER_ZOOM);
                break;
            case WAnim.ALL_LTOP_IN_RBOTTOM_OUT:
                setInAnim(WAnim.IN_LEFT_TOP_ZOOM, WAnim.OUT_RIGHT_BOTTOM);
                setOutAnim(WAnim.IN_LEFT_TOP_ZOOM, WAnim.OUT_RIGHT_BOTTOM);
                break;
            case WAnim.ALL_ROTATE_IN_FADE_OUT:
                setInAnim(WAnim.IN_ROTATE_SCALE_MOVE, WAnim.OUT_FADE);
                setOutAnim(WAnim.IN_ROTATE_SCALE_MOVE, WAnim.OUT_FADE);
                break;
        }

        return this;
    }

    /**
     * 设置预览组件进场动画
     *
     * @param enterAnim 目标页面的进场动画;详见{@link WAnim}类中IN_*格式的常量
     * @param exitAnim  当前页面的退场动画;详见{@link WAnim}类中OUT_*格式的常量
     * @return
     */
    public WImagePreviewBuilder setInAnim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    /**
     * 设置预览组件退场动画
     *
     * @param enterAnim 上一个页面的进场动画;详见{@link WAnim}类中IN_*格式的常量
     * @param exitAnim  当前页面的退场动画;详见{@link WAnim}类中OUT_*格式的常量
     * @return
     */
    public WImagePreviewBuilder setOutAnim(int enterAnim, int exitAnim) {
        weakDataHolder.saveData(KeyConst.PAGER2_PAGE_OUT_ENTER_ANIM, enterAnim);
        weakDataHolder.saveData(KeyConst.PAGER2_PAGE_OUT_EXIT_ANIM, exitAnim);
        return this;
    }

    /**
     * 监听器
     *
     * @param listener
     * @return WImagePreviewBuilder
     */
    public WImagePreviewBuilder setOnPageListener(OnPageListener listener) {
        weakDataHolder.saveData(KeyConst.ON_PAGE_LISTENER, listener);
        return this;
    }

    public void start() {
        intent.setClass(mContext, ImagePreviewFragmentActivity.class);
        mContext.startActivity(intent);
        if (enterAnim != 0 && exitAnim != 0) {
            ((Activity) mContext).overridePendingTransition(enterAnim, exitAnim);
        }
        intent = null;
        mContext = null;
    }

}
