package com.wei.wimagepreviewlib.wight;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 字体图标控件<br/>
 * 自带Ant Design字体图标库和Font Awesome 6.0字体图标库，
 * 默认使用Ant Design图标，若想使用Font Awesome图标，请使用
 * {@link #setFontType(WIconText.FontType fontType)}设置切换
 *
 * @author weizhanjie
 */
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

    /**
     * 将icon编码字符转成图标（若是strings.xml中设置的字符串，可直接使用{@link com.wei.wimagepreviewlib.wight.WIconText#setText(CharSequence text)}）
     *
     * @param iconTextString icon编码字符（{@code &#x***;}格式的字符串）
     */
    public void setIconTextString(String iconTextString) {
        setText(Html.fromHtml(iconTextString, Html.FROM_HTML_MODE_COMPACT));
    }
}
