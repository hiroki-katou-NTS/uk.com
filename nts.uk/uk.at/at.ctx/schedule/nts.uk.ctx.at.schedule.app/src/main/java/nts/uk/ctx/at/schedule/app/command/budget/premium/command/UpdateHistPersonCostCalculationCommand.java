package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;


@Getter
@AllArgsConstructor
public class UpdateHistPersonCostCalculationCommand {
    public GeneralDate startDate;
    public GeneralDate endDate;
    public String historyId;
}
