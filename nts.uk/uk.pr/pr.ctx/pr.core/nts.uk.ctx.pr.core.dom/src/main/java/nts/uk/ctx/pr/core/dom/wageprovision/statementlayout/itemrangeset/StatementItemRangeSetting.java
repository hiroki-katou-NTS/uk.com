package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementCode;

import java.math.BigDecimal;

/**
* 明細書項目範囲設定
*/
@Getter
@Setter
public class StatementItemRangeSetting extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 明細書コード
     */
    private StatementCode statementCode;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 項目名コード
     */
    private String itemNameCd;
    
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
    
    public StatementItemRangeSetting(String histId, String cid, String statementCode, int categoryAtr, String itemNameCd, int rangeValueAtr, int errorUpperLimitSetAtr,
                                     Long errorUpRangeValAmount, Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum,
                                     int errorLowerLimitSetAtr, Long errorLoRangeValAmount, Integer errorLoRangeValTime,
                                     BigDecimal errorLoRangeValNum, int alarmUpperLimitSetAtr, Long alarmUpRangeValAmount,
                                     Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum, int alarmLowerLimitSetAtr,
                                     Long alarmLoRangeValAmount, Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
        this.histId = histId;
        this.cid = cid;
        this.statementCode = new StatementCode(statementCode);
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCd = itemNameCd;
        this.rangeValAttribute = EnumAdaptor.valueOf(rangeValueAtr, RangeValueEnum.class);
        this.errorRangeSet = new ErrorRangeSetting(errorUpperLimitSetAtr, errorUpRangeValAmount,
                errorUpRangeValTime, errorUpRangeValNum, errorLowerLimitSetAtr, errorLoRangeValAmount,
                errorLoRangeValTime, errorLoRangeValNum);
        this.alarmRangeSet = new AlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeValAmount,
                alarmUpRangeValTime, alarmUpRangeValNum, alarmLowerLimitSetAtr, alarmLoRangeValAmount,
                alarmLoRangeValTime, alarmLoRangeValNum);
    }
}
