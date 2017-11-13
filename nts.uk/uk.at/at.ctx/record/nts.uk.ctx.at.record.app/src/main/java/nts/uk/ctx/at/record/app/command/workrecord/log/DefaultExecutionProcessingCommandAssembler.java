package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.log.CalExeSettingInfor;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseSpecExeContent;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseSpecExeContentRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.PartResetClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.SetInforReflAprResult;
import nts.uk.ctx.at.record.dom.workrecord.log.SettingInforForDailyCreation;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.DailyRecreateClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DefaultExecutionProcessingCommandAssembler {

	@Inject 
	CaseSpecExeContentRepository caseSpecExeContentRepository;
	
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
		} else {
			CaseSpecExeContent caseSpecExeContent = caseSpecExeContentRepository.getCaseSpecExeContentById(command.getCaseSpecExeContentID()).get();
			
			val optDailyCreationSetInfo = caseSpecExeContent.getDailyCreationSetInfo();
			val optDailyCalSetInfo= caseSpecExeContent.getDailyCalSetInfo(); 
			val optReflectApprovalSetInfo = caseSpecExeContent.getReflectApprovalSetInfo();
			val optMonlyAggregationSetInfo = caseSpecExeContent.getMonlyAggregationSetInfo(); 
			if (optDailyCreationSetInfo.isPresent()) {
				val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CREATION, command);
				executionLog.setDailyCreationSetInfo(optDailyCreationSetInfo.get());
				empCalAndSumExeLog.addExecutionLog(executionLog);
			}
			if(optDailyCalSetInfo.isPresent()){
				val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CALCULATION, command);
				executionLog.setDailyCalSetInfo(optDailyCalSetInfo.get());
				empCalAndSumExeLog.addExecutionLog(executionLog);
			}
			if(optReflectApprovalSetInfo.isPresent()){
				val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.REFLRCT_APPROVAL_RESULT, command);
				executionLog.setReflectApprovalSetInfo(optReflectApprovalSetInfo.get());
				empCalAndSumExeLog.addExecutionLog(executionLog);
			}
			if(optMonlyAggregationSetInfo.isPresent()){
				val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.MONTHLY_AGGREGATION, command);
				executionLog.setMonlyAggregationSetInfo(optMonlyAggregationSetInfo.get());
				empCalAndSumExeLog.addExecutionLog(executionLog);
			}
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
					/**creationType*/
					EnumAdaptor.valueOf(command.getRefClass(),DailyRecreateClassification.class), 
					/**partResetClassification*/
					getPartResetClassification);
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CREATION, command);
			executionLog.setDailyCreationSetInfo(dailyCreationSetInfo);
			result.add(executionLog);
		}
		if (command.isCalClassReset()) {
			CalExeSettingInfor dailyCalSetInfo = new CalExeSettingInfor(
				command.getExcutionContent(),
				EnumAdaptor.valueOf(command.getCalClass(),ExecutionType.class),
				IdentifierUtil.randomUniqueId());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CALCULATION, command);
			executionLog.setDailyCalSetInfo(dailyCalSetInfo);
			result.add(executionLog);
		}
		if (command.isRefApprovalresult()) {
			SetInforReflAprResult reflectApprovalSetInfo = new SetInforReflAprResult(
					command.getExcutionContent(),
					EnumAdaptor.valueOf(command.getCalClass(),ExecutionType.class),
					IdentifierUtil.randomUniqueId(),
					command.isAlsoForciblyReflectEvenIfItIsConfirmed());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.REFLRCT_APPROVAL_RESULT, command);
			executionLog.setReflectApprovalSetInfo(reflectApprovalSetInfo);
			result.add(executionLog);
		}
		if (command.isMonthlyAggregation()) {
			CalExeSettingInfor monlyAggregationSetInfo = new CalExeSettingInfor(
					command.getExcutionContent(),
					EnumAdaptor.valueOf(command.getCalClass(),ExecutionType.class),
					IdentifierUtil.randomUniqueId());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.MONTHLY_AGGREGATION, command);
			executionLog.setMonlyAggregationSetInfo(monlyAggregationSetInfo);
			result.add(executionLog);
		}
		return result;
	}

	private ExecutionLog createExecutionLog(String empCalAndSumExecLogID, ExecutionContent executionContent, ExecutionProcessingCommand command) {
		ExecutionLog executionLog = ExecutionLog.createFromJavaType(
				empCalAndSumExecLogID,
				executionContent.value,
				ErrorPresent.NO_ERROR.value,
				null, null,
				ExeStateOfCalAndSum.PROCESSING.value,
				/** objectPeriod param Screen C */
				GeneralDate.fromString(command.getPeriodStartDate(), "yyyy/MM/dd"),
				GeneralDate.fromString(command.getPeriodEndDate(), "yyyy/MM/dd"));
		return executionLog;
	}
	
}
