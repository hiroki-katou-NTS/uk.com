package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ExecuteAggrPeriodCommand {
	public String excuteId;
	
	public String employeeId;
	
	private GeneralDate startDate;
	
	private GeneralDate endDate;
}
