package com.wei.wimagepreviewlib.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 *
 * @author weizhanjie
 */
public class WTools {

    /**
     * 设置图片集合数据
     *
     * @param imageList
     * @return
     */
    public static List<Object> setData(List<Object> imageList) {
        return setData(true, imageList);
    }

    /**
     * 设置图片集合数据
     *
     * @param isInfiniteLoop 是否无限循环滚动
     * @param imageList
     * @return
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
            return 1;
        }

        if (!isInfiniteLoop) {
            return position;
        }

        int realPosition;
        if (position == 0) {
            realPosition = listSize - 2;
        } else if (position == listSize - 1) {
            realPosition = 1;
        } else {
            realPosition = position - 1;
        }

        return realPosition;
    }
}
