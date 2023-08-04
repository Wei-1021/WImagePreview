package com.wei.wimagepreviewlib.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 页面切换动画效果
 *
 * @author weizhanjie
 */
public class PageTransformer {
    /**
     * 景深
     */
    public static final int PAGE_TRANSFORM_ZOOM_OUT = 1;
    /**
     * 叠层
     */
    public static final int PAGE_TRANSFORM_DEPTH = 2;
    /**
     * 翻转
     */
    public static final int PAGE_TRANSFORM_FLIP = 3;
    /**
     * 推压
     */
    public static final int PAGE_TRANSFORM_PUSH = 4;
    /**
     * 旋转
     */
    public static final int PAGE_TRANSFORM_ROTATE = 5;
    /**
     * 方块
     */
    public static final int PAGE_TRANSFORM_SQUARE_BOX = 6;
    /**
     * 风车
     */
    public static final int PAGE_TRANSFORM_WIND_MILL = 7;

    /**
     * 初始化动画类型
     *
     * @param type 动画类型;详见{@link PageTransformer}中的静态变量
     */
    public static ViewPager2.PageTransformer initPageTransformer(int type) {
        switch (type) {
            case PAGE_TRANSFORM_ZOOM_OUT:
                return new ZoomOutPageTransformer();
            case PAGE_TRANSFORM_DEPTH:
                return new DepthPageTransformer();
            case PAGE_TRANSFORM_FLIP:
                return new FlipTransformer();
            case PAGE_TRANSFORM_PUSH:
                return new PushTransformer();
            case PAGE_TRANSFORM_ROTATE:
                return new RotationTransformer();
            case PAGE_TRANSFORM_SQUARE_BOX:
                return new SquareBoxTransformer();
            case PAGE_TRANSFORM_WIND_MILL:
                return new WindMillTransformer();
            default:
                return new ZoomOutPageTransformer();
        }
    }

    /**
     * 景深
     *
     * @author weizhanjie
     */
    public static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // 这一页在屏幕的左边.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // 修改默认的幻灯片过渡以缩小页面
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // 按比例缩小页面 (1-MIN_SCALE之间)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // 使页面相对于其大小缩小.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // 这个页面在屏幕的右边.
                view.setAlpha(0f);
            }
        }
    }

    /**
     * 叠层
     *
     * @author weizhanjie
     */
    public static class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // 这一页在屏幕的左边.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // 移动到左页时使用默认的幻灯片过渡
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // 使页面淡出.
                view.setAlpha(1 - position);

                // 抵消默认的滑动过渡
                view.setTranslationX(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // 按比例缩小页面 (1-MIN_SCALE之间)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // 这个页面在屏幕的右边.
                view.setAlpha(0f);
            }
        }
    }


    /**
     * 翻转
     *
     * @author weizhanjie
     */
    public static class FlipTransformer implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();
            int height = view.getHeight();

            view.setTranslationX(-width * position);
            view.setPivotX(width / 2f);
            view.setPivotY(height / 2f);
            view.setRotationY(180 * position);

            if (position > -0.5f && position < 0.5f) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }

            float absPos = Math.abs(position);
            float scale = absPos > 1 ? 0f : 1 - absPos;
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
    }

    /**
     * 推压
     *
     * @author weizhanjie
     */
    public static class PushTransformer implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();

            view.setTranslationX(-position * width / 2);
            float absPos = Math.abs(position);
            float scale = absPos > 1 ? 0f : 1 - absPos;
            view.setScaleX(scale);
        }
    }

    /**
     * 旋转
     *
     * @author weizhanjie
     */
    public static class RotationTransformer implements ViewPager2.PageTransformer {

        private static final float MAX_ROTATION = 90f;
        private static final float MIN_SCALE = 0.9f;

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();
            int height = view.getHeight();

            view.setTranslationX(-position * width * 0.8f);
            view.setPivotY(height / 2f);

            if (position < -1) {
                view.setRotationY(-MAX_ROTATION);
                view.setPivotX(0f);
            } else if (position <= 1) {
                if (position < 0) {
                    view.setRotationY(position * position * MAX_ROTATION);
                    view.setPivotX(0f);
                    float scale = MIN_SCALE + 4f * (1f - MIN_SCALE) *
                            (position + 0.5f) * (position + 0.5f);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                } else {
                    view.setRotationY(-position * position * MAX_ROTATION);
                    view.setPivotX(width);
                    float scale = MIN_SCALE + 4f * (1f - MIN_SCALE) *
                            (position - 0.5f) * (position - 0.5f);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            } else {
                view.setRotationY(MAX_ROTATION);
                view.setPivotX(view.getWidth());
            }
        }
    }

    /**
     * 方块
     *
     * @author weizhanjie
     */
    public static class SquareBoxTransformer implements ViewPager2.PageTransformer {
        private static final float MAX_ROTATION = 90f;
        private static final float MIN_SCALE = 0.9f;

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();
            int height = view.getHeight();

            view.setPivotY(height / 2f);

            if (position < -1) {
                view.setRotationY(-MAX_ROTATION);
                view.setPivotX(0f);
            } else if (position <= 1) {
                view.setRotationY(position * MAX_ROTATION);

                if (position < 0) {
                    view.setPivotX(width);
                    float scale = MIN_SCALE + 4f * (1f - MIN_SCALE) *
                            (position + 0.5f) * (position + 0.5f);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                } else {
                    view.setPivotX(0f);
                    float scale = MIN_SCALE + 4f * (1f - MIN_SCALE) *
                            (position - 0.5f) * (position - 0.5f);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            } else {
                view.setRotationY(MAX_ROTATION);
                view.setPivotX(0);
            }
        }
    }

    /**
     * 风车
     *
     * @author weizhanjie
     */
    public static class WindMillTransformer implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            int width = view.getWidth();
            int height = view.getHeight();
            view.setPivotX(width / 2f);
            view.setPivotY(height);
            view.setRotation(position * 90);
        }
    }
}
