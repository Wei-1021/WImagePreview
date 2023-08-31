package com.wei.wimagepreviewlib.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.adapter.ImagePreviewAdapter;
import com.wei.wimagepreviewlib.exception.WImagePreviewException;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.utils.KeyConst;
import com.wei.wimagepreviewlib.utils.WConfig;
import com.wei.wimagepreviewlib.utils.WTools;
import com.wei.wimagepreviewlib.utils.WeakDataHolder;

import java.util.List;

/**
 * 图片预览
 *
 * @author weizhanjie
 */
public class ImagePreviewFragmentActivity extends FragmentActivity {

    private Intent intent;

    /**
     * ViewPager2组件
     */
    private ViewPager2 viewPager2;
    /**
     * 底部数字指示器组件
     */
    private TextView textView;
    /**
     * 关闭按钮
     */
    private ImageView closeBtn;
    /**
     * 图片预览ViewPager2适配器
     */
    private ImagePreviewAdapter imagePreviewAdapter;
    /**
     * ViewPager2监听回调
     */
    private ViewPager2OnPageChangeCallback mOnPageChangeCallback;
    /**
     * 页面监听器
     */
    private OnPageListener onPageListener;
    /**
     * 当前图片所展示的定位
     */
    private int currentPosition = 0;
    /**
     * 当前图片真实的定位
     */
    private int currentRealPosition = 0;
    /**
     * 图片集合
     */
    private List<Object> imageList;
    /**
     * 处理后的图片集合
     */
    private List<Object> handleImageList;
    /**
     * 图片集合长度
     */
    private int imgLen;
    /**
     * 处理后的图片集合长度
     */
    private int handleImgLen;
    /**
     * 设置当前显示的item定位
     */
    private int showPosition;
    /**
     * 设置当前ViewPager2滚动方向
     */
    private int showOrientation;
    /**
     * 是否允许移动ViewPager2
     */
    private boolean showIsAllowMove = true;
    /**
     * 是否全屏
     */
    private boolean isFullscreen = true;
    /**
     * 是否显示关闭按钮
     */
    private boolean isShowClose = true;
    /**
     * 是否允许无限循环滑动
     */
    private boolean isInfiniteLoop = true;
    /**
     * 是否显示数字指示器
     */
    private boolean isShowNumIndicator = true;
    /**
     * ViewPager2页面间距
     */
    private int pageMargin = 10;
    /**
     * 预加载个数
     */
    private int offscreenPageLimit;
    /**
     * 上一个页面的进场动画
     */
    private int outPageEnterAnim;
    /**
     * 当前页面的退场动画
     */
    private int outPageExitAnim;
    /**
     * 页面切换动画
     */
    private ViewPager2.PageTransformer pageTransformer;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    private static WeakDataHolder weakDataHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_fragment_image_preview);
        try {
            textView = findViewById(R.id.image_view_pager_num_indicator);
            initParam();
            if (onPageListener != null) {
                onPageListener.onOpen(showPosition);
            }
            initClose();
            initViewPager();
            if (isFullscreen) {
                initStatusBar();
            }
        } catch (WImagePreviewException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onPageListener != null) {
            onPageListener.onClose(imageList.get(currentPosition), currentPosition);
        }
        exitActivity();
    }

    /**
     * 初始化参数
     */
    public void initParam() {
        // 配置SharedPreferences
        prefs = getApplicationContext().getSharedPreferences(KeyConst.APP_SHARED_PREFERENCES, MODE_PRIVATE);
        prefsEditor = prefs.edit();
        intent = getIntent();
        weakDataHolder = WeakDataHolder.getInstance();

        // 获取组件配置属性参数
        showPosition = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_ITEM_POSITION, WConfig.DEFAULT_ITEM_POSITION);
        showOrientation = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_ORIENTATION, WConfig.DEFAULT_ORIENTATION);
        showIsAllowMove = (boolean) weakDataHolder.getData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, WConfig.DEFAULT_IS_ALLOW_MOVE);
        isShowNumIndicator = (boolean) weakDataHolder.getData(KeyConst.VIEWPAGER2_SHOW_NUM_INDICATOR, WConfig.DEFAULT_SHOW_NUM_INDICATOR);
        isFullscreen = (boolean) weakDataHolder.getData(KeyConst.IS_FULLSCREEN, WConfig.DEFAULT_IS_FULLSCREEN);
        isShowClose = (boolean) weakDataHolder.getData(KeyConst.IS_SHOW_CLOSE, WConfig.DEFAULT_IS_SHOW_CLOSE);
        isInfiniteLoop = (boolean) weakDataHolder.getData(KeyConst.VIEWPAGER2_IS_INFINITE_LOOP, WConfig.DEFAULT_IS_INFINITE_LOOP);
        pageMargin = (int) weakDataHolder.getData(KeyConst.VIEW_PAGER2_PAGE_MARGIN, WConfig.DEFAULT_PAGE_MARGIN);
        offscreenPageLimit = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_OFFSCREEN_PAGE_LIMIT, WConfig.DEFAULT_OFFSCREEN_PAGE_LIMIT);
        outPageEnterAnim = (int) weakDataHolder.getData(KeyConst.PAGER2_PAGE_OUT_ENTER_ANIM, WConfig.DEFAULT_PAGE_OUT_ENTER_ANIM);
        outPageExitAnim = (int) weakDataHolder.getData(KeyConst.PAGER2_PAGE_OUT_EXIT_ANIM, WConfig.DEFAULT_PAGE_OUT_EXIT_ANIM);
        currentPosition = showPosition;

        // 监听器参数
        Object onPageListenerObj = WeakDataHolder.getInstance().getData(KeyConst.ON_PAGE_LISTENER);
        onPageListener = onPageListenerObj == null ? null : (OnPageListener) onPageListenerObj;
        // 页面翻页动画参数
        Object pageTransformerObj = WeakDataHolder.getInstance().getData(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER);
        pageTransformer = pageTransformerObj == null ? null : (ViewPager2.PageTransformer) pageTransformerObj;
    }

    /**
     * 初始化ViewPager2
     */
    private void initViewPager() {
        imageList = (List<Object>) weakDataHolder.getData(KeyConst.IMAGE_URI_LIST);
        if (imageList == null || imageList.isEmpty()) {
            // 无法获取图片
            WImagePreviewException.setExceptionByType(this, WImagePreviewException.ExceptionType.IMAGE_LIST_EMPTY);
        }
        Object firstImg = imageList.get(0);
        if (!(firstImg instanceof Uri || firstImg instanceof String)) {
            // 图片类型不正确， URI and String
            WImagePreviewException.setExceptionByType(this, WImagePreviewException.ExceptionType.IMAGE_TYPE_INVALID);
        }

        // 初始化数字指示器
        imgLen = imageList.size();
        if (isShowNumIndicator) {
            textView.setText(getString(R.string.num_indicator_text, (showPosition + 1), imgLen));
        } else {
            textView.setVisibility(View.GONE);
        }

        // 处理图片集合
        handleImageList = WTools.setData(isInfiniteLoop, imageList);
        handleImgLen = handleImageList.size();
        currentRealPosition = WTools.getRealPosition(isInfiniteLoop, handleImgLen, showPosition);

        // ViewPager2配置
        viewPager2 = findViewById(R.id.image_view_pager_2);
        imagePreviewAdapter = new ImagePreviewAdapter(ImagePreviewFragmentActivity.this, handleImageList);
        viewPager2.setAdapter(imagePreviewAdapter);
        int showItemPosition = WTools.getRealPosition(isInfiniteLoop, handleImgLen, showPosition);
        viewPager2.setCurrentItem(showItemPosition, false);
        viewPager2.setOrientation(showOrientation);
        viewPager2.setUserInputEnabled(showIsAllowMove);
        viewPager2.setOffscreenPageLimit(offscreenPageLimit);
        if (pageTransformer != null) {
            viewPager2.setPageTransformer(pageTransformer);
        } else {
            viewPager2.setPageTransformer(new MarginPageTransformer(pageMargin));
        }

        // viewPager2滑动监听
        mOnPageChangeCallback = new ViewPager2OnPageChangeCallback();
        viewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);
    }

    /**
     * 初始化关闭按钮
     */
    private void initClose() {
        if (isShowClose) {
            closeBtn = findViewById(R.id.image_view_pager_close);
            closeBtn.setOnClickListener(view -> {
                if (onPageListener != null) {
                    onPageListener.onClose(imageList.get(currentPosition), currentPosition);
                }

                exitActivity();
            });
        } else {
            closeBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        Window window = getWindow();
        // 透明状态栏
        window.setStatusBarColor(Color.TRANSPARENT);
        // 沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false);
        WindowCompat.getInsetsController(window, window.getDecorView())
                .setAppearanceLightNavigationBars(true);

//        // 设置透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            // 实现状态栏图标(文字颜色暗色为View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
    }

    /**
     * 页面变化监听
     */
    public class ViewPager2OnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            // 获取ViewPager2是否允许移动
            if (showIsAllowMove) {
                boolean isAllowMove = prefs.getBoolean(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
                viewPager2.setUserInputEnabled(isAllowMove);

                if (onPageListener != null) {
                    onPageListener.onPageScrolled(WTools.getShowPosition(isInfiniteLoop, handleImgLen, position),
                            positionOffset, positionOffsetPixels);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            currentPosition = WTools.getShowPosition(isInfiniteLoop, handleImgLen, position);
            currentRealPosition = position;

            if (showIsAllowMove) {
                textView.setText(getString(R.string.num_indicator_text, currentPosition + 1, imgLen));
            }

            Log.i("TAG", "onPageSelected-->position: " + position);
            if (onPageListener != null &&
                    position != 0 &&
                    position != handleImgLen - 1) {
                onPageListener.onPageSelected(currentPosition);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (isInfiniteLoop) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (currentRealPosition == 0) {
                        viewPager2.setCurrentItem(imgLen, false);
                    } else if (currentRealPosition == handleImgLen - 1) {
                        viewPager2.setCurrentItem(1, false);
                    }
                }
            }

            if (onPageListener != null) {
                onPageListener.onPageScrollStateChanged(state);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 获取ViewPager2是否允许移动
        if (showIsAllowMove) {
            boolean isAllowMove = prefs.getBoolean(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
            viewPager2.setUserInputEnabled(isAllowMove);
        }

        if (onPageListener != null) {
            onPageListener.onClick(imageList.get(currentPosition), currentPosition);
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 退出页面
     */
    private void exitActivity() {
        finish();
        if (outPageEnterAnim != 0 && outPageExitAnim != 0) {
            overridePendingTransition(outPageEnterAnim, outPageExitAnim);
        }
    }


}