package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AddIndividualwagecontractCommand {

    SalIndAmountHisCommand salIndAmountHisCommand;

    SalIndAmountCommand salIndAmountCommand;

    String oldHistoryId;

    int newEndMonthOfOldHistory;

}
