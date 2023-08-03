package com.wei.wimagepreviewlib.listener;

import androidx.annotation.Px;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 页面监听接口
 *
 * @author weizhanjie
 */
public abstract class OnPageListener {

    /**
     * 页面打开事件
     * @param position
     */
    public void onOpen(int position) {

    }

    /**
     * 页面点击事件
     * @param o 对应Image对象
     * @param position 下标
     */
    public void onClick(Object o, int position) {

    }

    /**
     * 页面关闭事件
     */
    public void onClose(Object o, int position) {

    }

    public void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {

    }

    public void onPageScrollStateChanged(@ViewPager2.ScrollState int state) {

    }
}
