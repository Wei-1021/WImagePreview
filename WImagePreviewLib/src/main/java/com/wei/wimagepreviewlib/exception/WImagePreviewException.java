package com.wei.wimagepreviewlib.exception;

import android.content.Context;

import com.wei.wimagepreviewlib.R;
import com.wei.wimagepreviewlib.utils.WTools;

import java.io.IOException;

/**
 * 异常处理
 *
 * @author weizhanjie
 */
public class WImagePreviewException extends RuntimeException {

    public enum ExceptionType {
        /**
         * 图片为空
         */
        IMAGE_EMPTY,
        /**
         * 图片集合为空
         */
        IMAGE_LIST_EMPTY,
        /**
         * 图片类型无效
         */
        IMAGE_TYPE_INVALID,
        /**
         * 数组越界
         */
        ARRAY_OUT_OF_BOUNDS,
        /**
         * 无效参数
         */
        ILLEGAL_ARGUMENT,
    }

    public WImagePreviewException() {
        super();
    }

    public WImagePreviewException(String message) {
        super(message);
    }

    public WImagePreviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public WImagePreviewException(Throwable cause) {
        super(cause);
    }

    protected WImagePreviewException(String message,
                                     Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 根据异常类型设置异常信息
     *
     * @param context       Context
     * @param exceptionType 异常类型，
     */
    public static void setExceptionByType(Context context, ExceptionType exceptionType) {
        String msg = "";
        switch (exceptionType) {
            case IMAGE_EMPTY:
                msg = context.getString(R.string.exception_image_empty);
                throw new WImagePreviewException(msg, new IOException(msg));

            case IMAGE_LIST_EMPTY:
                msg = context.getString(R.string.exception_image_empty) +
                        context.getString(R.string.exception_image_list_empty);
                throw new WImagePreviewException(msg, new IOException(msg));

            case IMAGE_TYPE_INVALID:
                String[] imageTypes = context.getResources().getStringArray(R.array.support_image_type);
                msg = context.getString(R.string.exception_image_type_invalid) +
                        "Only support:" +
                        String.join(",", imageTypes);
                throw new WImagePreviewException(msg, new IOException(msg));

            case ARRAY_OUT_OF_BOUNDS:
                msg = context.getString(R.string.exception_array_out_of_bounds);
                throw new WImagePreviewException(msg, new ArrayIndexOutOfBoundsException(msg));

            case ILLEGAL_ARGUMENT:
                throw new WImagePreviewException(new IllegalArgumentException());
            default:
                break;
        }
    }

    /**
     * 判断当前文件对象是否为支持的图片类型，不是则抛出异常
     *
     * @param context
     * @param img
     */
    public static void setExceptionImage(Context context, Object img) {
        if (!WTools.isSupportImage(img)) {
            String[] imageTypes = context.getResources().getStringArray(R.array.support_image_type);
            String msg = context.getString(R.string.exception_image_type_invalid) +
                    img.getClass().getName() + " is not supported, " +
                    "Only support:" +
                    String.join(",", imageTypes);

            throw new WImagePreviewException(msg);
        }
    }

}
