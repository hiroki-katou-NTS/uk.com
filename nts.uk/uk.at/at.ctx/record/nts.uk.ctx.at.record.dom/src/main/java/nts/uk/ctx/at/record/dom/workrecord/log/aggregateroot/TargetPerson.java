/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.CompletionStateOfExecutionContents;

/**
 * @author danpv
 *
 */
@Getter
public class TargetPerson {

	private long empCalAndSumExecLogId;

	private String employeeId;
	
	private CompletionStateOfExecutionContents state;
	
}
