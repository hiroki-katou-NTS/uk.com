package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddAggrPeriodCommand{

	private boolean mode;
	
	private SaveOptionalAggrPeriodCommand aggrPeriodCommand;
	
	private TargetCommand targetCommand;
	
	private ExecutionCommand executionCommand;


}
