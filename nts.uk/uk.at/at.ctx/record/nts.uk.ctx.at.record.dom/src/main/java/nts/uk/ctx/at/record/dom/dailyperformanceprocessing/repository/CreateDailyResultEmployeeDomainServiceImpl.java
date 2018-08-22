package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.EmployeeAndClosureOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.context.ContextSupport;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.DailyRecreateClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMonthDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CreateDailyResultEmployeeDomainServiceImpl implements CreateDailyResultEmployeeDomainService {

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ActualLockRepository actualLockRepository;

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private ReflectWorkInforDomainService reflectWorkInforDomainService;

	@Inject
	private ResetDailyPerforDomainService resetDailyPerforDomainService;
	
	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	// =============== HACK ON (this) ================= //
	/* The sc context. */
	@Resource
	private SessionContext scContext;

	private CreateDailyResultEmployeeDomainService self;

	/**
	 * Inits.
	 */
	@PostConstruct
	public void init() {
		// Get self.
		this.self = scContext.getBusinessObject(CreateDailyResultEmployeeDomainService.class);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ProcessState createDailyResultEmployee(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTime, String companyId, String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog,
			boolean reCreateWorkType, EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem,
			PeriodInMasterList periodInMasterList) {

		// 正常終了 : 0
		// 中断 : 1
		ProcessState status = ProcessState.SUCCESS;

		GeneralDate processingDate = periodTime.start();

		// lits day between startDate and endDate
		List<GeneralDate> listDayBetween = this.getDaysBetween(periodTime.start(), periodTime.end());

		// Imported（就業）「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId,
				employeeId, processingDate);
		if (!employmentHisOptional.isPresent()) {
			// #日別作成修正　2018/07/17　前川　隼大　
			// 社員の日別実績のエラーを作成する
			EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId,
					employeeId, processingDate, new ErrorAlarmWorkRecordCode("S025"),
					new ArrayList<>());			
			this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);
			
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("010"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_426")));
			this.errMessageInfoRepository.add(employmentErrMes);
			return ProcessState.SUCCESS;
		}
		String employmentCode = employmentHisOptional.get().getEmploymentCode();
		// Create task list and execute.
		Collection<List<GeneralDate>> exectedList = ContextSupport.partitionBySize(listDayBetween, 7);
		List<ProcessState> stateList = Collections.synchronizedList(new ArrayList<>());
		for (List<GeneralDate> listDay : exectedList){
			ProcessState processState = this.self.createDailyResultEmployeeNew(asyncContext,
					employeeId, listDay, companyId, empCalAndSumExecLogID, executionLog, reCreateWorkType,
					employeeGeneralInfoImport, stampReflectionManagement, mapWorkingConditionItem,
					mapDateHistoryItem, employmentHisOptional , employmentCode, periodInMasterList);
			if (processState == ProcessState.INTERRUPTION) {
				stateList.add(processState);
				return ProcessState.INTERRUPTION;
			}
			stateList.add(processState);
		}
		
		
//		exectedList.stream().map((dateList) -> {
//			AsyncTask asyncTask = AsyncTask.builder().withContexts().keepsTrack(false).build(() -> {
//				ProcessState processState = this.self.createDailyResultEmployeeNew(asyncContext, processingDate,
//						employeeId, dateList, companyId, empCalAndSumExecLogID, executionLog, reCreateWorkType,
//						employeeGeneralInfoImport, stampReflectionManagement, mapWorkingConditionItem,
//						mapDateHistoryItem);
//				stateList.add(processState);
//			});
//			return asyncTask;
//		}).collect(Collectors.toList());

		if (stateList.stream().allMatch(s -> s == ProcessState.SUCCESS)) {
			return ProcessState.SUCCESS;
		}

		// Return.
		return ProcessState.INTERRUPTION;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ProcessState createDailyResultEmployeeNew(AsyncCommandHandlerContext asyncContext,
			String employeeId, List<GeneralDate> executedDate, String companyId,
			String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog, boolean reCreateWorkType,
			EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem,
			Optional<EmploymentHistoryImported> employmentHisOptional,
			String employmentCode,
			PeriodInMasterList periodInMasterList) {

		for (GeneralDate day : executedDate) {
			// 締めIDを取得する
			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
					.findByEmploymentCD(companyId, employmentCode);
			// Optional<ClosureEmployment> closureEmploymentOptional =
			// this.provider().findClousureEmployementByEmpCd(companyId,
			// employmentCode);

			if (day.afterOrEquals(employmentHisOptional.get().getPeriod().end())
					&& day.beforeOrEquals(employmentHisOptional.get().getPeriod().start())) {
				return ProcessState.SUCCESS;
			} else {
				EmployeeAndClosureOutput employeeAndClosureDto = new EmployeeAndClosureOutput();
				if (employmentHisOptional.get().getEmploymentCode()
						.equals(closureEmploymentOptional.get().getEmploymentCD())) {
					employeeAndClosureDto.setClosureId(closureEmploymentOptional.get().getClosureId());
					employeeAndClosureDto.setEmployeeId(employeeId);
					employeeAndClosureDto.setPeriod(employmentHisOptional.get().getPeriod());
				}

				// アルゴリズム「実績ロックされているか判定する」を実行する
				EmployeeAndClosureOutput employeeAndClosure = this.determineActualLocked(companyId,
						employeeAndClosureDto, day);

				if (employeeAndClosure.getLock() == 0) {
					ExecutionType reCreateAttr = executionLog.get().getDailyCreationSetInfo().get().getExecutionType();

					if (reCreateAttr == ExecutionType.RERUN) {
						DailyRecreateClassification creationType = executionLog.get().getDailyCreationSetInfo().get()
								.getCreationType();
						if (creationType == DailyRecreateClassification.PARTLY_MODIFIED) {
							// 再設定
							this.resetDailyPerforDomainService.resetDailyPerformance(companyId, employeeId, day,
									empCalAndSumExecLogID, reCreateAttr, periodInMasterList, employeeGeneralInfoImport);
						} else {
							this.reflectWorkInforDomainService.reflectWorkInformation(companyId, employeeId, day,
									empCalAndSumExecLogID, reCreateAttr, reCreateWorkType, employeeGeneralInfoImport,
									stampReflectionManagement, mapWorkingConditionItem, mapDateHistoryItem, periodInMasterList);
						}
					} else {
						this.reflectWorkInforDomainService.reflectWorkInformation(companyId, employeeId, day,
								empCalAndSumExecLogID, reCreateAttr, reCreateWorkType, employeeGeneralInfoImport,
								stampReflectionManagement, mapWorkingConditionItem, mapDateHistoryItem, periodInMasterList);
					}
				}
				if (asyncContext.hasBeenRequestedToCancel()) {
					asyncContext.finishedAsCancelled();
					return ProcessState.INTERRUPTION;
				}
			}
		}

		// Return
		return ProcessState.SUCCESS;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ProcessState createDailyResultEmployeeWithNoInfoImport(AsyncCommandHandlerContext asyncContext,
			String employeeId, DatePeriod periodTime, String companyId, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog, boolean reCreateWorkType,
			Optional<StampReflectionManagement> stampReflectionManagement) {

		// 正常終了 : 0
		// 中断 : 1
		ProcessState status = ProcessState.SUCCESS;

		GeneralDate processingDate = periodTime.start();

		// lits day between startDate and endDate
		List<GeneralDate> listDayBetween = this.getDaysBetween(periodTime.start(), periodTime.end());

		// Imported（就業）「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId,
				employeeId, processingDate);
		String employmentCode = employmentHisOptional.get().getEmploymentCode();
		if (!employmentHisOptional.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("010"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_426")));
			this.errMessageInfoRepository.add(employmentErrMes);
			return ProcessState.SUCCESS;
		}

		// Create task list and execute.
		Collection<List<GeneralDate>> exectedList = ContextSupport.partitionBySize(listDayBetween, 7);
		List<ProcessState> stateList = Collections.synchronizedList(new ArrayList<>());
		for(List<GeneralDate> listDay : exectedList){
			ProcessState processState = this.self.createDailyResultEmployeeWithNoInfoImportNew(asyncContext,
					employeeId, listDay, companyId, empCalAndSumExecLogID, executionLog,
					reCreateWorkType, stampReflectionManagement, employmentHisOptional, employmentCode);
			stateList.add(processState);
		}
		
//		List<AsyncTask> taskList = exectedList.stream().map((dateList) -> {
//			AsyncTask asyncTask = AsyncTask.builder().withContexts().keepsTrack(false).build(() -> {
//				ProcessState processState = this.self.createDailyResultEmployeeWithNoInfoImportNew(asyncContext,
//						processingDate, employeeId, dateList, companyId, empCalAndSumExecLogID, executionLog,
//						reCreateWorkType, stampReflectionManagement);
//				stateList.add(processState);
//			});
//			return asyncTask;
//		}).collect(Collectors.toList());

		if (stateList.stream().allMatch(s -> s == ProcessState.SUCCESS)) {
			return ProcessState.SUCCESS;
		}

		// Return.
		return ProcessState.INTERRUPTION;

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public ProcessState createDailyResultEmployeeWithNoInfoImportNew(AsyncCommandHandlerContext asyncContext,
			String employeeId, List<GeneralDate> executeDate, String companyId,
			String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog, boolean reCreateWorkType,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Optional<EmploymentHistoryImported> employmentHisOptional,
			String employmentCode) {
		
		for (GeneralDate day : executeDate) {

			// 締めIDを取得する
			Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
					.findByEmploymentCD(companyId, employmentCode);
			// Optional<ClosureEmployment> closureEmploymentOptional =
			// this.provider().findClousureEmployementByEmpCd(companyId,
			// employmentCode);

			if (day.afterOrEquals(employmentHisOptional.get().getPeriod().end())
					&& day.beforeOrEquals(employmentHisOptional.get().getPeriod().start())) {
				return ProcessState.SUCCESS;
			} else {
				EmployeeAndClosureOutput employeeAndClosureDto = new EmployeeAndClosureOutput();
				if (employmentHisOptional.get().getEmploymentCode()
						.equals(closureEmploymentOptional.get().getEmploymentCD())) {
					employeeAndClosureDto.setClosureId(closureEmploymentOptional.get().getClosureId());
					employeeAndClosureDto.setEmployeeId(employeeId);
					employeeAndClosureDto.setPeriod(employmentHisOptional.get().getPeriod());
				}

				// アルゴリズム「実績ロックされているか判定する」を実行する
				EmployeeAndClosureOutput employeeAndClosure = this.determineActualLocked(companyId,
						employeeAndClosureDto, day);

				if (employeeAndClosure.getLock() == 0) {
					ExecutionType reCreateAttr = executionLog.get().getDailyCreationSetInfo().get().getExecutionType();

					if (reCreateAttr == ExecutionType.RERUN) {
						DailyRecreateClassification creationType = executionLog.get().getDailyCreationSetInfo().get()
								.getCreationType();
						if (creationType == DailyRecreateClassification.PARTLY_MODIFIED) {
							// 再設定
							this.resetDailyPerforDomainService.resetDailyPerformance(companyId, employeeId, day,
									empCalAndSumExecLogID, reCreateAttr, null , null);
						} else {
							this.reflectWorkInforDomainService.reflectWorkInformationWithNoInfoImport(companyId,
									employeeId, day, empCalAndSumExecLogID, reCreateAttr, reCreateWorkType,
									stampReflectionManagement);
						}
					} else {
						this.reflectWorkInforDomainService.reflectWorkInformationWithNoInfoImport(companyId, employeeId,
								day, empCalAndSumExecLogID, reCreateAttr, reCreateWorkType, stampReflectionManagement);
					}
				}
				if (asyncContext.hasBeenRequestedToCancel()) {
					asyncContext.finishedAsCancelled();
					return ProcessState.INTERRUPTION;
				}
			}
		}
		return ProcessState.SUCCESS;
	}

	/**
	 * 実績ロックされているか判定する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param processingDate
	 * @param closureId
	 * @return
	 */
	private EmployeeAndClosureOutput determineActualLocked(String companyId,
			EmployeeAndClosureOutput employeeAndClosure, GeneralDate day) {

		/**
		 * ロック : 1 , アンロック : 0
		 */

		/**
		 * アルゴリズム「当月の実績ロックの取得」を実行する
		 */

		Integer closureId = employeeAndClosure.getClosureId();
		// 全てのドメインモデル「当月の実績ロック」を取得する
		// List<ActualLock> actualLockLists =
		// this.actualLockRepository.findByListId(companyId, closureIds);

		Optional<ActualLock> actualLock = this.actualLockRepository.findById(companyId, closureId);

		if (!actualLock.isPresent()) {
			employeeAndClosure.setLock(0);
		} else {
			DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.min());
			if (actualLock.get().getDailyLockState().value == 0) {
				employeeAndClosure.setLock(0);
			} else {
				Optional<Closure> closure = this.closureRepository.findById(companyId, closureId);

				List<ClosureHistory> closureHistories = this.closureRepository.findByClosureId(companyId, closureId);

				// exist data
				if (closure.isPresent()) {

					// to data
					closure.get().setClosureHistories(closureHistories);
					// 基準日が当月に含まれているかチェックする
					Optional<ClosureHistory> closureHisory = this.closureRepository.findBySelectedYearMonth(companyId,
							closureId, closure.get().getClosureMonth().getProcessingYm().v());

					ClosureGetMonthDay closureGetMonthDay = new ClosureGetMonthDay();
					period = closureGetMonthDay.getDayMonth(closureHisory.get().getClosureDate(),
							closure.get().getClosureMonth().getProcessingYm().v());
				}
				if (day.afterOrEquals(period.start()) && day.beforeOrEquals(period.end())) {
					employeeAndClosure.setLock(1);
				} else {
					employeeAndClosure.setLock(0);
				}
			}
		}

		return employeeAndClosure;
	}

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

}
