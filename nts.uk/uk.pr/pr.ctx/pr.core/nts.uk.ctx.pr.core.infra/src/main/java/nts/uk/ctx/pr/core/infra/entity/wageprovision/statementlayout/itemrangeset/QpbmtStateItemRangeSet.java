package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.itemrangeset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;
import nts.uk.shr.com.context.AppContexts;
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
@Table(name = "QPBMT_STT_ITEM_RANGE_SET")
public class QpbmtStateItemRangeSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtStateItemRangeSetPk stateItemRangeSetPk;
    
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
    @Basic(optional = false)
    @Column(name = "ERROR_UP_RANGE_VAL_AMOUNT")
    public Long errorUpRangeValAmount;
    
    /**
    * エラー上限値時間
    */
    @Basic(optional = false)
    @Column(name = "ERROR_UP_RANGE_VAL_TIME")
    public Integer errorUpRangeValTime;
    
    /**
    * エラー上限値回数
    */
    @Basic(optional = false)
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
    @Basic(optional = false)
    @Column(name = "ERROR_LO_RANGE_VAL_AMOUNT")
    public Long errorLoRangeValAmount;
    
    /**
    * エラー上限値時間
    */
    @Basic(optional = false)
    @Column(name = "ERROR_LO_RANGE_VAL_TIME")
    public Integer errorLoRangeValTime;
    
    /**
    * エラー上限値回数
    */
    @Basic(optional = false)
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
    @Basic(optional = false)
    @Column(name = "ALARM_UP_RANGE_VAL_AMOUNT")
    public Long alarmUpRangeValAmount;
    
    /**
    * アラーム上限値時間
    */
    @Basic(optional = false)
    @Column(name = "ALARM_UP_RANGE_VAL_TIME")
    public Integer alarmUpRangeValTime;
    
    /**
    * アラーム上限値回数
    */
    @Basic(optional = false)
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
    @Basic(optional = false)
    @Column(name = "ALARM_LO_RANGE_VAL_AMOUNT")
    public Long alarmLoRangeValAmount;
    
    /**
    * アラーム上限値時間
    */
    @Basic(optional = false)
    @Column(name = "ALARM_LO_RANGE_VAL_TIME")
    public Integer alarmLoRangeValTime;
    
    /**
    * アラーム上限値回数
    */
    @Basic(optional = false)
    @Column(name = "ALARM_LO_RANGE_VAL_NUM")
    public BigDecimal alarmLoRangeValNum;
    
    @Override
    protected Object getKey()
    {
        return stateItemRangeSetPk;
    }

    public StatementItemRangeSetting toDomain() {
        return new StatementItemRangeSetting(this.stateItemRangeSetPk.histId, this.stateItemRangeSetPk.cid, this.stateItemRangeSetPk.statementCd, this.stateItemRangeSetPk.categoryAtr, this.stateItemRangeSetPk.itemNameCd, this.rangeValAttribute, this.errorUpperLimitSetAtr,
                this.errorUpRangeValAmount, this.errorUpRangeValTime, this.errorUpRangeValNum, this.errorLowerLimitSetAtr, this.errorLoRangeValAmount,
                this.errorLoRangeValTime, this.errorLoRangeValNum, this.alarmUpperLimitSetAtr, this.alarmUpRangeValAmount, this.alarmUpRangeValTime,
                this.alarmUpRangeValNum, this.alarmLowerLimitSetAtr, this.alarmLoRangeValAmount, this.alarmLoRangeValTime, this.alarmLoRangeValNum);
    }

    public static QpbmtStateItemRangeSet toEntity(StatementItemRangeSetting domain){
        String cid = AppContexts.user().companyId();

        QpbmtStateItemRangeSet entity =  new QpbmtStateItemRangeSet();
        entity.stateItemRangeSetPk = new QpbmtStateItemRangeSetPk(cid, domain.getStatementCode().v(), domain.getHistId(), domain.getCategoryAtr().value, domain.getItemNameCd());
        entity.rangeValAttribute = domain.getRangeValAttribute().value;
        entity.errorUpperLimitSetAtr = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperLimitSettingAtr().value;
        entity.errorUpRangeValAmount = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueAmount().map(i->i.v()).orElse(0L);
        entity.errorUpRangeValTime = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueTime().map(i->i.v()).orElse(0);
        entity.errorUpRangeValNum = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueNum().map(i->i.v()).orElse(BigDecimal.ZERO);
        entity.errorLowerLimitSetAtr = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerLimitSettingAtr().value;
        entity.errorLoRangeValAmount = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueAmount().map(i->i.v()).orElse(0L);
        entity.errorLoRangeValTime = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueTime().map(i->i.v()).orElse(0);
        entity.errorLoRangeValNum = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueNum().map(i->i.v()).orElse(BigDecimal.ZERO);
        entity.alarmUpperLimitSetAtr = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperLimitSettingAtr().value;
        entity.alarmUpRangeValAmount = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueAmount().map(i->i.v()).orElse(0L);
        entity.alarmUpRangeValTime = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueTime().map(i->i.v()).orElse(0);
        entity.alarmUpRangeValNum = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueNum().map(i->i.v()).orElse(BigDecimal.ZERO);
        entity.alarmLowerLimitSetAtr = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerLimitSettingAtr().value;
        entity.alarmLoRangeValAmount = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueAmount().map(i->i.v()).orElse(0L);
        entity.alarmLoRangeValTime = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueTime().map(i->i.v()).orElse(0);
        entity.alarmLoRangeValNum = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueNum().map(i->i.v()).orElse(BigDecimal.ZERO);
        return entity;
    }

}
