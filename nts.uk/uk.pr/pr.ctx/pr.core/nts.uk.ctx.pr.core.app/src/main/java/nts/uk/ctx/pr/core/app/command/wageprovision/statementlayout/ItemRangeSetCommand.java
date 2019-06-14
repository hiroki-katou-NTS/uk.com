package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
public class ItemRangeSetCommand {
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

    public StatementItemRangeSetting toDomain(String cid, String statementCode, int categoryAtr) {
        return new StatementItemRangeSetting(this.histId, cid, statementCode, categoryAtr, this.salaryItemId, this.rangeValAttribute, this.errorUpperLimitSetAtr,
                this.errorUpRangeValAmount, this.errorUpRangeValTime, this.errorUpRangeValNum, this.errorLowerLimitSetAtr, this.errorLoRangeValAmount,
                this.errorLoRangeValTime, this.errorLoRangeValNum, this.alarmUpperLimitSetAtr, this.alarmUpRangeValAmount, this.alarmUpRangeValTime,
                this.alarmUpRangeValNum, this.alarmLowerLimitSetAtr, this.alarmLoRangeValAmount, this.alarmLoRangeValTime, this.alarmLoRangeValNum);
    }
}
