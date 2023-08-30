package com.wei.wimagepreviewlib.utils;

import androidx.viewpager2.widget.ViewPager2;

/**
 * 配置参数
 *
 * @author weizhanjie
 */
public class WConfig {
    /**
     * 默认的图片定位
     */
    public static final int DEFAULT_ITEM_POSITION = 0;
    /**
     * 默认的滚动方向
     */
    public static final int DEFAULT_ORIENTATION = ViewPager2.ORIENTATION_HORIZONTAL;
    /**
     * 默认是否允许移动ViewPager2
     */
    public static final boolean DEFAULT_IS_ALLOW_MOVE = true;
    /**
     * 默认是否显示数字指示器
     */
    public static final boolean DEFAULT_SHOW_NUM_INDICATOR = true;
    /**
     * 默认是否全屏
     */
    public static final boolean DEFAULT_IS_FULLSCREEN = true;
    /**
     * 默认是否显示关闭按钮
     */
    public static final boolean DEFAULT_IS_SHOW_CLOSE = true;
    /**
     * 默认是否允许无限循环滑动
     */
    public static final boolean DEFAULT_IS_INFINITE_LOOP = true;
    /**
     * 默认item间的间距
     */
    public static final int DEFAULT_PAGE_MARGIN = 10;
    /**
     * 默认ViewPager2预加载个数
     */
    public static final int DEFAULT_OFFSCREEN_PAGE_LIMIT = 2;
    /**
     * 默认ViewPager2上一页面的进场动画
     */
    public static final int DEFAULT_PAGE_OUT_ENTER_ANIM = 0;
    /**
     * 默认ViewPager2页面退场动画
     */
    public static final int DEFAULT_PAGE_OUT_EXIT_ANIM = 0;
}
