package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.timeitemset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤怠項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_TIME_ITEM_ST")
public class QpbmtTimeItemSt extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtTimeItemStPk timeItemStPk;

    /**
     * 時間回数区分
     */
    @Basic(optional = false)
    @Column(name = "TIME_COUNT_ATR")
    public int timeCountAtr;

    /**
     * 所定労働日数区分
     */
    @Basic(optional = true)
    @Column(name = "WORKING_DAYS_PER_YEAR")
    public Integer workingDaysPerYear;

    /**
     * 平均賃金区分
     */
    @Basic(optional = true)
    @Column(name = "AVERAGE_WAGE_ATR")
    public Integer averageWageAtr;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ERROR_UPPER_LIMIT_SET_ATR")
    public int errorUpperLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL_TIME")
    public Integer errorUpRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL_NUM")
    public BigDecimal errorUpRangeValNum;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ERROR_LOWER_LIMIT_SET_ATR")
    public int errorLowerLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL_TIME")
    public Integer errorLoRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL_NUM")
    public BigDecimal errorLoRangeValNum;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ALARM_UPPER_LIMIT_SET_ATR")
    public int alarmUpperLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL_TIME")
    public Integer alarmUpRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL_NUM")
    public BigDecimal alarmUpRangeValNum;

    /**
     * 値設定区分
     */
    @Basic(optional = false)
    @Column(name = "ALARM_LOWER_LIMIT_SET_ATR")
    public int alarmLowerLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL_TIME")
    public Integer alarmLoRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL_NUM")
    public BigDecimal alarmLoRangeValNum;

    /**
     * 備考
     */
    @Basic(optional = true)
    @Column(name = "NOTE")
    public String note;

    @Override
    protected Object getKey() {
        return timeItemStPk;
    }

    public TimeItemSet toDomain() {
        return new TimeItemSet(this.timeItemStPk.cid, this.timeItemStPk.categoryAtr, this.timeItemStPk.itemNameCd,
                this.timeCountAtr, this.workingDaysPerYear, this.averageWageAtr,
                this.errorUpperLimitSetAtr, this.errorUpRangeValTime, this.errorUpRangeValNum,
                this.errorLowerLimitSetAtr, this.errorLoRangeValTime, this.errorLoRangeValNum,
                this.alarmUpperLimitSetAtr, this.alarmUpRangeValTime, this.alarmUpRangeValNum,
                this.alarmLowerLimitSetAtr, this.alarmLoRangeValTime, this.alarmLoRangeValNum,
                this.note);
    }

    public static QpbmtTimeItemSt toEntity(TimeItemSet domain) {
        QpbmtTimeItemSt entity = new QpbmtTimeItemSt();
        entity.timeItemStPk = new QpbmtTimeItemStPk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCode().v());
        entity.timeCountAtr = domain.getTimeCountAtr().value;
        entity.workingDaysPerYear = domain.getWorkingDaysPerYear().map(i -> i.value).orElse(null);
        entity.averageWageAtr = domain.getAverageWageAtr().map(i -> i.value).orElse(null);
        entity.errorUpperLimitSetAtr = domain.getErrorRangeSetting().getUpperLimitSetting().getValueSettingAtr().value;
        entity.errorUpRangeValTime = domain.getErrorRangeSetting().getUpperLimitSetting().getTimeValue()
                .map(i -> i.v()).orElse(null);
        entity.errorUpRangeValNum = domain.getErrorRangeSetting().getUpperLimitSetting().getTimesValue()
                .map(i -> i.v()).orElse(null);
        entity.errorLowerLimitSetAtr = domain.getErrorRangeSetting().getLowerLimitSetting().getValueSettingAtr().value;
        entity.errorLoRangeValTime = domain.getErrorRangeSetting().getLowerLimitSetting().getTimeValue()
                .map(i -> i.v()).orElse(null);
        entity.errorLoRangeValNum = domain.getErrorRangeSetting().getLowerLimitSetting().getTimesValue()
                .map(i -> i.v()).orElse(null);
        entity.alarmUpperLimitSetAtr = domain.getAlarmRangeSetting().getUpperLimitSetting().getValueSettingAtr().value;
        entity.alarmUpRangeValTime = domain.getAlarmRangeSetting().getUpperLimitSetting().getTimeValue()
                .map(i -> i.v()).orElse(null);
        entity.alarmUpRangeValNum = domain.getAlarmRangeSetting().getUpperLimitSetting().getTimesValue()
                .map(i -> i.v()).orElse(null);
        entity.alarmLowerLimitSetAtr = domain.getAlarmRangeSetting().getLowerLimitSetting().getValueSettingAtr().value;
        entity.alarmLoRangeValTime = domain.getAlarmRangeSetting().getLowerLimitSetting().getTimeValue()
                .map(i -> i.v()).orElse(null);
        entity.alarmLoRangeValNum = domain.getAlarmRangeSetting().getLowerLimitSetting().getTimesValue()
                .map(i -> i.v()).orElse(null);
        entity.note = domain.getNote().map(i -> i.v()).orElse(null);
        return entity;
    }

}
