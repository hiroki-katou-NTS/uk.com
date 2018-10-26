package nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.RangeValueEnum;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.*;

import java.math.BigDecimal;

/**
* 明細書項目範囲設定
*/
@Getter
public class SpecificationItemRangeSetting extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;
    
    /**
    * 給与項目ID
    */
    private String salaryItemId;
    
    /**
    * 範囲値の属性
    */
    private RangeValueEnum rangeValAttribute;
    
    /**
    * エラー範囲設定
    */
    private ErrorRangeSetting errorRangeSet;
    
    /**
    * アラーム範囲設定
    */
    private AlarmRangeSetting alarmRangeSet;
    
    public SpecificationItemRangeSetting(String histId, String salaryItemId, int rangeValueAtr, int errorUpperLimitSetAtr,
                        Long errorUpRangeValAmount, Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum,
                        int errorLowerLimitSetAtr, Long errorLoRangeValAmount, Integer errorLoRangeValTime,
                        BigDecimal errorLoRangeValNum, int alarmUpperLimitSetAtr, Long alarmUpRangeValAmount,
                        Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum, int alarmLowerLimitSetAtr,
                        Long alarmLoRangeValAmount, Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
        this.histId = histId;
        this.salaryItemId = salaryItemId;
        this.rangeValAttribute = EnumAdaptor.valueOf(rangeValueAtr, RangeValueEnum.class);
        this.errorRangeSet = new ErrorRangeSetting(errorUpperLimitSetAtr, errorUpRangeValAmount,
                errorUpRangeValTime, errorUpRangeValNum, errorLowerLimitSetAtr, errorLoRangeValAmount,
                errorLoRangeValTime, errorLoRangeValNum);
        this.alarmRangeSet = new AlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeValAmount,
                alarmUpRangeValTime, alarmUpRangeValNum, alarmLowerLimitSetAtr, alarmLoRangeValAmount,
                alarmLoRangeValTime, alarmLoRangeValNum);
    }
}
