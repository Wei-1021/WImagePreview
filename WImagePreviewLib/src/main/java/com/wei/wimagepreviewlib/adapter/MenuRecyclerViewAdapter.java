package com.wei.wimagepreviewlib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.entity.WMenuItemInfo;
import com.wei.wimagepreviewlib.wight.WIconText;
import com.wei.wimagepreviewlib.wight.WRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 更多功能菜单适配器
 *
 * @author weizhanjie
 */
public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.AdapterHolder> {
    private static final String NO_VALUE = "--";
    private List<WMenuItemInfo> menuItemInfoList = new ArrayList<>();
    /**
     * 当前图片定位
     */
    private int currentPosition;
    /**
     * 图片对象
     */
    private Object objectImg;

    private WRecyclerView recyclerView;

    public MenuRecyclerViewAdapter(List<WMenuItemInfo> menuItemInfoList, WRecyclerView recyclerView, Object objectImg, int currentPosition) {
        this.menuItemInfoList = menuItemInfoList;
        this.currentPosition = currentPosition;
        this.objectImg = objectImg;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(R.layout.w_item_image_menu, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        holder.bind(menuItemInfoList.get(position), recyclerView, objectImg, currentPosition);
    }

    @Override
    public int getItemCount() {
        return menuItemInfoList == null ? 0 : menuItemInfoList.size();
    }

    static class AdapterHolder extends RecyclerView.ViewHolder {

        private final WIconText menuLabelIcon;
        private final WIconText menuLabel;
        public AdapterHolder(@NonNull View itemView) {
            super(itemView);
            this.menuLabelIcon = itemView.findViewById(R.id.menu_label_icon);
            this.menuLabel = itemView.findViewById(R.id.menu_label);
        }

        public void bind(WMenuItemInfo wMenuItemInfo, WRecyclerView recyclerView, Object objectImg, int position) {
            if (wMenuItemInfo != null) {
                if (wMenuItemInfo.getIconFontType() != null) {
                    menuLabelIcon.setFontType(wMenuItemInfo.getIconFontType());
                } else if (wMenuItemInfo.getTypeface() != null) {
                    menuLabelIcon.setTypeface(wMenuItemInfo.getTypeface());
                }
                menuLabelIcon.setIconTextString(wMenuItemInfo.getIcon());

                menuLabel.setText(wMenuItemInfo.getName());
                if (wMenuItemInfo.getTextColor() != null) {
                    menuLabelIcon.setTextColor(wMenuItemInfo.getTextColor());
                    menuLabel.setTextColor(wMenuItemInfo.getTextColor());
                }
                if (wMenuItemInfo.getBackgroundColor() != null) {
                    super.itemView.setBackgroundColor(wMenuItemInfo.getBackgroundColor());
                } else if (wMenuItemInfo.getBackground() != null) {
                    super.itemView.setBackground(wMenuItemInfo.getBackground());
                }
                menuLabel.setOnClickListener(v -> wMenuItemInfo.getOnMenuItemListener().onClick(recyclerView, super.itemView, objectImg, position));
            } else {
                menuLabel.setText(NO_VALUE);
            }
        }
    }
}
