package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class PayrollInformationCommands {
    private List<PayrollInformationCommand> payrollInformationCommands;
}


