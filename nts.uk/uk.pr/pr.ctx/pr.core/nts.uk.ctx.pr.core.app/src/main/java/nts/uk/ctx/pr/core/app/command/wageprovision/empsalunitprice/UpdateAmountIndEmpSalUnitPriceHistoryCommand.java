package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class UpdateAmountIndEmpSalUnitPriceHistoryCommand {

    String historyId;

    BigDecimal amountOfMoney;

}
