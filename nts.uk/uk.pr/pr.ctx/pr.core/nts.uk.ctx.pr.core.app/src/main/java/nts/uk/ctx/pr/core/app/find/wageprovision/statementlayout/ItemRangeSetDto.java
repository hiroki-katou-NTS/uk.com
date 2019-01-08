package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
public class ItemRangeSetDto {
    private String histId;
    private String salaryItemId;
    private int rangeValAttribute;
    private int errorUpperLimitSetAtr;
    private Long errorUpRangeValAmount;
    private Integer errorUpRangeValTime;
    private BigDecimal errorUpRangeValNum;
    private int errorLowerLimitSetAtr;
    private Long errorLoRangeValAmount;
    private Integer errorLoRangeValTime;
    private BigDecimal errorLoRangeValNum;
    private int alarmUpperLimitSetAtr;
    private Long alarmUpRangeValAmount;
    private Integer alarmUpRangeValTime;
    private BigDecimal alarmUpRangeValNum;
    private int alarmLowerLimitSetAtr;
    private Long alarmLoRangeValAmount;
    private Integer alarmLoRangeValTime;
    private BigDecimal alarmLoRangeValNum;

    ItemRangeSetDto (StatementItemRangeSetting domain) {
        this.histId =domain.getHistId();
        this.salaryItemId = domain.getItemNameCd();
        this.rangeValAttribute = domain.getRangeValAttribute().value;
        this.errorUpperLimitSetAtr = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperLimitSettingAtr().value;
        this.errorUpRangeValAmount = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueAmount().map(i->i.v()).orElse(null);
        this.errorUpRangeValTime = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueTime().map(i->i.v()).orElse(null);
        this.errorUpRangeValNum = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperRangeValueNum().map(i->i.v()).orElse(null);
        this.errorLowerLimitSetAtr = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerLimitSettingAtr().value;
        this.errorLoRangeValAmount = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueAmount().map(i->i.v()).orElse(null);
        this.errorLoRangeValTime = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueTime().map(i->i.v()).orElse(null);
        this.errorLoRangeValNum = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerRangeValueNum().map(i->i.v()).orElse(null);
        this.alarmUpperLimitSetAtr = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperLimitSettingAtr().value;
        this.alarmUpRangeValAmount = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueAmount().map(i->i.v()).orElse(null);
        this.alarmUpRangeValTime = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueTime().map(i->i.v()).orElse(null);
        this.alarmUpRangeValNum = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperRangeValueNum().map(i->i.v()).orElse(null);
        this.alarmLowerLimitSetAtr = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerLimitSettingAtr().value;
        this.alarmLoRangeValAmount = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueAmount().map(i->i.v()).orElse(null);
        this.alarmLoRangeValTime = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueTime().map(i->i.v()).orElse(null);
        this.alarmLoRangeValNum = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerRangeValueNum().map(i->i.v()).orElse(null);
    }
}
