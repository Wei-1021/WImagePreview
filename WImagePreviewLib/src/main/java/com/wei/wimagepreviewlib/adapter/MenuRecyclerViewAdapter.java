package com.wei.wimagepreviewlib.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.entity.WMenuItemInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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

    public MenuRecyclerViewAdapter(List<WMenuItemInfo> menuItemInfoList, Object objectImg, int currentPosition) {
        this.menuItemInfoList = menuItemInfoList;
        this.currentPosition = currentPosition;
        this.objectImg = objectImg;
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
        holder.bind(menuItemInfoList.get(position), objectImg, currentPosition);
    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "getItemCount: " + menuItemInfoList.size());
        return menuItemInfoList == null ? 0 : menuItemInfoList.size();
    }

    static class AdapterHolder extends RecyclerView.ViewHolder {

        private final TextView menuLabel;

        public AdapterHolder(@NonNull View itemView) {
            super(itemView);
            menuLabel = itemView.findViewById(R.id.menu_label);
        }

        public void bind(WMenuItemInfo wMenuItemInfo, Object objectImg, int position) {
            Log.i("TAG", "bind: " + position);
            if (wMenuItemInfo != null) {
                menuLabel.setCompoundDrawables(wMenuItemInfo.getIcon(), null, null, null);
                menuLabel.setText(wMenuItemInfo.getName());
                if (wMenuItemInfo.getTextColor() != null) {
                    menuLabel.setTextColor(wMenuItemInfo.getTextColor());
                }
                if (wMenuItemInfo.getBackground() != null) {
                    menuLabel.setBackground(wMenuItemInfo.getBackground());
                }
                menuLabel.setOnClickListener(v -> wMenuItemInfo.getOnMenuItemListener().onClick(v, objectImg, position));
            } else {
                menuLabel.setText(NO_VALUE);
            }
        }
    }
}
