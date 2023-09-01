package com.wei.wimagepreviewlib.entity;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * 图片视图组件信息实体
 *
 * @author weizhanjie
 */
public class Info {
 
    // 内部图片在整个手机界面的位置
    RectF mRect = new RectF();
 
    // 控件在窗口的位置
    RectF mImgRect = new RectF();
 
    RectF mWidgetRect = new RectF();
 
    RectF mBaseRect = new RectF();
 
    PointF mScreenCenter = new PointF();
 
    float mScale;
 
    float mDegrees;

    ImageView.ScaleType mScaleType;
 
    public Info(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }

    public RectF getmRect() {
        return mRect;
    }

    public RectF getmImgRect() {
        return mImgRect;
    }

    public RectF getmWidgetRect() {
        return mWidgetRect;
    }

    public RectF getmBaseRect() {
        return mBaseRect;
    }

    public PointF getmScreenCenter() {
        return mScreenCenter;
    }

    public float getmScale() {
        return mScale;
    }

    public float getmDegrees() {
        return mDegrees;
    }

    public ImageView.ScaleType getmScaleType() {
        return mScaleType;
    }
}