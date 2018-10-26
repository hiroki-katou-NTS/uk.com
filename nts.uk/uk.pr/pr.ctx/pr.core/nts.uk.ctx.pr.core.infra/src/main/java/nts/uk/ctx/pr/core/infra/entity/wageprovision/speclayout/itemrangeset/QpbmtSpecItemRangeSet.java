package nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.itemrangeset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset.SpecificationItemRangeSetting;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* 明細書項目範囲設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SPEC_ITEM_RANGE_SET")
public class QpbmtSpecItemRangeSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSpecItemRangeSetPk specItemRangeSetPk;
    
    /**
    * 開始日
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了日
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    /**
    * 給与項目ID
    */
    @Basic(optional = false)
    @Column(name = "SALARY_ITEM_ID")
    public String salaryItemId;
    
    /**
    * 範囲値の属性
    */
    @Basic(optional = false)
    @Column(name = "RANGE_VAL_ATTRIBUTE")
    public int rangeValAttribute;
    
    /**
    * エラー上限値設定区分
    */
    @Basic(optional = false)
    @Column(name = "ERROR_UPPER_LIMIT_SET_ATR")
    public int errorUpperLimitSetAtr;
    
    /**
    * エラー上限値金額
    */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL_AMOUNT")
    public Long errorUpRangeValAmount;
    
    /**
    * エラー上限値時間
    */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL_TIME")
    public Integer errorUpRangeValTime;
    
    /**
    * エラー上限値回数
    */
    @Basic(optional = true)
    @Column(name = "ERROR_UP_RANGE_VAL_NUM")
    public BigDecimal errorUpRangeValNum;
    
    /**
    * エラー下限値設定区分
    */
    @Basic(optional = false)
    @Column(name = "ERROR_LOWER_LIMIT_SET_ATR")
    public int errorLowerLimitSetAtr;
    
    /**
    * エラー上限値金額
    */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL_AMOUNT")
    public Long errorLoRangeValAmount;
    
    /**
    * エラー上限値時間
    */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL_TIME")
    public Integer errorLoRangeValTime;
    
    /**
    * エラー上限値回数
    */
    @Basic(optional = true)
    @Column(name = "ERROR_LO_RANGE_VAL_NUM")
    public BigDecimal errorLoRangeValNum;
    
    /**
    * アラーム上限値設定区分
    */
    @Basic(optional = false)
    @Column(name = "ALARM_UPPER_LIMIT_SET_ATR")
    public int alarmUpperLimitSetAtr;
    
    /**
    * アラーム上限値金額
    */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL_AMOUNT")
    public Long alarmUpRangeValAmount;
    
    /**
    * アラーム上限値時間
    */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL_TIME")
    public Integer alarmUpRangeValTime;
    
    /**
    * アラーム上限値回数
    */
    @Basic(optional = true)
    @Column(name = "ALARM_UP_RANGE_VAL_NUM")
    public BigDecimal alarmUpRangeValNum;
    
    /**
    * アラーム下限値設定区分
    */
    @Basic(optional = false)
    @Column(name = "ALARM_LOWER_LIMIT_SET_ATR")
    public int alarmLowerLimitSetAtr;
    
    /**
    * アラーム上限値金額
    */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL_AMOUNT")
    public Long alarmLoRangeValAmount;
    
    /**
    * アラーム上限値時間
    */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL_TIME")
    public Integer alarmLoRangeValTime;
    
    /**
    * アラーム上限値回数
    */
    @Basic(optional = true)
    @Column(name = "ALARM_LO_RANGE_VAL_NUM")
    public BigDecimal alarmLoRangeValNum;
    
    @Override
    protected Object getKey()
    {
        return specItemRangeSetPk;
    }

    public SpecificationItemRangeSetting toDomain() {
        return new SpecificationItemRangeSetting(this.specItemRangeSetPk.histId, this.salaryItemId, this.rangeValAttribute, this.errorUpperLimitSetAtr, this.errorUpRangeValAmount, this.errorUpRangeValTime, this.errorUpRangeValNum, this.errorLowerLimitSetAtr, this.errorLoRangeValAmount, this.errorLoRangeValTime, this.errorLoRangeValNum, this.alarmUpperLimitSetAtr, this.alarmUpRangeValAmount, this.alarmUpRangeValTime, this.alarmUpRangeValNum, this.alarmLowerLimitSetAtr, this.alarmLoRangeValAmount, this.alarmLoRangeValTime, this.alarmLoRangeValNum);
    }

    public static QpbmtSpecItemRangeSet toEntity(SpecificationItemRangeSetting domain, YearMonthPeriod yearMonthPeriod, String cid, String specCd){
        QpbmtSpecItemRangeSet entity =  new QpbmtSpecItemRangeSet();
        entity.specItemRangeSetPk = new QpbmtSpecItemRangeSetPk(cid, specCd,domain.getHistId());
        entity.startYearMonth = yearMonthPeriod.start().v();
        entity.endYearMonth = yearMonthPeriod.end().v();
        entity.salaryItemId = domain.getSalaryItemId();
        entity.rangeValAttribute = domain.getRangeValAttribute().value;
        entity.errorUpperLimitSetAtr = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperLimitSettingAtr().value;
        entity.errorUpRangeValAmount = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueAmount().map(i->i.v()).orElse(null);
        entity.errorUpRangeValTime = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueTime().map(i->i.v()).orElse(null);
        entity.errorUpRangeValNum = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueNum().map(i->i.v()).orElse(null);
        entity.errorLowerLimitSetAtr = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerLimitSettingAtr().value;
        entity.errorLoRangeValAmount = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueAmount().map(i->i.v()).orElse(null);
        entity.errorLoRangeValTime = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueTime().map(i->i.v()).orElse(null);
        entity.errorLoRangeValNum = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueNum().map(i->i.v()).orElse(null);
        entity.alarmUpperLimitSetAtr = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperLimitSettingAtr().value;
        entity.alarmUpRangeValAmount = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueAmount().map(i->i.v()).orElse(null);
        entity.alarmUpRangeValTime = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueTime().map(i->i.v()).orElse(null);
        entity.alarmUpRangeValNum = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueNum().map(i->i.v()).orElse(null);
        entity.alarmLowerLimitSetAtr = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerLimitSettingAtr().value;
        entity.alarmLoRangeValAmount = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueAmount().map(i->i.v()).orElse(null);
        entity.alarmLoRangeValTime = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueTime().map(i->i.v()).orElse(null);
        entity.alarmLoRangeValNum = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueNum().map(i->i.v()).orElse(null);
        return entity;
    }

}
