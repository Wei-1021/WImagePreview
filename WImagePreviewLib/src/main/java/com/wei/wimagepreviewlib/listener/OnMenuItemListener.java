package com.wei.wimagepreviewlib.listener;

import android.view.View;

import com.wei.wimagepreviewlib.wight.WRecyclerView;

/**
 * @author weizhanjie
 */
public abstract class OnMenuItemListener {

    /**
     * 点击回调事件
     *
     * @param recyclerView WRecyclerView
     * @param itemView 当前菜单项的View视图
     * @param obj 媒体文件对象
     * @param position 当前文件在集合中的定位
     */
    public void onClick(WRecyclerView recyclerView, View itemView, Object obj, int position) {
    }


}
