package com.wei.wimagepreviewlib.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wei.wimagepreviewlib.R;

public class WRecyclerView extends RecyclerView {
    private Context mContext;

    public WRecyclerView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public WRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public WRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * 设置视图不可见，以上往下的方式退场
     */
    public void setInvisible() {
        setVisibility(View.INVISIBLE);
        setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_top_to_bottom));
    }

    /**
     * 设置视图不可见，以上往下的方式退场
     */
    public void setGone() {
        setVisibility(View.GONE);
        setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_top_to_bottom));
    }

    /**
     * 设置视图可见，以下往上的方式退场
     */
    public void setVisible() {
        setVisibility(View.VISIBLE);
        setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_bottom_to_top));
    }
}
