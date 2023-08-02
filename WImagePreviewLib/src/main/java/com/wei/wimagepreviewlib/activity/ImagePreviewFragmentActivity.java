package com.wei.wimagepreviewlib.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.adapter.ImagePreviewAdapter;
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
     * ViewPager2页面间距
     */
    private int pageTransformer = 10;

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_image_preview);

        prefs = getApplicationContext().getSharedPreferences(KeyConst.APP_SHARED_PREFERENCES, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        intent = getIntent();
        textView = findViewById(R.id.image_view_pager_num_indicator);
        showPosition = intent.getIntExtra(KeyConst.VIEWPAGER2_ITEM_POSITION, 0);
        showOrientation = intent.getIntExtra(KeyConst.VIEWPAGER2_ORIENTATION, ViewPager2.ORIENTATION_HORIZONTAL);
        showIsAllowMove = intent.getBooleanExtra(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
        isFullscreen = intent.getBooleanExtra(KeyConst.IS_FULLSCREEN, true);
        isShowClose = intent.getBooleanExtra(KeyConst.IS_SHOW_CLOSE, true);
        pageTransformer = intent.getIntExtra(KeyConst.VIEW_PAGER2_PAGE_TRANSFORMER, 10);
        initClose();
        initViewPager();
        if (isFullscreen) {
            initStatusBar();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 初始化ViewPager2
     */
    private void initViewPager() {
        viewPager2 = findViewById(R.id.image_view_pager_2);
        imageList = (List<Object>) WeakDataHolder.getInstance().getData(KeyConst.IMAGE_URI_LIST);
        if (imageList == null) {
            Toast.makeText(this, "无法获取图片", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (!(imageList.get(0) instanceof Uri || imageList.get(0) instanceof String)) {
            Toast.makeText(this, "图片类型不正确", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        imagePreviewAdapter = new ImagePreviewAdapter(this, imageList);
        viewPager2.setAdapter(imagePreviewAdapter);
        viewPager2.setCurrentItem(showPosition);
        viewPager2.setOrientation(showOrientation);
        viewPager2.setUserInputEnabled(showIsAllowMove);
        viewPager2.setPageTransformer(new MarginPageTransformer(pageTransformer));
        int imgLen = imageList.size();
        textView.setText((showPosition + 1) + "/" + imgLen);

        // viewPager2滑动监听
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // 获取ViewPager2是否允许移动
                if (showIsAllowMove) {
                    boolean isAllowMove = prefs.getBoolean(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
                    viewPager2.setUserInputEnabled(isAllowMove);
                    Log.i("ImagePreviewFragment", "onPageScrolled: " + isAllowMove);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                textView.setText((position + 1) + "/" + imgLen);
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
            // 实现状态栏图标和文字颜色为暗色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //19表示4.4
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 获取ViewPager2是否允许移动
        if (showIsAllowMove) {
            boolean isAllowMove = prefs.getBoolean(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
            viewPager2.setUserInputEnabled(isAllowMove);
        }
        return super.dispatchTouchEvent(ev);
    }


}