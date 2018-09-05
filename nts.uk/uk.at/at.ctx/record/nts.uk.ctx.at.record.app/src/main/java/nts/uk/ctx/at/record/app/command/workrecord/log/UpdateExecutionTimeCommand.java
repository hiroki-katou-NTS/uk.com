package nts.uk.ctx.at.record.app.command.workrecord.log;

import lombok.Data;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author nampt
 *
 */

@Data
public class UpdateExecutionTimeCommand {
	
	private String empCalAndSumExecLogID; 

	private GeneralDateTime executionStartDate;
	
	private GeneralDateTime executionEndDate;
}
