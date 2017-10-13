/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ErrorPresentation;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionTime;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ObjectPeriod;

/**
 * @author danpv
 *
 */
@Getter
public class ExecutionLog {

	private long empCalAndSumExecLogId;

	public ErrorPresentation errorPresentation;

	private int caseExecutionContentId;

	private ExecutionStatus processStatus;

	private ExecutionContent executionContent;

	private ExecutionTime executionTime;

	private ObjectPeriod objectPeriod;

	private CalculationExecutionSettingInformation calExeSetInfor;

}
