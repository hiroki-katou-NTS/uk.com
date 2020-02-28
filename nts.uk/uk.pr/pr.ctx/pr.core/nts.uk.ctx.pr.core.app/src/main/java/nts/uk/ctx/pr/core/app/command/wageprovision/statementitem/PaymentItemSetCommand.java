package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;

import java.math.BigDecimal;

@Value
public class PaymentItemSetCommand {

    /**
     * 課税区分
     */
    private int taxAtr;

    /**
     * 課税金額区分
     */
    private Integer taxableAmountAtr;

    /**
     * 限度金額
     */
    private Long limitAmount;

    /**
     * 限度金額区分
     */
    private Integer limitAmountAtr;

    /**
     * 非課税限度額コード
     */
    private String taxLimitAmountCode;

    /**
     * 社会保険区分
     */
    private int socialInsuranceCategory;

    /**
     * 労働保険区分
     */
    private int laborInsuranceCategory;

    /**
     * 固定的賃金の設定区分
     */
    private int settingAtr;

    /**
     * 全員一律の設定
     */
    private Integer everyoneEqualSet;

    /**
     * 月給者
     */
    private Integer monthlySalary;

    /**
     * 時給者
     */
    private Integer hourlyPay;

    /**
     * 日給者
     */
    private Integer dayPayee;

    /**
     * 日給月給者
     */
    private Integer monthlySalaryPerday;

    /**
     * 平均賃金区分
     */
    private int averageWageAtr;

    /**
     * 内訳項目利用区分
     */
    private int breakdownItemUseAtr;

    /**
     * 値設定区分
     */
    private int errorUpperLimitSetAtr;

    /**
     * 範囲値
     */
    private BigDecimal errorUpRangeVal;

    /**
     * 値設定区分
     */
    private int errorLowerLimitSetAtr;

    /**
     * 範囲値
     */
    private BigDecimal errorLoRangeVal;

    /**
     * 値設定区分
     */
    private int alarmUpperLimitSetAtr;

    /**
     * 範囲値
     */
    private BigDecimal alarmUpRangeVal;

    /**
     * 値設定区分
     */
    private int alarmLowerLimitSetAtr;

    /**
     * 範囲値
     */
    private BigDecimal alarmLoRangeVal;

    /**
     * 備考
     */
    private String note;

    public PaymentItemSet toDomain(String cid, int categoryAtr, String itemNameCd) {
        return new PaymentItemSet(cid, categoryAtr, itemNameCd,
                taxAtr, taxableAmountAtr, limitAmount, limitAmountAtr, taxLimitAmountCode,
                socialInsuranceCategory, laborInsuranceCategory,
                settingAtr, everyoneEqualSet, monthlySalary, hourlyPay, dayPayee, monthlySalaryPerday,
                averageWageAtr, breakdownItemUseAtr,
                errorUpperLimitSetAtr, errorUpRangeVal, errorLowerLimitSetAtr, errorLoRangeVal,
                alarmUpperLimitSetAtr, alarmUpRangeVal, alarmLowerLimitSetAtr, alarmLoRangeVal,
                note);
    }
}
