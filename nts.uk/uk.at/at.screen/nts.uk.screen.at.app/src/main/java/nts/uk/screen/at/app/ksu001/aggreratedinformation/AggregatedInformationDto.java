package nts.uk.screen.at.app.ksu001.aggreratedinformation;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.AggregateScheduleDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggregatedInformationDto {

	public Map<GeneralDate, Map<ExternalBudgetDto, String>> externalBudget = new HashMap<>();
	
	public AggregateScheduleDto aggrerateSchedule;
	
	
}
