package com.wei.wimagepreviewlib.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.adapter.ImagePreviewAdapter;
import com.wei.wimagepreviewlib.exception.WImagePreviewException;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.utils.KeyConst;
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
     * 页面监听器
     */
    private OnPageListener onPageListener;
    /**
     * 当前图片定位
     */
    private int currentPosition = 0;
    /**
     * 图片集合
     */
    private List<Object> imageList;
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
     * 页面切换动画
     */
    private ViewPager2.PageTransformer pageTransformer;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 初始化参数
     */
    public void initParam() {
        // 配置SharedPreferences
        prefs = getApplicationContext().getSharedPreferences(KeyConst.APP_SHARED_PREFERENCES, MODE_PRIVATE);
        prefsEditor = prefs.edit();
        intent = getIntent();

        // 获取组件配置属性参数
        showPosition = intent.getIntExtra(KeyConst.VIEWPAGER2_ITEM_POSITION, 0);
        showOrientation = intent.getIntExtra(KeyConst.VIEWPAGER2_ORIENTATION, ViewPager2.ORIENTATION_HORIZONTAL);
        showIsAllowMove = intent.getBooleanExtra(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
        isShowNumIndicator = intent.getBooleanExtra(KeyConst.VIEWPAGER2_SHOW_NUM_INDICATOR, true);
        isFullscreen = intent.getBooleanExtra(KeyConst.IS_FULLSCREEN, true);
        isShowClose = intent.getBooleanExtra(KeyConst.IS_SHOW_CLOSE, true);
        pageMargin = intent.getIntExtra(KeyConst.VIEW_PAGER2_PAGE_MARGIN, 10);
        offscreenPageLimit = intent.getIntExtra(KeyConst.VIEWPAGER2_OFFSCREEN_PAGE_LIMIT, ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        currentPosition = showPosition;
        // 监听器参数
        Object onPageListenerObj = WeakDataHolder.getInstance().getData(KeyConst.ON_PAGE_LISTENER);
        onPageListener = onPageListenerObj == null ? null : (OnPageListener) onPageListenerObj;
        // 页面翻页动画参数
        Object pageTransformerObj = WeakDataHolder.getInstance().getData(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER);
        pageTransformer = pageTransformerObj == null ? null :(ViewPager2.PageTransformer) pageTransformerObj;
    }

    /**
     * 初始化ViewPager2
     */
    private void initViewPager() throws WImagePreviewException {
        imageList = (List<Object>) WeakDataHolder.getInstance().getData(KeyConst.IMAGE_URI_LIST);
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
        int imgLen = imageList.size();
        if (isShowNumIndicator) {
            textView.setText(getString(R.string.num_indicator_text, (showPosition + 1), imgLen));
        } else {
            textView.setVisibility(View.GONE);
        }

        // ViewPager2配置
        viewPager2 = findViewById(R.id.image_view_pager_2);
        imagePreviewAdapter = new ImagePreviewAdapter(ImagePreviewFragmentActivity.this, imageList);
        viewPager2.setAdapter(imagePreviewAdapter);
        viewPager2.setCurrentItem(showPosition);
        viewPager2.setOrientation(showOrientation);
        viewPager2.setUserInputEnabled(showIsAllowMove);
        viewPager2.setOffscreenPageLimit(offscreenPageLimit);
        if (pageTransformer != null) {
            viewPager2.setPageTransformer(pageTransformer);
        } else {
            viewPager2.setPageTransformer(new MarginPageTransformer(pageMargin));
        }

        // viewPager2滑动监听
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // 获取ViewPager2是否允许移动
                if (showIsAllowMove) {
                    boolean isAllowMove = prefs.getBoolean(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
                    viewPager2.setUserInputEnabled(isAllowMove);

                    if (onPageListener != null) {
                        onPageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (onPageListener != null) {
                    onPageListener.onPageScrollStateChanged(state);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;

                if (isShowNumIndicator) {
                    textView.setText(getString(R.string.num_indicator_text, (currentPosition + 1), imgLen));
                }
                if (onPageListener != null) {
                    onPageListener.onPageSelected(position);
                }

            }
        });
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

                finish();
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
        // 设置透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // 实现状态栏图标(文字颜色暗色为View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //19表示4.4
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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


}