package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;

import java.math.BigDecimal;

/**
 * @author thanh.tq 勤怠項目設定
 */
@Value
public class TimeItemSetCommand {

    /**
     * 時間回数区分
     */
    public int timeCountAtr;

    /**
     * 所定労働日数区分
     */
    public Integer workingDaysPerYear;

    /**
     * 平均賃金区分
     */
    public Integer averageWageAtr;

    /**
     * 値設定区分
     */
    public int errorUpperLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    public Integer errorUpRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    public BigDecimal errorUpRangeValNum;

    /**
     * 値設定区分
     */
    public int errorLowerLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    public Integer errorLoRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    public BigDecimal errorLoRangeValNum;

    /**
     * 値設定区分
     */
    public int alarmUpperLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    public Integer alarmUpRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    public BigDecimal alarmUpRangeValNum;

    /**
     * 値設定区分
     */
    public int alarmLowerLimitSetAtr;

    /**
     * 明細範囲値（時間）
     */
    public Integer alarmLoRangeValTime;

    /**
     * 明細範囲値（回数）
     */
    public BigDecimal alarmLoRangeValNum;

    /**
     * 備考
     */
    public String note;

    public TimeItemSet toDomain(String cid, int categoryAtr, String itemNameCd) {
        return new TimeItemSet(cid, categoryAtr, itemNameCd,
                this.timeCountAtr, this.workingDaysPerYear, this.averageWageAtr,
                this.errorUpperLimitSetAtr, this.errorUpRangeValTime, this.errorUpRangeValNum,
                this.errorLowerLimitSetAtr, this.errorLoRangeValTime, this.errorLoRangeValNum,
                this.alarmUpperLimitSetAtr, this.alarmUpRangeValTime, this.alarmUpRangeValNum,
                this.alarmLowerLimitSetAtr, this.alarmLoRangeValTime, this.alarmLoRangeValNum,
                this.note);
    }
}
