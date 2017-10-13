/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseExecutionContentId;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutedMenu;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionStatusOfCalAndSum;

/**
 * @author danpv
 *
 */
@Getter
public class EmploymentCalAndSumExecutionLog extends AggregateRoot {

	private long Id;

	private String companyId;

	private String employeeId;
	
	private int closureId;
	
	private GeneralDate executionDate;
	
	private YearMonth processingMonth;

	private ExecutedMenu executedMenu;

	private List<ExecutionLog> executionLogs;

	private ExecutionStatusOfCalAndSum executionStatus;

	private CaseExecutionContentId operationCase;

}
