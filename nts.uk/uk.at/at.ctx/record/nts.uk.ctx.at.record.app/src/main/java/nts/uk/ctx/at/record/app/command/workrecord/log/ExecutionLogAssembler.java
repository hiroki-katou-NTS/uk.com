package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;

@Stateless
public class ExecutionLogAssembler {
	
	public List<ExecutionLog> fromDTO(AddEmpCalSumAndTargetCommand command, String empCalAndSumExecLogID) {
		
		List<ExecutionLog> result = Collections.emptyList();
		
		if (command.getScreen().equals("B")) {
			result = buildExecutionLog(empCalAndSumExecLogID, command);
		} else {
			throw new BusinessException("Not valid screen");
		}
		
		return result;
	}
	
	/** Build list ExecutionLog for Screen B */
	private List<ExecutionLog> buildExecutionLog(String empCalAndSumExecLogID, AddEmpCalSumAndTargetCommand command) {
		List<ExecutionLog> result = new ArrayList<ExecutionLog>();
		// Create DailyCreationSetInfo
		if (command.isDailyCreation()) {
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CREATION, command);
			result.add(executionLog);
		}
		// Create DailyCalSetInfo
		if (command.isDailyCalClass()) {
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CALCULATION, command);
			result.add(executionLog);
		}
		// Create ReflectApprovalSetInfo
		if (command.isRefApprovalresult()) {
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.REFLRCT_APPROVAL_RESULT, command);
			result.add(executionLog);
		}
		// Create MonlyAggregationSetInfo
		if (command.isMonthlyAggregation()) {
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.MONTHLY_AGGREGATION, command);
			result.add(executionLog);
		}
		return result;
	}

	/** Build each ExecutionLog */
	private ExecutionLog createExecutionLog(String empCalAndSumExecLogID, ExecutionContent executionContent, AddEmpCalSumAndTargetCommand command) {
		ExecutionLog executionLog = ExecutionLog.createFromJavaType(
				empCalAndSumExecLogID,
				executionContent.value,
				ErrorPresent.NO_ERROR.value,
				GeneralDateTime.now(), GeneralDateTime.now(),
				ExeStateOfCalAndSum.PROCESSING.value,
				// objectPeriod param Screen C
				GeneralDate.fromString(command.getPeriodStartDate(), "yyyy/MM/dd"),
				GeneralDate.fromString(command.getPeriodEndDate(), "yyyy/MM/dd"),
				Optional.ofNullable(command.getIsCalWhenLock() == null ? null : command.getIsCalWhenLock() ==1 ),
				command.getCreationType());
		return executionLog;
	}
	
}
