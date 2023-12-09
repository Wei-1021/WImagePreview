package com.wei.wimagepreviewlib.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.adapter.ImagePreviewAdapter;
import com.wei.wimagepreviewlib.adapter.MenuRecyclerViewAdapter;
import com.wei.wimagepreviewlib.entity.WMenuItemInfo;
import com.wei.wimagepreviewlib.exception.WImagePreviewException;
import com.wei.wimagepreviewlib.listener.OnPageListener;
import com.wei.wimagepreviewlib.utils.KeyConst;
import com.wei.wimagepreviewlib.utils.WConfig;
import com.wei.wimagepreviewlib.utils.WIcon;
import com.wei.wimagepreviewlib.utils.WTools;
import com.wei.wimagepreviewlib.utils.WeakDataHolder;
import com.wei.wimagepreviewlib.wight.WIconText;
import com.wei.wimagepreviewlib.wight.WRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 *
 * @author weizhanjie
 */
public class ImagePreviewFragmentActivity extends FragmentActivity {

    private Intent intent;

    private Typeface typeface;

    /**
     * ViewPager2组件
     */
    private ViewPager2 viewPager2;
    /**
     * 底部数字指示器组件
     */
    private TextView numIndicatorTextView;
    /**
     * 关闭按钮
     */
    private WIconText closeBtn;
    /**
     * 菜单按钮
     */
    private WIconText menuBtn;
    /**
     * 更多菜单RecyclerView
     */
    private WRecyclerView menuRecyclerView;
    /**
     * 图片预览ViewPager2适配器
     */
    private ImagePreviewAdapter imagePreviewAdapter;
    /**
     * 更多菜单适配器
     */
    private MenuRecyclerViewAdapter menuRecyclerViewAdapter;
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
    private List<Object> imageList = new ArrayList<>();
    /**
     * 处理后的图片集合
     */
    private List<Object> handleImageList = new ArrayList<>();
    /**
     * 图片集合长度
     */
    private int imgLen;
    /**
     * 处理后的图片集合长度
     */
    private int handleImgLen;

    private static WeakDataHolder weakDataHolder;

    //--------------------------------------------------------------------------------------------------------------------------
    // 构建参数
    //--------------------------------------------------------------------------------------------------------------------------

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
     * 是否显示菜单按钮
     */
    private boolean isShowMenu = true;
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
     * 更多菜单
     */
    private List<WMenuItemInfo> menuItemInfoList;
    /**
     * 页面切换动画
     */
    private ViewPager2.PageTransformer pageTransformer;

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeface = Typeface.createFromAsset(getAssets(), WIconText.ANT_DESIGN_PATH);
        setContentView(R.layout.w_fragment_image_preview);
        try {
            numIndicatorTextView = findViewById(R.id.image_view_pager_num_indicator);
            initParam();
            if (onPageListener != null) {
                onPageListener.onOpen(showPosition);
            }
            initClose();
            initViewPager();
            initMenu();
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
        intent = getIntent();
        weakDataHolder = WeakDataHolder.getInstance();

        // 获取组件配置属性参数
        showIsAllowMove    = (boolean) weakDataHolder.getData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, WConfig.DEFAULT_IS_ALLOW_MOVE);
        isShowNumIndicator = (boolean) weakDataHolder.getData(KeyConst.VIEWPAGER2_SHOW_NUM_INDICATOR, WConfig.DEFAULT_SHOW_NUM_INDICATOR);
        isFullscreen       = (boolean) weakDataHolder.getData(KeyConst.IS_FULLSCREEN, WConfig.DEFAULT_IS_FULLSCREEN);
        isShowClose        = (boolean) weakDataHolder.getData(KeyConst.IS_SHOW_CLOSE, WConfig.DEFAULT_IS_SHOW_CLOSE);
        isShowMenu         = (boolean) weakDataHolder.getData(KeyConst.IS_SHOW_MENU, WConfig.DEFAULT_IS_SHOW_MENU);
        isInfiniteLoop     = (boolean) weakDataHolder.getData(KeyConst.VIEWPAGER2_IS_INFINITE_LOOP, WConfig.DEFAULT_IS_INFINITE_LOOP);
        showPosition       = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_ITEM_POSITION, WConfig.DEFAULT_ITEM_POSITION);
        showOrientation    = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_ORIENTATION, WConfig.DEFAULT_ORIENTATION);
        pageMargin         = (int) weakDataHolder.getData(KeyConst.VIEW_PAGER2_PAGE_MARGIN, WConfig.DEFAULT_PAGE_MARGIN);
        offscreenPageLimit = (int) weakDataHolder.getData(KeyConst.VIEWPAGER2_OFFSCREEN_PAGE_LIMIT, WConfig.DEFAULT_OFFSCREEN_PAGE_LIMIT);
        outPageEnterAnim   = (int) weakDataHolder.getData(KeyConst.PAGER2_PAGE_OUT_ENTER_ANIM, WConfig.DEFAULT_PAGE_OUT_ENTER_ANIM);
        outPageExitAnim    = (int) weakDataHolder.getData(KeyConst.PAGER2_PAGE_OUT_EXIT_ANIM, WConfig.DEFAULT_PAGE_OUT_EXIT_ANIM);
        menuItemInfoList   = (List<WMenuItemInfo>) weakDataHolder.getData(KeyConst.MORE_MENU, WConfig.DEFAULT_MORE_MENU);
        currentPosition    = showPosition;

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
        WImagePreviewException.setExceptionImage(this, imageList.get(0));

        // 初始化数字指示器
        imgLen = imageList.size();
        if (isShowNumIndicator) {
            numIndicatorTextView.setText(getString(R.string.num_indicator_text, (showPosition + 1), imgLen));
        } else {
            numIndicatorTextView.setVisibility(View.GONE);
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
        closeBtn = findViewById(R.id.image_view_pager_close);
        closeBtn.setTextColor(Color.WHITE);
        closeBtn.setTextSize(25);
        closeBtn.setIconTextString(WIcon.ANT_CLOSE);
        if (!isShowClose) {
            closeBtn.setVisibility(View.GONE);
            return;
        }

        closeBtn.setOnClickListener(view -> {
            if (onPageListener != null) {
                onPageListener.onClose(imageList.get(currentPosition), currentPosition);
            }

            exitActivity();
        });
    }

    /**
     * 初始化菜单按钮
     */
    private void initMenu() {
        menuBtn = findViewById(R.id.image_view_pager_menu);
        menuBtn.setTextColor(Color.WHITE);
        menuBtn.setTextSize(30);
        menuBtn.setIconTextString(WIcon.ANT_ELLIPSIS);

        menuRecyclerView = findViewById(R.id.image_view_pager_menu_recyclerView);
        menuRecyclerView.setVisibility(View.GONE);

        if (!isShowMenu) {
            menuBtn.setVisibility(View.GONE);
            return;
        }

        initMenuRecyclerViewAdapter();
        menuBtn.setOnClickListener(view -> menuRecyclerView.setVisible());
    }

    /**
     * 初始化功能菜单适配器
     */
    private void initMenuRecyclerViewAdapter() {
        menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(menuItemInfoList, menuRecyclerView,
                imageList.get(currentPosition), currentPosition);
        menuRecyclerView.setAdapter(menuRecyclerViewAdapter);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(ImagePreviewFragmentActivity.this));
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
                boolean isAllowMove = (boolean) weakDataHolder.getData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
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
                numIndicatorTextView.setText(getString(R.string.num_indicator_text, currentPosition + 1, imgLen));

                initMenuRecyclerViewAdapter();
            }

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
            boolean isAllowMove = (boolean) weakDataHolder.getData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
            viewPager2.setUserInputEnabled(isAllowMove);
        }

        if (onPageListener != null) {
            onPageListener.onClick(imageList.get(currentPosition), currentPosition);
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获取点击坐标
            int x = (int) ev.getRawX();
            int y = (int) ev.getRawY();
            // 若点击位置在menuRecyclerView之外，则隐藏menuRecyclerView
            if (!WTools.isTouchPointInView((View) menuRecyclerView, x, y)) {
                if (menuRecyclerView.getVisibility() == View.VISIBLE) {
                    menuRecyclerView.setInvisible();
                }
            }
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


    public static class MenuBottomDialogFragment extends BottomSheetDialogFragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


}