package nts.uk.ctx.core.dom.socialinsurance;

import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundCalculatedValue {

    public static final int ROUND_1_AFTER_DOT = 0;
    public static final int ROUND_3_AFTER_DOT = 2;

    /**
     * 計算した値を端数処理する
     *
     * @param standardMonthlyFee standardMonthlyFee
     * @param rate               value
     * @param fractionCls        InsurancePremiumFractionClassification
     * @return Round value
     */
    public static BigDecimal calculation(long standardMonthlyFee, BigDecimal rate,
                                         InsurancePremiumFractionClassification fractionCls, int scale) {
        Double calculation = standardMonthlyFee * rate.doubleValue() / 1000;
        switch (fractionCls) {
            // 切り捨て
            case TRUNCATION:
                return new BigDecimal(calculation).setScale(scale, RoundingMode.DOWN).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
            // 切り上げ
            case ROUND_UP:
                return new BigDecimal(calculation).setScale(scale, RoundingMode.UP).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
            // 四捨五入
            case ROUND_4_UP_5:
                return new BigDecimal(calculation).setScale(scale, RoundingMode.HALF_UP).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
            // 五捨六入
            case ROUND_SUPER_5:
                return new BigDecimal(calculation).setScale(scale, RoundingMode.HALF_DOWN).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
            // 五捨五超入
            case ROUND_5_UP_6:
                return new BigDecimal(calculation + (ROUND_1_AFTER_DOT == scale ? 0.400 : 0.004)).setScale(scale, RoundingMode.DOWN).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
            default:
                return new BigDecimal(0).setScale(ROUND_3_AFTER_DOT, RoundingMode.UP);
        }
    }
}
