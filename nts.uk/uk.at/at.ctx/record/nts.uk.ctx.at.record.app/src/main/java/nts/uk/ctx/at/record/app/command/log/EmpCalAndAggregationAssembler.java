package nts.uk.ctx.at.record.app.command.log;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.shr.com.context.AppContexts;

public class EmpCalAndAggregationAssembler {
	public EmpCalAndSumExeLog fromDTO(EmpCalAndAggregationCommand command) {
        /** ログインしている社員の社員IDを取得する (Lấy login EmployeeID) */
		String employeeID = AppContexts.user().employeeId();
		/** 実行ボタン押下時のシステム日付を取得する (lấy thời gian hệ thống) */
		GeneralDate  systemTime = GeneralDate.today();
		String empCalAndSumExecLogID = IdentifierUtil.randomUniqueId();
		EmpCalAndSumExeLog empCalAndSumExeLog = EmpCalAndSumExeLog.createFromJavaType(
				/**empCalAndSumExecLogID*/
				IdentifierUtil.randomUniqueId(),
				/**companyID*/
				AppContexts.user().companyId(),
				/**processingMonth*/
				YearMonth.of(systemTime.year(), systemTime.month()),
				/**executedMenu */
				command.getExecutedMenu(),
				/**executionDate */
				systemTime,
				/**executionStatus */
				ExeStateOfCalAndSum.PROCESSING.value,
                /** employeeID */ 
				employeeID,
				/** closureID */
				command.getClosureID(),
				/**caseSpecExeContentID */
				command.getCaseSpecExeContentID(),
				/**executionLogs */
				buildExecutionLog(empCalAndSumExecLogID, command));
		return empCalAndSumExeLog;
	}
	
	private List<ExecutionLog> buildExecutionLog(String empCalAndSumExecLogID, EmpCalAndAggregationCommand command) {
		List<ExecutionLog> result = new ArrayList<ExecutionLog>();
		if (command.isDailyCreation()) {
			ExecutionLog executionLog = ExecutionLog.createFromJavaType(empCalAndSumExecLogID,
					ExecutionContent.DAILY_CREATION.value,
					ErrorPresent.NO_ERROR.value,
					null,
					null,
					ExeStateOfCalAndSum.PROCESSING.value,
					/**objectPeriod param Screen C */
					GeneralDate.fromString(command.getPeriodStartDate(),"YYYY/MM/DD") ,
					GeneralDate.fromString(command.getPeriodEndDate(),"YYYY/MM/DD"),
					/**calExecutionSetInfoID */
					IdentifierUtil.randomUniqueId());
			
		}
		return result;
	}
	
}
