package nts.uk.ctx.at.record.app.command.workrecord.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutedMenu;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddEmpCalSumAndTargetCommandAssembler {

	@Inject 
	CaseSpecExeContentRepository caseSpecExeContentRepository;
	
	public EmpCalAndSumExeLog fromDTO(AddEmpCalSumAndTargetCommand command) {
		// ログインしている社員の社員IDを取得する (Lấy login EmployeeID)
		String employeeID = AppContexts.user().employeeId();
		// 実行ボタン押下時のシステム日付を取得する (lấy thời gian hệ thống)
		GeneralDate systemTime = GeneralDate.today();
		int yearMonth = systemTime.yearMonth().v();
		String empCalAndSumExecLogID = IdentifierUtil.randomUniqueId();

		EmpCalAndSumExeLog empCalAndSumExeLog = EmpCalAndSumExeLog.createFromJavaType(
				// empCalAndSumExecLogID
				empCalAndSumExecLogID,
				// companyID
				AppContexts.user().companyId(),
				// processingMonth
				new YearMonth(yearMonth),
				// executedMenu
				ExecutedMenu.SELECT_AND_RUN.value,
				// executionDate
				systemTime,
				// executionStatus
				ExeStateOfCalAndSum.PROCESSING.value,
				// employeeID
				employeeID,
				// closureID
				command.getClosureID(),
				// caseSpecExeContentID
				command.getCaseSpecExeContentID(),
				// executionLogs
				new ArrayList<ExecutionLog>());
		if (command.getScreen().equals("B")) {
			empCalAndSumExeLog.setExecutionLogs(buildExecutionLog(empCalAndSumExecLogID, command));
		} else if (command.getScreen().equals("J")) {
			empCalAndSumExeLog.setExecutedMenu(ExecutedMenu.EXECUTION_BY_CASE);
			CaseSpecExeContent caseSpecExeContent = caseSpecExeContentRepository.getCaseSpecExeContentById(command.getCaseSpecExeContentID()).get();
			
			val optDailyCreationSetInfo = caseSpecExeContent.getDailyCreationSetInfo();
			val optDailyCalSetInfo = caseSpecExeContent.getDailyCalSetInfo(); 
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
		} else {
			throw new BusinessException("Not valid screen");
		}
		return empCalAndSumExeLog;
	}

	/** Build list ExecutionLog for Screen B */
	private List<ExecutionLog> buildExecutionLog(String empCalAndSumExecLogID, AddEmpCalSumAndTargetCommand command) {
		List<ExecutionLog> result = new ArrayList<ExecutionLog>();
		// Create DailyCreationSetInfo
		if (command.isDailyCreation()) {
			ExecutionType executionType = EnumAdaptor.valueOf(command.getCreationType(), ExecutionType.class);
			DailyRecreateClassification creationType = EnumAdaptor.valueOf(command.getResetClass(), DailyRecreateClassification.class);
			// Create PartResetClassification
			Optional<PartResetClassification> getPartResetClassification = Optional.empty();
			if (creationType == DailyRecreateClassification.PARTLY_MODIFIED) {
				getPartResetClassification = Optional.of(new PartResetClassification(
						// masterReconfiguration
						command.isMasterReconfiguration(),
						// closedHolidays
						command.isClosedHolidays(),
						// resettingWorkingHours
						command.isResettingWorkingHours(),
						// reflectsTheNumberOfFingerprintChecks
						command.isRefNumberFingerCheck(),
						// specificDateClassificationResetting
						command.isSpecDateClassReset(),
						// resetTimeAssignment
						command.isResetTimeForAssig(),
						// resetTimeChildOrNurseCare
						command.isResetTimeForChildOrNurseCare(),
						// calculationClassificationResetting
						command.isCalClassReset()));
			}
			
			SettingInforForDailyCreation dailyCreationSetInfo = new SettingInforForDailyCreation(
					// executionContent
					ExecutionContent.DAILY_CREATION,
					// executionType
					executionType, 
					// calExecutionSetInfoID
					IdentifierUtil.randomUniqueId(), 
					// creationType
					creationType, 
					// partResetClassification
					getPartResetClassification);
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CREATION, command);
			executionLog.setDailyCreationSetInfo(dailyCreationSetInfo);
			result.add(executionLog);
		}
		// Create DailyCalSetInfo
		if (command.isDailyCalClass()) {
			CalExeSettingInfor dailyCalSetInfo = new CalExeSettingInfor(
					ExecutionContent.DAILY_CALCULATION,
					EnumAdaptor.valueOf(command.getCalClass(), ExecutionType.class), 
				IdentifierUtil.randomUniqueId());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.DAILY_CALCULATION, command);
			executionLog.setDailyCalSetInfo(dailyCalSetInfo);
			result.add(executionLog);
		}
		// Create ReflectApprovalSetInfo
		if (command.isRefApprovalresult()) {
			SetInforReflAprResult reflectApprovalSetInfo = new SetInforReflAprResult(
					ExecutionContent.REFLRCT_APPROVAL_RESULT,
					EnumAdaptor.valueOf(command.getRefClass(), ExecutionType.class), 
					IdentifierUtil.randomUniqueId(),
					command.isAlsoForciblyReflectEvenIfItIsConfirmed());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.REFLRCT_APPROVAL_RESULT, command);
			executionLog.setReflectApprovalSetInfo(reflectApprovalSetInfo);
			result.add(executionLog);
		}
		// Create MonlyAggregationSetInfo
		if (command.isMonthlyAggregation()) {
			CalExeSettingInfor monlyAggregationSetInfo = new CalExeSettingInfor(
					ExecutionContent.MONTHLY_AGGREGATION,
					EnumAdaptor.valueOf(command.getSummaryClass(), ExecutionType.class), 
					IdentifierUtil.randomUniqueId());
			val executionLog = createExecutionLog(empCalAndSumExecLogID, ExecutionContent.MONTHLY_AGGREGATION, command);
			executionLog.setMonlyAggregationSetInfo(monlyAggregationSetInfo);
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
				GeneralDate.fromString(command.getPeriodEndDate(), "yyyy/MM/dd"));
		return executionLog;
	}
	
}
