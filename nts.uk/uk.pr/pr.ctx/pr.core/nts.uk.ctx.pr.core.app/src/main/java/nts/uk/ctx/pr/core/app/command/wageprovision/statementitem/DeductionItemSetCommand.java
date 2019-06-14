package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;

import java.math.BigDecimal;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetCommand {

    /**
     * 控除項目区分
     */
    private int deductionItemAtr;

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

    public DeductionItemSet toDomain(String cid, int categoryAtr, String itemNameCd) {
        return new DeductionItemSet(cid, categoryAtr, itemNameCd,
                this.deductionItemAtr, this.breakdownItemUseAtr,
                this.errorUpperLimitSetAtr, this.errorUpRangeVal, this.errorLowerLimitSetAtr, this.errorLoRangeVal,
                this.alarmUpperLimitSetAtr, this.alarmUpRangeVal, this.alarmLowerLimitSetAtr, this.alarmLoRangeVal,
                this.note);
    }
}
