package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 支給項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAYMENT_ITEM_ST")
public class QpbmtPaymentItemSt extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtPaymentItemStPk paymentItemStPk;

    /**
     * 課税区分
     */
    @Basic(optional = false)
    @Column(name = "TAX_ATR")
    public int taxAtr;

    /**
     * 課税金額区分
     */
    @Basic(optional = true)
    @Column(name = "TAXABLE_AMOUNT_ATR")
    public Integer taxableAmountAtr;

    /**
     * 限度金額区分
     */
    @Basic(optional = true)
    @Column(name = "LIMIT_AMOUNT_ATR")
    public Integer limitAmountAtr;

    /**
     * 限度金額
     */
    @Basic(optional = true)
    @Column(name = "LIMIT_AMOUNT")
    public Long limitAmount;

    /**
     * 非課税限度額コード
     */
    @Basic(optional = true)
    @Column(name = "TAX_LIMIT_AMOUNT_CODE")
    public String taxLimitAmountCode;

    /**
     * 社会保険区分
     */
    @Basic(optional = false)
    @Column(name = "SOCIAL_INSURANCE_CATEGORY")
    public int socialInsuranceCategory;

    /**
     * 労働保険区分
     */
    @Basic(optional = false)
    @Column(name = "LABOR_INSURANCE_CATEGORY")
    public int laborInsuranceCategory;

    /**
     * 設定区分
     */
    @Basic(optional = false)
    @Column(name = "SETTING_ATR")
    public int settingAtr;

    /**
     * 全員一律の設定
     */
    @Basic(optional = true)
    @Column(name = "EVERYONE_EQUAL_SET")
    public Integer everyoneEqualSet;

    /**
     * 月給者
     */
    @Basic(optional = true)
    @Column(name = "MONTHLY_SALARY")
    public Integer monthlySalary;

    /**
     * 日給月給者
     */
    @Basic(optional = true)
    @Column(name = "MONTHLY_SALARY_PERDAY")
    public Integer monthlySalaryPerday;

    /**
     * 時給者
     */
    @Basic(optional = true)
    @Column(name = "HOURLY_PAY")
    public Integer hourlyPay;

    /**
     * 日給者
     */
    @Basic(optional = true)
    @Column(name = "DAY_PAYEE")
    public Integer dayPayee;

    /**
     * 平均賃金区分
     */
    @Basic(optional = false)
    @Column(name = "AVERAGE_WAGE_ATR")
    public int averageWageAtr;

    /**
     * 内訳項目利用区分
     */
    @Basic(optional = false)
    @Column(name = "BREAKDOWN_ITEM_USE_ATR")
    public int breakdownItemUseAtr;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ERROR_UPPER_LIMIT_SET_ATR")
    public int errorUpperLimitSetAtr;

    /**
     * 範囲値
     */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL")
    public BigDecimal errorUpRangeVal;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ERROR_LOWER_LIMIT_SET_ATR")
    public int errorLowerLimitSetAtr;

    /**
     * 範囲値
     */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL")
    public BigDecimal errorLoRangeVal;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ALARM_UPPER_LIMIT_SET_ATR")
    public int alarmUpperLimitSetAtr;

    /**
     * 範囲値
     */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL")
    public BigDecimal alarmUpRangeVal;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ALARM_LOWER_LIMIT_SET_ATR")
    public int alarmLowerLimitSetAtr;

    /**
     * 範囲値
     */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL")
    public BigDecimal alarmLoRangeVal;

    /**
     * 備考
     */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;

    @Override
    protected Object getKey() {
        return paymentItemStPk;
    }

    public PaymentItemSet toDomain() {
        return new PaymentItemSet(paymentItemStPk.cid, paymentItemStPk.categoryAtr, paymentItemStPk.itemNameCd,
                taxAtr, taxableAmountAtr, limitAmount, limitAmountAtr, taxLimitAmountCode,
                socialInsuranceCategory, laborInsuranceCategory,
                settingAtr, everyoneEqualSet, monthlySalary, hourlyPay, dayPayee, monthlySalaryPerday,
                averageWageAtr, breakdownItemUseAtr,
                errorUpperLimitSetAtr, errorUpRangeVal, errorLowerLimitSetAtr, errorLoRangeVal,
                alarmUpperLimitSetAtr, alarmUpRangeVal, alarmLowerLimitSetAtr, alarmLoRangeVal,
                note);
    }

    public static QpbmtPaymentItemSt toEntity(PaymentItemSet domain) {
        QpbmtPaymentItemSt entity = new QpbmtPaymentItemSt();
        entity.paymentItemStPk = new QpbmtPaymentItemStPk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCode().v());
        entity.taxAtr = domain.getTaxAtr().value;
        entity.taxableAmountAtr = domain.getLimitAmountSetting().getTaxableAmountAtr().map(i -> i.value).orElse(null);
        entity.limitAmountAtr = domain.getLimitAmountSetting().getTaxableAmountAtr().map(i -> i.value).orElse(null);
        entity.limitAmount = domain.getLimitAmountSetting().getLimitAmount().map(i -> i.v()).orElse(null);
        entity.taxLimitAmountCode = domain.getLimitAmountSetting().getTaxLimitAmountCode().map(i -> i.v()).orElse(null);
        entity.socialInsuranceCategory = domain.getSocialInsuranceCategory().value;
        entity.laborInsuranceCategory = domain.getLaborInsuranceCategory().value;
        entity.settingAtr = domain.getFixedWage().getSettingAtr().value;
        entity.everyoneEqualSet = domain.getFixedWage().getEveryoneEqualSet().map(i -> i.value).orElse(null);
        entity.monthlySalary = domain.getFixedWage().getPerSalaryContractType().getMonthlySalary().map(i -> i.value).orElse(null);
        entity.monthlySalaryPerday = domain.getFixedWage().getPerSalaryContractType().getMonthlySalaryPerday().map(i -> i.value).orElse(null);
        entity.hourlyPay =  domain.getFixedWage().getPerSalaryContractType().getHourlyPay().map(i -> i.value).orElse(null);
        entity.dayPayee = domain.getFixedWage().getPerSalaryContractType().getDayPayee().map(i -> i.value).orElse(null);
        entity.averageWageAtr = domain.getAverageWageAtr().value;
        entity.breakdownItemUseAtr = domain.getBreakdownItemUseAtr().value;
        entity.errorUpperLimitSetAtr = domain.getErrorRangeSetting().getUpperLimitSetting().getValueSettingAtr().value;
        entity.errorUpRangeVal = domain.getErrorRangeSetting().getUpperLimitSetting().getRangeValue().map(i -> i.v()).orElse(null);
        entity.errorLowerLimitSetAtr = domain.getErrorRangeSetting().getLowerLimitSetting().getValueSettingAtr().value;
        entity.errorLoRangeVal = domain.getErrorRangeSetting().getLowerLimitSetting().getRangeValue().map(i -> i.v()).orElse(null);
        entity.alarmUpperLimitSetAtr = domain.getAlarmRangeSetting().getUpperLimitSetting().getValueSettingAtr().value;
        entity.alarmUpRangeVal = domain.getAlarmRangeSetting().getUpperLimitSetting().getRangeValue().map(i -> i.v()).orElse(null);
        entity.alarmLowerLimitSetAtr = domain.getAlarmRangeSetting().getLowerLimitSetting().getValueSettingAtr().value;
        entity.alarmLoRangeVal = domain.getAlarmRangeSetting().getLowerLimitSetting().getRangeValue().map(i -> i.v()).orElse(null);
        entity.note = domain.getNote().map(i -> i.v()).orElse(null);
        return entity;
    }
}
