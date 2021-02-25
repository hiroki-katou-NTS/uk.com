package nts.uk.ctx.at.schedule.app.command.budget.premium.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InsertHistPersonCostCalculationCommand {
    private GeneralDate date;
}
