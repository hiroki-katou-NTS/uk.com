/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.EmployeeExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;

/**
 * @author danpv
 *
 */
@Getter
public class ComplStateOfExeContents {

	private ExecutionContent executionContent;

	private EmployeeExecutionStatus status;

}
