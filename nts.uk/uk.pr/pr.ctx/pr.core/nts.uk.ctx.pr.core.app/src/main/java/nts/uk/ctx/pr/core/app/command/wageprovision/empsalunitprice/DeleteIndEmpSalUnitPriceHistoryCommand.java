package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Value;

@Value
public class DeleteIndEmpSalUnitPriceHistoryCommand {

    String historyId;

    String lastHistoryId;

}
