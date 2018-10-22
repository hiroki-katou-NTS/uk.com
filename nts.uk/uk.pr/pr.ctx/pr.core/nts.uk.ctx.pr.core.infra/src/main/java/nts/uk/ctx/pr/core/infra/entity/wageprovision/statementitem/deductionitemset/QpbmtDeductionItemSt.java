package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.deductionitemset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 控除項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_DEDUCTION_ITEM_ST")
public class QpbmtDeductionItemSt extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtDeductionItemStPk deductionItemStPk;

    /**
     * 控除項目区分
     */
    @Basic(optional = false)
    @Column(name = "DEDUCTION_ITEM_ATR")
    public int deductionItemAtr;

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
        return deductionItemStPk;
    }

    public DeductionItemSet toDomain() {
        return new DeductionItemSet(this.deductionItemStPk.cid, this.deductionItemStPk.categoryAtr, this.deductionItemStPk.itemNameCd,
                this.deductionItemAtr, this.breakdownItemUseAtr,
                this.errorUpperLimitSetAtr, this.errorUpRangeVal, this.errorLowerLimitSetAtr, this.errorLoRangeVal,
                this.alarmUpperLimitSetAtr, this.alarmUpRangeVal, this.alarmLowerLimitSetAtr, this.alarmLoRangeVal,
                this.note);
    }

    public static QpbmtDeductionItemSt toEntity(DeductionItemSet domain) {
        QpbmtDeductionItemSt entity = new QpbmtDeductionItemSt();
        entity.deductionItemStPk = new QpbmtDeductionItemStPk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCode().v());
        entity.deductionItemAtr = domain.getDeductionItemAtr().value;
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
