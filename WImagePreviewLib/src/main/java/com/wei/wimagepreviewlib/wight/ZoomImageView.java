package com.wei.wimagepreviewlib.wight;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import androidx.annotation.Nullable;

import com.wei.wimagepreviewlib.utils.BigDecimalUtil;
import com.wei.wimagepreviewlib.utils.KeyConst;
import com.wei.wimagepreviewlib.utils.WeakDataHolder;

import java.math.BigDecimal;

/**
 * 图片视图控件
 *
 * @author weizhanjie
 */
public class ZoomImageView extends AppCompatImageView {

    /**
     * 缩放的三个状态
     */
    public enum ZoomMode {
        /**
         * 普通
         */
        ORDINARY,
        /**
         * 双击放大
         */
        ZOOM_IN,
        /**
         * 双指缩放
         */
        TOW_FINGER_ZOOM
    }

    private Context context;

    private Matrix matrix = new Matrix();
    /**
     * imageView的大小
     */
    private PointF viewSize;
    /**
     * 图片的大小
     */
    private PointF imageSize = new PointF();
    /**
     * 缩放后图片的大小
     */
    private PointF scaleSize = new PointF();
    /**
     * 最初的宽高的缩放比例
     */
    private PointF originScale = new PointF();
    /**
     * imageview中bitmap的xy实时坐标
     */
    private PointF bitmapOriginPoint = new PointF();

    // ----------------------------------------------------------------------------------
    //
    // 双击缩放参数
    //
    // ----------------------------------------------------------------------------------

    /**
     * 点击的点
     */
    private PointF clickPoint = new PointF();
    /**
     * 设置的双击检查时间间隔
     */
    private long doubleClickTimeSpan = 200;
    /**
     * 上次点击的时间
     */
    private long lastClickTime = 0;
    /**
     * 双击放大的倍数
     */
    private int doubleClickZoom = 2;
    /**
     * 当前缩放的模式
     */
    private ZoomMode zoomInMode = ZoomMode.ORDINARY;
    /**
     * 临时坐标比例数据
     */
    private PointF tempPoint = new PointF();

    // ----------------------------------------------------------------------------------
    //
    // 双指缩放参数
    //
    // ----------------------------------------------------------------------------------

    /**
     * 最大缩放比例
     */
    private float maxScale = 4;
    /**
     * 两点之间的距离
     */
    private float doublePointDistance = 0;
    /**
     * 双指缩放时候的中心点
     */
    private PointF doublePointCenter = new PointF();
    /**
     * 两指缩放的比例
     */
    private float doubleFingerScale = 0;
    /**
     * 上次触碰的手指数量
     */
    private int lastFingerNum = 0;

    private WeakDataHolder weakDataHolder;

    // ----------------------------------------------------------------------------------
    //
    // 主要方法
    //
    // ----------------------------------------------------------------------------------

    public ZoomImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();

        weakDataHolder = WeakDataHolder.getInstance();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        viewSize = new PointF(width, height);
        Drawable drawable = getDrawable();
        if (drawable != null) {
            imageSize = new PointF(drawable.getMinimumWidth(), drawable.getMinimumHeight());
            showCenter();
        }
    }

    /**
     * 手指触碰事件
     *
     * @param event The motion event.
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchActionDown(event);

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionPointerDown(event);

                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionPointerUp(event);

                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);

                break;
            case MotionEvent.ACTION_UP:
                actionUp(event);
               weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);


                break;
        }

        return true;
    }

    /**
     * 手指按下事件
     *
     * @param event
     */
    private void touchActionDown(MotionEvent event) {
        // 记录被点击的点的坐标
        clickPoint.set(event.getX(), event.getY());
        // 判断屏幕上此时被按住的点的个数，当前屏幕只有一个点被点击的时候触发
        if (event.getPointerCount() == 1) {
            // 当缩放倍数大于原尺寸时，禁止ViewPager2滑动切换视图，反之则允许
            if (originScale.x < doubleFingerScale) {
               weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, false);
            } else {
               weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
            }

            // 设置一个点击的间隔时长，来判断是不是双击
            if (System.currentTimeMillis() - lastClickTime <= doubleClickTimeSpan) {
                // 如果图片此时缩放模式是普通模式，就触发双击放大
                if (zoomInMode == ZoomMode.ORDINARY) {
                    // 分别记录被点击的点到图片左上角x,y轴的距离与图片x,y轴边长的比例，方便在进行缩放后，算出这个点对应的坐标点
                    tempPoint.set((clickPoint.x - bitmapOriginPoint.x) / scaleSize.x, (clickPoint.y - bitmapOriginPoint.y) / scaleSize.y);
                    // 进行缩放
                    scaleImage(new PointF(originScale.x * doubleClickZoom, originScale.y * doubleClickZoom));
                    // 获取缩放后，图片左上角的xy坐标
                    getBitmapOffset();
                    // 平移图片，使得被点击的点的位置不变。这里是计算缩放后被点击的xy坐标，与原始点击的位置的xy 坐标值，计算出差值，然后做平移动作
                    translationImage(new PointF(clickPoint.x - (bitmapOriginPoint.x + tempPoint.x * scaleSize.x), clickPoint.y - (bitmapOriginPoint.y + tempPoint.y * scaleSize.y)));
                    zoomInMode = ZoomMode.ZOOM_IN;

                    // 在双击放大后记录缩放比例
                    doubleFingerScale = originScale.x * doubleClickZoom;
                } else {
                    //双击还原
                    showCenter();
                    zoomInMode = ZoomMode.ORDINARY;
                    doubleFingerScale = originScale.x;
                }
            } else {
                lastClickTime = System.currentTimeMillis();
            }
        } else if (event.getPointerCount() > 1){
           weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, false);
        }
    }

    /**
     * 屏幕上已经有一个点被按住了, 第二个点被按下时触发该事件
     *
     * @param event
     */
    private void actionPointerDown(MotionEvent event) {
        // 计算最初的两个手指之间的距离
        doublePointDistance = getDoubleFingerDistance(event);
       weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, false);
    }

    /**
     * 屏幕上已经有两个点按住, 再松开一个点时触发该事件
     *
     * @param event
     */
    private void actionPointerUp(MotionEvent event) {
        // 当有一个手指离开屏幕后，就修改状态，这样如果双击屏幕就能恢复到初始大小
        zoomInMode = ZoomMode.ZOOM_IN;
        // 记录此时的双指缩放比例
        doubleFingerScale = BigDecimalUtil.divide(scaleSize.x, imageSize.x).floatValue();
        // 记录此时屏幕触碰的点的数量
        lastFingerNum = 1;
        // 判断缩放后的比例，如果小于最初的那个比例，就恢复到最初的大小
        if (scaleSize.x < viewSize.x && scaleSize.y < viewSize.y) {
            zoomInMode = ZoomMode.ORDINARY;
            showCenter();
        }
       weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
    }

    /**
     * 手指移动时触发事件
     *
     * @param event
     */
    private void actionMove(MotionEvent event) {
        // 手指移动事件
        if (zoomInMode != ZoomMode.ORDINARY) {
            pointerMoveEvent(event);
        }
        // 双指缩放事件
        if (event.getPointerCount() == 2) {
            doublePointerScaleEvent(event);
        }
    }

    /**
     * 手指松开时触发事件
     *
     * @param event
     */
    private void actionUp(MotionEvent event) {
        lastFingerNum = 0;
       weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, true);
    }

    /**
     * 双指缩放事件
     *
     * @param event
     */
    private void doublePointerScaleEvent(MotionEvent event) {
       weakDataHolder.saveData(KeyConst.IS_ALLOW_MOVE_VIEW_PAGER2, false);

        // 判断当前是两个手指接触到屏幕才处理缩放事件
        // 如果此时缩放后的大小，大于等于设置的最大缩放的大小，就不处理
        BigDecimal xScale = BigDecimalUtil.divide(scaleSize.x, imageSize.x);
        BigDecimal xOrigin = BigDecimalUtil.multi(originScale.x, maxScale);
        BigDecimal yScale = BigDecimalUtil.divide(scaleSize.y, imageSize.y);
        BigDecimal yOrigin = BigDecimalUtil.multi(originScale.y, maxScale);
        if ((xScale.compareTo(xOrigin) > 0 || yScale.compareTo(yOrigin) > 0)
                && getDoubleFingerDistance(event) - doublePointDistance > 0) {
            return;
        }

        // 这里设置当双指缩放的的距离变化量大于50，并且当前不是在双指缩放状态下，
        // 就计算中心点，等一些操作
        if (Math.abs(getDoubleFingerDistance(event) - doublePointDistance) > 50
                && zoomInMode != ZoomMode.TOW_FINGER_ZOOM) {
            // 计算两个手指之间的中心点，当作放大的中心点
            doublePointCenter.set(
                    (event.getX(0) + event.getX(1)) / 2,
                    (event.getY(0) + event.getY(1)) / 2);

            // 将双指的中心点就假设为点击的点
            clickPoint.set(doublePointCenter);
            // 下面就和双击放大基本一样
            getBitmapOffset();
            // 分别记录被点击的点到图片左上角x,y轴的距离与图片x,y轴边长的比例，
            // 方便在进行缩放后，算出这个点对应的坐标点
            tempPoint.set(
                    (clickPoint.x - bitmapOriginPoint.x) / scaleSize.x,
                    (clickPoint.y - bitmapOriginPoint.y) / scaleSize.y);
            // 设置进入双指缩放状态
            zoomInMode = ZoomMode.TOW_FINGER_ZOOM;
        }

        // 如果已经进入双指缩放状态，就直接计算缩放的比例，并进行位移
        if (zoomInMode == ZoomMode.TOW_FINGER_ZOOM) {
            // 用当前的缩放比例与此时双指间距离的缩放比例相乘，就得到对应的图片应该缩放的比例
            float fingerScale = BigDecimalUtil.multi(doubleFingerScale, getDoubleFingerDistance(event)).floatValue();
            float scale = BigDecimalUtil.divide(fingerScale, doublePointDistance).floatValue();
            // 这里也是和双击放大时一样的
            scaleImage(new PointF(scale, scale));
            getBitmapOffset();
            translationImage(new PointF(
                    clickPoint.x - (bitmapOriginPoint.x + tempPoint.x * scaleSize.x),
                    clickPoint.y - (bitmapOriginPoint.y + tempPoint.y * scaleSize.y))
            );
        }
    }

    /**
     * 手指移动事件
     *
     * @param event
     */
    public void pointerMoveEvent(MotionEvent event) {
        // 如果是多指，计算中心点为假设的点击的点
        float currentX = 0;
        float currentY = 0;
        // 获取此时屏幕上被触碰的点有多少个
        int pointCount = event.getPointerCount();
        // 计算出中间点所在的坐标
        for (int i = 0; i < pointCount; i++) {
            currentX += event.getX(i);
            currentY += event.getY(i);
        }

        currentX /= pointCount;
        currentY /= pointCount;
        // 当屏幕被触碰的点的数量变化时，将最新算出来的中心点看作是被点击的点
        if (lastFingerNum != event.getPointerCount()) {
            clickPoint.x = currentX;
            clickPoint.y = currentY;
            lastFingerNum = event.getPointerCount();
        }

        // 将移动手指时，实时计算出来的中心点坐标，减去被点击点的坐标就得到了需要移动的距离
        float moveX = currentX - clickPoint.x;
        float moveY = currentY - clickPoint.y;
        // 计算边界，使得不能已出边界，但是如果是双指缩放时移动，因为存在缩放效果，
        // 所以此时的边界判断无效
        float[] moveFloat = moveBorderDistance(moveX, moveY);
        // 处理移动图片的事件
        translationImage(new PointF(moveFloat[0], moveFloat[1]));
        clickPoint.set(currentX, currentY);
    }

    /**
     * 计算两个手指间的距离
     *
     * @param event
     * @return
     */
    public static float getDoubleFingerDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 设置图片居中等比显示
     */
    private void showCenter() {
        float scaleX = viewSize.x / imageSize.x;
        float scaleY = viewSize.y / imageSize.y;
        float scale = Math.min(scaleX, scaleY);

        scaleImage(new PointF(scale, scale));
        // 移动图片，并保存最初的图片左上角（即原点）所在坐标
        if (scaleX < scaleY) {
            bitmapOriginPoint.x = 0;
            bitmapOriginPoint.y = viewSize.y / 2 - scaleSize.y / 2;
            translationImage(new PointF(bitmapOriginPoint.x, bitmapOriginPoint.y));
        } else {
            bitmapOriginPoint.x = viewSize.x / 2 - scaleSize.x / 2;
            bitmapOriginPoint.y = 0;
            translationImage(new PointF(bitmapOriginPoint.x, bitmapOriginPoint.y));
        }
        // 保存下最初的缩放比例
        originScale.set(scale, scale);
        doubleFingerScale = scale;
    }


    /**
     * 缩放图片
     * @param scaleXY
     */
    public void scaleImage(PointF scaleXY) {
        matrix.setScale(scaleXY.x, scaleXY.y);
        scaleSize.set(scaleXY.x * imageSize.x, scaleXY.y * imageSize.y);
        setImageMatrix(matrix);
    }

    /**
     * 对图片进行x和y轴方向的平移
     *
     * @param pointF
     */
    public void translationImage(PointF pointF) {
        matrix.postTranslate(pointF.x, pointF.y);
        setImageMatrix(matrix);
    }

    /**
     * 获取view中bitmap的坐标点
     */
    public void getBitmapOffset() {
        float[] value = new float[9];
        float[] offset = new float[2];
        Matrix imageMatrix = getImageMatrix();
        imageMatrix.getValues(value);
        offset[0] = value[2];
        offset[1] = value[5];
        bitmapOriginPoint.set(offset[0], offset[1]);
    }

    /**
     * 防止移动图片超过边界，计算边界情况
     *
     * @param moveX
     * @param moveY
     * @return
     */
    public float[] moveBorderDistance(float moveX, float moveY) {
        // 计算bitmap的左上角坐标
        getBitmapOffset();
        // 计算bitmap的右下角坐标
        float bitmapRightBottomX = bitmapOriginPoint.x + scaleSize.x;
        float bitmapRightBottomY = bitmapOriginPoint.y + scaleSize.y;

        if (moveY > 0) {
            // 向下滑
            if (bitmapOriginPoint.y + moveY > 0) {
                if (bitmapOriginPoint.y < 0) {
                    moveY = -bitmapOriginPoint.y;
                } else {
                    moveY = 0;
                }
            }
        } else if (moveY < 0) {
            // 向上滑
            if (bitmapRightBottomY + moveY < viewSize.y) {
                if (bitmapRightBottomY > viewSize.y) {
                    moveY = -(bitmapRightBottomY - viewSize.y);
                } else {
                    moveY = 0;
                }
            }
        }

        if (moveX > 0) {
            // 向右滑
            if (bitmapOriginPoint.x + moveX > 0) {
                if (bitmapOriginPoint.x < 0) {
                    moveX = -bitmapOriginPoint.x;
                } else {
                    moveX = 0;
                }
            }
        } else if (moveX < 0) {
            // 向左滑
            if (bitmapRightBottomX + moveX < viewSize.x) {
                if (bitmapRightBottomX > viewSize.x) {
                    moveX = -(bitmapRightBottomX - viewSize.x);
                } else {
                    moveX = 0;
                }
            }
        }
        return new float[]{moveX, moveY};
    }
}
