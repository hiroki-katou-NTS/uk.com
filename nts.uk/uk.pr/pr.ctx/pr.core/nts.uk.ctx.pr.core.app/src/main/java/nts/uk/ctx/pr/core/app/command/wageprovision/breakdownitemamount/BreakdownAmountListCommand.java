package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import lombok.Value;

@Value
public class BreakdownAmountListCommand {

    /**
     * 内訳項目コード
     */
    private String breakdownItemCode;

    /**
     * 金額
     */
    private Long amount;
}
