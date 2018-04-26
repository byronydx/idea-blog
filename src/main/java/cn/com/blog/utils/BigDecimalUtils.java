package cn.com.blog.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>Title: BigDecimalUtils. </p>
 * <p>BigDecimal </p>
 *
 * @Author hiyond
 * @CreateDate 2017/4/5
 */
public class BigDecimalUtils {

    //默认精度
    static final int DEFAULT_SCALE = 3;
    //默认舍入方式
    static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * 字符串转BigDecimal
     *
     * @param val
     * @return
     */
    public static BigDecimal String2BigDecimal(String val) {
        try {
            return new BigDecimal(val);
        } catch (Exception e) {
        }
        return BigDecimal.ZERO;
    }

    /**
     * BigDecimal的减法运算
     * 如果参数为空，默认值为0
     *
     * @param val1
     * @param val2
     * @return
     */
    public static BigDecimal decimalSubtract(BigDecimal val1, BigDecimal val2) {
        val1 = (val1 == null) ? BigDecimal.ZERO : val1;
        val2 = (val2 == null) ? BigDecimal.ZERO : val2;
        return val1.subtract(val2);
    }

    /**
     * 多个BigDecimal参数的加法运算
     * 如果参数为空，默认值为0
     *
     * @param decimals
     * @return
     */
    public static BigDecimal decimalsAdd(BigDecimal... decimals) {
        if (decimals == null || decimals.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal bigDecimal : decimals) {
            bigDecimal = bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
            sum = sum.add(bigDecimal);
        }
        return sum;
    }

    /**
     * 返回多个BigDecimal参数中相乘的值
     *
     * @param bigDecimalScale 精度设置
     * @param values          多个BigDecimal参数
     * @return
     */
    public static BigDecimal decimalMultiply(BigDecimalScale bigDecimalScale, BigDecimal... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        int count = 0;
        BigDecimal resultValue = null;
        for (BigDecimal value : values) {
            if (value == null) {
                continue;
            }
            if (count == 0) {
                resultValue = value;
                count++;
                continue;
            }
            resultValue = resultValue.multiply(value);
        }
        if (bigDecimalScale != null && resultValue != null) {
            resultValue.setScale(bigDecimalScale.getScale(), bigDecimalScale.getRoundingMode());
        }
        return resultValue;
    }

    /**
     * 返回多个BigDecimal参数中相除的值
     * 如果指定精度为空，默认制订了一个
     *
     * @param bigDecimalScale 指定精度
     * @param values
     * @return
     */
    public static BigDecimal decimalDivide(BigDecimalScale bigDecimalScale, BigDecimal... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        int count = 0;
        BigDecimal resultValue = null;
        for (BigDecimal value : values) {
            if (value == null) {
                continue;
            }
            if (count == 0) {
                resultValue = value;
                count++;
                if (bigDecimalScale == null) {
                    //如果精度为空，实例化一个默认的，写在这里是为了防止无效的实例化或过多的实例化
                    bigDecimalScale = new BigDecimalUtils().new BigDecimalScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
                }
                continue;
            }
            resultValue = resultValue.divide(value, bigDecimalScale.getScale(), bigDecimalScale.getRoundingMode());
        }
        return resultValue;
    }

    /**
     * 返回多个BigDecimal参数中最大的值
     * 规则:
     * 如果没有参数返回0
     * 如果参数为空，跳过当前的参数
     * 如果全部为空，则返回结果为空
     *
     * @param values
     * @return
     */
    public static BigDecimal getMaxNumber(BigDecimal... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        int exeCount = 0;
        BigDecimal resultVal = null;
        for (BigDecimal value : values) {
            if (value == null) {
                continue;
            }
            if (exeCount == 0) {
                resultVal = value;
                exeCount++;
                continue;
            }
            resultVal = value.compareTo(resultVal) >= 0 ? value : resultVal;
        }
        return resultVal;
    }


    public class BigDecimalScale {
        private int scale;

        private RoundingMode roundingMode;

        public BigDecimalScale(int scale, RoundingMode roundingMode) {
            if (scale < 0) {
                throw new IllegalArgumentException("scale不能小于0");
            }
            if (roundingMode == null) {
                throw new IllegalArgumentException("roundingMode不能为空");
            }
            this.scale = scale;
            this.roundingMode = roundingMode;
        }

        public BigDecimalScale(int scale, int roundingMode) {
            this.scale = scale;
            this.roundingMode = RoundingMode.valueOf(roundingMode);
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public RoundingMode getRoundingMode() {
            return roundingMode;
        }

        public void setRoundingMode(RoundingMode roundingMode) {
            this.roundingMode = roundingMode;
        }
    }

    /**
     * 将BigDecimal为空数据转换为0
     *
     * @param val
     * @return
     */
    public static BigDecimal convertEmptyData2ZERO(BigDecimal val) {

        return null == val ? new BigDecimal("0") : val;

    }

}
