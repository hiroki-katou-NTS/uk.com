package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.PartResetClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.SettingInforForDailyCreation;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.DailyRecreateClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.context.AppContexts;

public class ExecutionProcessingCommandAssembler {

	public EmpCalAndSumExeLog fromDTO(ExecutionProcessingCommand command) {
		/** ログインしている社員の社員IDを取得する (Lấy login EmployeeID) */
		String employeeID = AppContexts.user().employeeId();
		/** 実行ボタン押下時のシステム日付を取得する (lấy thời gian hệ thống) */
		GeneralDate systemTime = GeneralDate.today();
		int yearMonth = systemTime.yearMonth().v();
		String empCalAndSumExecLogID = IdentifierUtil.randomUniqueId();

		EmpCalAndSumExeLog empCalAndSumExeLog = EmpCalAndSumExeLog.createFromJavaType(
				/** empCalAndSumExecLogID */
				empCalAndSumExecLogID,
				/** companyID */
				AppContexts.user().companyId(),
				/** processingMonth */
				new YearMonth(yearMonth),
				/** executedMenu */
				command.getExecutedMenu(),
				/** executionDate */
				systemTime,
				/** executionStatus */
				ExeStateOfCalAndSum.PROCESSING.value,
				/** employeeID */
				employeeID,
				/** closureID */
				command.getClosureID(),
				/** caseSpecExeContentID */
				command.getCaseSpecExeContentID(),
				/** executionLogs */
				new ArrayList<ExecutionLog>());
		if (command.getExcutionContent().equals("B")) {
			empCalAndSumExeLog.setExecutionLogs(buildExecutionLog(empCalAndSumExecLogID, command));
		}
		return empCalAndSumExeLog;
	}

	private List<ExecutionLog> buildExecutionLog(String empCalAndSumExecLogID, ExecutionProcessingCommand command) {
		List<ExecutionLog> result = new ArrayList<ExecutionLog>();
		if (command.isDailyCreation()) {
			
			PartResetClassification getPartResetClassification = new PartResetClassification(
					/** masterReconfiguration */
					command.isMasterReconfiguration(),
					/** closedHolidays */
					command.isClosedHolidays(),
					/** resettingWorkingHours*/
					command.isResettingWorkingHours(),
					/** reflectsTheNumberOfFingerprintChecks*/
					command.isRefNumberFingerCheck(),
					/**specificDateClassificationResetting */
					command.isSpecDateClassReset(),
					/**resetTimeAssignment*/
					command.isResetTimeForAssig(),
					/**resetTimeChildOrNurseCare*/
					command.isResetTimeForChildOrNurseCare(),
					/**calculationClassificationResetting*/
					command.isCalClassReset());
			
			SettingInforForDailyCreation dailyCreationSetInfo = new SettingInforForDailyCreation(
					/**executionContent*/
					command.getExcutionContent(), 
					/**executionType*/
					EnumAdaptor.valueOf(command.getCalClass(),ExecutionType.class), 
					/**calExecutionSetInfoID*/
					IdentifierUtil.randomUniqueId(), 
					/**caseSpecExeContentID*/
					command.getCaseSpecExeContentID(), 
					/**creationType*/
					EnumAdaptor.valueOf(command.getRefClass(),DailyRecreateClassification.class), 
					/**partResetClassification*/
					getPartResetClassification);
			
			ExecutionLog executionLog = ExecutionLog.createFromJavaType(
					empCalAndSumExecLogID,
					ExecutionContent.DAILY_CREATION.value,
					ErrorPresent.NO_ERROR.value,
					null,
					null,
					ExeStateOfCalAndSum.PROCESSING.value,
					/** objectPeriod param Screen C */
					GeneralDate.fromString(command.getPeriodStartDate(), "yyyy/MM/dd"),
					GeneralDate.fromString(command.getPeriodEndDate(), "yyyy/MM/dd"));
			executionLog.setDailyCreationSetInfo(dailyCreationSetInfo);
			result.add(executionLog);
		}
		return result;
	}

}
