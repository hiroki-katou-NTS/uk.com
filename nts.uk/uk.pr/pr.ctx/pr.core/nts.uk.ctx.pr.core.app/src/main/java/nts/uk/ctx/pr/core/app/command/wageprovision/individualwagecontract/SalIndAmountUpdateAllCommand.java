package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.Value;

import java.util.List;

@Value
public class SalIndAmountUpdateAllCommand {

    List<SalIndAmountUpdateCommand> salIndAmountUpdateCommandList;

}

@Value
class SalIndAmountUpdateCommand{
    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 金額
     */
    private long amountOfMoney;
}



