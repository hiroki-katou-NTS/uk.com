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
	
	private String dailyCreateStartTime;
	
	private String dailyCreateEndTime;
	
	private String dailyCalculateStartTime;
	
	private String dailyCalculateEndTime;
	
	private String reflectApprovalStartTime;
	
	private String reflectApprovalEndTime;
	
	private String monthlyAggregateStartTime;
	
	private String monthlyAggregateEndTime;
	
	private int stopped;
}
