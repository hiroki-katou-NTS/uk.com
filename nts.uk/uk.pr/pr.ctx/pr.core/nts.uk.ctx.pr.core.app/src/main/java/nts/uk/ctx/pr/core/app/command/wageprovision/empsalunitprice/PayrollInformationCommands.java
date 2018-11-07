package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PayrollInformationCommands {
    List<PayrollInformationCommand> payrollInformationCommands;
}

@Data
class PayrollInformationCommand{
    String historyID;

    BigDecimal individualUnitPrice;
}
