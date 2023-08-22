package com.wei.wimagepreviewlib.utils;

/**
 * 动画工具类
 *
 * @author weizhanjie
 */
public class WAnim {

    /**
     * 进场动画
     */
    public enum InType {
        IN_BOTTOM_TO_TOP,
        IN_CENTER_ZOOM,
        IN_LEFT_TO_RIGHT,
        IN_LEFT_TOP_ZOOM,
        IN_OUTSIDE_SCALE,
        IN_RIGHT_TO_LEFT,
        IN_ROTATE_SCALE_MOVE,
        IN_TOP_TOP_BOTTOM
    }

    /**
     * 退场动画
     */
    public enum OutType {
        OUT_BOTTOM_TO_TOP,
        OUT_FADE,
        OUT_LEFT_TO_RIGHT,
        OUT_LEFT_TOP,
        OUT_RIGHT_TO_LEFT,
        OUT_TOP_TO_BOTTOM
    }
}
