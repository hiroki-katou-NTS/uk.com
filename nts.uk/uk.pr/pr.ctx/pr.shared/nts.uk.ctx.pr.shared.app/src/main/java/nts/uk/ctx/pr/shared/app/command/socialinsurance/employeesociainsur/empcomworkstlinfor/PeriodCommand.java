package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomworkstlinfor;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class PeriodCommand {
    private String empId;

    private GeneralDate startDate;

    private GeneralDate endDate;
}
