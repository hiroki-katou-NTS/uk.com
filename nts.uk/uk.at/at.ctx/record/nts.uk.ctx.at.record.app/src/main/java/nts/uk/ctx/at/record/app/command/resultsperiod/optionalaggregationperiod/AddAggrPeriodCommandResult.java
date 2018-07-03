package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class AddAggrPeriodCommandResult {
	
	private String anyPeriodAggrLogId;
	
	private GeneralDateTime startDateTime;
	
	private GeneralDateTime endDateTime;
	

}
