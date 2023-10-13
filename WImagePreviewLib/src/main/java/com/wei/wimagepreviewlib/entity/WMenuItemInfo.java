package com.wei.wimagepreviewlib.entity;

import android.graphics.drawable.Drawable;
import com.wei.wimagepreviewlib.utils.*;

import com.wei.wimagepreviewlib.listener.OnMenuItemListener;

/**
 * 预览界面--菜单功能实体
 *
 * @author weizhanjie
 */
public class WMenuItemInfo {
    /**
     * 名称
     */
    private String name;

    /**
     * 字体图标，可以使用{@link WIcon}图标常量类，也可以使用自定义的图标库（必须是{@code &#x***;}的格式）
     */
    private String icon;

    /**
     * 图片图标
     */
    private Drawable iconDraw;

    private Integer textColor;

    private Drawable background;

    private Integer backgroundColor;

    private OnMenuItemListener onMenuItemListener;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Drawable getIconDraw() {
        return iconDraw;
    }

    public void setIconDraw(Drawable iconDraw) {
        this.iconDraw = iconDraw;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public void setTextColor(Integer textColor) {
        this.textColor = textColor;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public OnMenuItemListener getOnMenuItemListener() {
        return onMenuItemListener;
    }

    public void setOnMenuItemListener(OnMenuItemListener onMenuItemListener) {
        this.onMenuItemListener = onMenuItemListener;
    }
}
