package com.wei.wimagepreviewlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.wei.wimagepreviewlib.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 *
 * @author weizhanjie
 */
public class WTools {

    /**
     * 设置图片集合数据。
     * 集合首尾各插入一条数据，头部插入原集合的最后一条数据，
     * 尾部插入原集合的第一条数据；如[1,2,3]-->[3,1,2,3,1]
     *
     * @param imageList 未处理的图片集合
     * @return
     */
    public static List<Object> setData(List<Object> imageList) {
        return setData(true, imageList);
    }

    /**
     * 设置图片集合数据。
     * 若允许无限循环滚动，则集合首尾各插入一条数据，
     * 头部插入原集合的最后一条数据，尾部插入原集合的第一条数据；
     * 如[1,2,3]-->[3,1,2,3,1]
     *
     * @param isInfiniteLoop 是否无限循环滚动
     * @param imageList      未处理的图片集合
     * @return 处理后的图片集合
     */
    public static List<Object> setData(boolean isInfiniteLoop, List<Object> imageList) {
        if (!isInfiniteLoop) {
            return imageList;
        }

        if (imageList.size() < 2) {
            return imageList;
        }

        // 首尾各插入一条数据，头部插入原集合的最后一条数据，尾部插入原集合的第一条数据；
        // 如[1,2,3]-->[3,1,2,3,1]
        List<Object> list = new ArrayList<>(imageList);
        list.add(0, imageList.get(imageList.size() - 1));
        list.add(list.size(), imageList.get(0));

        return list;
    }

    /**
     * 获取真实定位；将所展示的定位转化为真的的定位；
     *
     * @param isInfiniteLoop 是否允许无限循环滚动
     * @param listSize       处理后的集合长度
     * @param position       所展示的定位
     * @return
     */
    public static int getRealPosition(boolean isInfiniteLoop, int listSize, int position) {
        if (listSize <= 0) {
            return 0;
        }

        if (listSize == 1) {
            return 1;
        }

        if (!isInfiniteLoop) {
            return position;
        }

        return position + 1;
    }

    /**
     * 获取展示的图片定位
     *
     * @param isInfiniteLoop 是否允许无限循环滚动
     * @param listSize       处理后的集合长度
     * @param position       真实的定位
     * @return
     */
    public static int getShowPosition(boolean isInfiniteLoop, int listSize, int position) {
        if (listSize <= 0) {
            return 0;
        }

        if (listSize == 1) {
            return 0;
        }

        if (!isInfiniteLoop) {
            return position;
        }

        int showPosition;
        if (position == 0) {
            showPosition = listSize - 3;
        } else if (position == listSize - 1) {
            showPosition = 0;
        } else {
            showPosition = position - 1;
        }

        return showPosition;
    }

    /**
     * 判断当前文件对象是否为支持的图片类型
     *
     * @param img 图片对象
     * @return
     */
    public static boolean isSupportImage(Object img) {
        return img instanceof String ||
                img instanceof Uri ||
                img instanceof Bitmap ||
                img instanceof File ||
                img instanceof Drawable ||
                img instanceof Integer ||
                img instanceof byte[];
    }

    public static boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        return (top <= y && y <= bottom) && x >= left && x <= right;
    }
}
