package com.wei.wimagepreviewlib.utils;

import com.wei.wimagepreviewlib.R;

/**
 * 动画工具类
 *
 * @author weizhanjie
 */
public class WAnim {

    //----------------------------------------------------------------------------------------------
    //
    // 进场动画
    //
    //----------------------------------------------------------------------------------------------
    /**
     * 进场动画--从下往上
     */
    public static final int IN_BOTTOM_TO_TOP = R.anim.in_bottom_to_top;
    /**
     * 进场动画--中间缩放
     */
    public static final int IN_CENTER_ZOOM = R.anim.in_center_zoom;
    /**
     * 进场动画--从左往右
     */
    public static final int IN_LEFT_TO_RIGHT = R.anim.in_left_to_right;
    /**
     * 进场动画--左上角缩放
     */
    public static final int IN_LEFT_TOP_ZOOM = R.anim.in_left_top_zoom;
    /**
     * 进场动画--外围缩放（从外往里）
     */
    public static final int IN_OUTSIDE_SCALE = R.anim.in_outside_scale;
    /**
     * 进场动画--从右往左
     */
    public static final int IN_RIGHT_TO_LEFT = R.anim.in_right_to_left;
    /**
     * 进场动画--组合动画：旋转+缩放+移动
     */
    public static final int IN_ROTATE_SCALE_MOVE = R.anim.in_rotate_scale_move;
    /**
     * 进场动画--从上往下
     */
    public static final int IN_TOP_TOP_BOTTOM = R.anim.in_top_to_bottom;
    /**
     * 进场动画--淡进
     */
    public static final int IN_FADE = R.anim.in_fade;

    //----------------------------------------------------------------------------------------------
    //
    // 退场动画
    //
    //----------------------------------------------------------------------------------------------
    /**
     * 退场动画--从下往上
     */
    public static final int OUT_BOTTOM_TO_TOP = R.anim.out_bottom_to_top;
    /**
     * 退场动画--透明淡出
     */
    public static final int OUT_FADE = R.anim.out_fade;
    /**
     * 退场动画--从左往右
     */
    public static final int OUT_LEFT_TO_RIGHT = R.anim.out_left_to_right;
    /**
     * 退场动画--左上角退出
     */
    public static final int OUT_LEFT_TOP = R.anim.out_left_top;
    /**
     * 退场动画--右下角退出
     */
    public static final int OUT_RIGHT_BOTTOM = R.anim.out_right_bottom;
    /**
     * 退场动画--从右往左
     */
    public static final int OUT_RIGHT_TO_LEFT = R.anim.out_right_to_left;
    /**
     * 退场动画--从上往下
     */
    public static final int OUT_TOP_TO_BOTTOM = R.anim.out_top_to_bottom;
    /**
     * 退场动画--中间缩放退出
     */
    public static final int OUT_CENTER_ZOOM = R.anim.out_center_zoom;

    //----------------------------------------------------------------------------------------------
    //
    // 进退场动画
    //
    //----------------------------------------------------------------------------------------------
    /**
     * 进退场动画--下进上出
     */
    public static final int ALL_BOTTOM_IN_TOP_OUT = 1;
    /**
     * 进退场动画--上进下出
     */
    public static final int ALL_TOP_IN_BOTTOM_OUT = 2;
    /**
     * 进退场动画--左进右出
     */
    public static final int ALL_LEFT_IN_RIGHT_OUT = 3;
    /**
     * 进退场动画--右进左出
     */
    public static final int ALL_RIGHT_IN_LEFT_OUT = 4;
    /**
     * 进退场动画--中间缩放进场，透明淡出
     */
    public static final int ALL_CENTER_IN_FADE_OUT = 5;
    /**
     * 进退场动画--外围缩放进场（从外往里），透明淡出
     */
    public static final int ALL_OUTSIDE_IN_FADE_OUT = 6;
    /**
     * 进退场动画--左上进，右下出
     */
    public static final int ALL_LEFT_TOP_IN_RIGHT_BTM_OUT = 7;
    /**
     * 进退场动画--旋转缩放进，透明淡出
     */
    public static final int ALL_ROTATE_IN_FADE_OUT = 8;

}
