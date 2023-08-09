package com.wei.wimagepreviewlib.exception;

/**
 * 异常处理
 *
 * @author weizhanjie
 */
public class WImagePreviewException extends RuntimeException {

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

}
