package nts.uk.ctx.at.record.app.command.workrecord.log;

import lombok.Data;

/**
 * 
 * @author nampt
 *
 */

@Data
public class UpdateExecutionTimeCommand {
	
	private String empCalAndSumExecLogID; 

	private String executionStartDate;
	
	private String executionEndDate;
}
