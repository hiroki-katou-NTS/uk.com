package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpCounterLaborCostAndTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWkpLaborCostAndTimeCommand {

    private List<LaborCostAndTime> laborCostAndTimes;

}
