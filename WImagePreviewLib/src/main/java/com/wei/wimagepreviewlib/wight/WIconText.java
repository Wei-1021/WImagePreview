package com.wei.wimagepreviewlib.wight;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WIconText extends androidx.appcompat.widget.AppCompatTextView {
    /**
     * 字体图标库类型
     */
    public enum FontType {
        /**
         * Ant Design字体图标库（默认）
         */
        ANT_DESIGN,
        /**
         * Font Awesome字体图标库（regular-400）
         */
        FONT_AWESOME
    }

    private Context mContext;

    public WIconText(@NonNull Context context) {
        super(context);
        this.mContext = context;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "icon/ant_design_iconfont.ttf");
        setTypeface(typeface);
    }

    public WIconText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "icon/ant_design_iconfont.ttf");
        setTypeface(typeface);
    }

    public WIconText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "icon/ant_design_iconfont.ttf");
        setTypeface(typeface);
    }

    /**
     * 设置字体图标库类型
     *
     * @param fontType {@link com.wei.wimagepreviewlib.wight.WIconText.FontType#ANT_DESIGN}：Ant Design字体图标库；<br/>
     *                 {@link com.wei.wimagepreviewlib.wight.WIconText.FontType#FONT_AWESOME}：Font Awesome字体图标库
     */
    public void setFontType(WIconText.FontType fontType) {
        if (FontType.ANT_DESIGN == fontType) {
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "icon/ant_design_iconfont.ttf");
            setTypeface(typeface);
        } else if (FontType.FONT_AWESOME == fontType) {
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "icon/fa-regular-400.ttf");
            setTypeface(typeface);
        }
    }
}
