package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Value
class PayrollInformationCommand{
   private String historyID;

   private BigDecimal amountOfMoney;
}