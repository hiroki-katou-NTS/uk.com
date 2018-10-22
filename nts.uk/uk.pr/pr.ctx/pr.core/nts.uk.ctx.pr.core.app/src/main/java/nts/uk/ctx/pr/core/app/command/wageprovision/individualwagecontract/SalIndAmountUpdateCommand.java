package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
public class SalIndAmountUpdateCommand{
    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 金額
     */
    private String amountOfMoney;

}
