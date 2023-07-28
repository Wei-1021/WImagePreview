package com.wei.wimagepreviewlib.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal工具类
 */
public class BigDecimalUtil {

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @return
     */
    public static BigDecimal divide(float dividend,
                                    float divisor) {
        return divide(new BigDecimal(String.valueOf(dividend)), new BigDecimal(String.valueOf(divisor)));
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @return
     */
    public static BigDecimal divide(int dividend,
                                    int divisor) {
        return divide(new BigDecimal(String.valueOf(dividend)), new BigDecimal(String.valueOf(divisor)));
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @return
     */
    public static BigDecimal divide(String dividend,
                                    String divisor) {
        return divide(new BigDecimal(dividend), new BigDecimal(divisor));
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend,
                                    BigDecimal divisor) {
        return divide(dividend, divisor, 2);
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @param accuracy 精度，精确到小数点后几位
     * @return
     */
    public static BigDecimal divide(float dividend,
                                    float divisor,
                                    int accuracy) {
        return new BigDecimal(Float.toString(dividend))
                .divide(new BigDecimal(Float.toString(divisor)), accuracy, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @param accuracy 精度，精确到小数点后几位
     * @return
     */
    public static BigDecimal divide(int dividend,
                                    int divisor,
                                    int accuracy) {
        return new BigDecimal(Integer.toString(dividend))
                .divide(new BigDecimal(Integer.toString(divisor)), accuracy, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @param accuracy 精度，精确到小数点后几位
     * @return
     */
    public static BigDecimal divide(String dividend,
                                    String divisor,
                                    int accuracy) {
        return new BigDecimal(dividend).divide(new BigDecimal(divisor), accuracy, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     *
     * @param dividend 被除数（分母）
     * @param divisor  除数（分子）
     * @param accuracy 精度，精确到小数点后几位
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend,
                                    BigDecimal divisor,
                                    int accuracy) {
        return dividend.divide(divisor, accuracy, RoundingMode.HALF_UP);
    }

    /**
     * 乘法
     *
     * @param multiplicand
     * @param multiplier
     * @return
     */
    public static BigDecimal multi(float multiplicand,
                                   float multiplier) {
        return new BigDecimal(String.valueOf(multiplicand)).multiply(new BigDecimal(String.valueOf(multiplier)));
    }

    /**
     * 乘法
     *
     * @param multiplicand
     * @param multiplier
     * @return
     */
    public static BigDecimal multi(int multiplicand,
                                   int multiplier) {
        return new BigDecimal(String.valueOf(multiplicand)).multiply(new BigDecimal(String.valueOf(multiplier)));
    }

    /**
     * 乘法
     *
     * @param multiplicand
     * @param multiplier
     * @return
     */
    public static BigDecimal multi(String multiplicand,
                                   String multiplier) {
        return new BigDecimal(multiplicand).multiply(new BigDecimal(multiplier));
    }

    /**
     * 乘法
     *
     * @param multiplicand
     * @param multiplier
     * @return
     */
    public static BigDecimal multi(BigDecimal multiplicand,
                                   BigDecimal multiplier) {
        return multiplicand.multiply(multiplier);
    }
}
