package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.DailyMonthlyprocessAdapterSch;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.ExeStateOfCalAndSumImportSch;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusImported;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScShortWorkTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.ScEmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.WorkRestTimeZoneDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHis;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScheduleCreatorExecutionService {
	@Inject
	private ManagedParallelWithContext parallel;

	/** The schedule execution log repository. */
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;

	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	/** The content repository. */
	@Inject
	private ScheduleCreateContentRepository contentRepository;

	/** The create content repository. */
	@Inject
	private ScheduleCreateContentRepository scheduleCreateContentRepository;

	@Inject
	private ScEmployeeGeneralInfoAdapter scEmpGeneralInfoAdapter;

	@Inject
	private EmploymentStatusAdapter employmentStatusAdapter;

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private BusinessTypeOfEmployeeService businessTypeOfEmpHisService;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	@Inject
	private ScShortWorkTimeAdapter scShortWorkTimeAdapter;

	@Inject
	private ScTimeAdapter scTimeAdapter;

	@Inject
	private ScheduleCreatorExecutionTransaction transaction;

	@Inject
	private ClosureEmploymentRepository closureEmployment;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private I18NResourcesForUK internationalization;

//	@Inject
//	private EmpEmployeeAdapter empEmployeeAdapter;

	@Inject
	private EmpComHisAdapter comHisAdapter;

	@Inject
	private WorkingConditionRepository conditionRespo;

	@Inject
	private EmpLeaveHistoryAdapter empHisAdapter;

	@Inject
	private EmpLeaveWorkHistoryAdapter leaHisAdapter;

	@Inject
	private EmploymentHisScheduleAdapter scheAdapter;
	
	@Inject
	private EmpAffiliationInforAdapter empAffiliationInforAdapter;
	
	@Inject
	protected TransactionService transactionService;

	/** The Constant DEFAULT_CODE. */
	public static final String DEFAULT_CODE = "000";

	/** The Constant NEXT_DAY_MONTH. */
	public static final int NEXT_DAY_MONTH = 1;

	/** The Constant ZERO_DAY_MONTH. */
	public static final int ZERO_DAY_MONTH = 0;

	/** The Constant MUL_YEAR. */
	public static final int MUL_YEAR = 10000;

	/** The Constant MUL_MONTH. */
	public static final int MUL_MONTH = 100;

	/** The Constant SHIFT1. */
	public static final int SHIFT1 = 1;

	/** The Constant SHIFT2. */
	public static final int SHIFT2 = 2;

	/** The Constant BEFORE_JOINING. */
	// ?????????
	public static final int BEFORE_JOINING = 4;

	/** The Constant ON_LOAN. */
	// ?????????
	public static final int ON_LOAN = 5;

	/** The Constant RETIREMENT. */
	// ??????
	public static final int RETIREMENT = 6;

	/** The Constant INCUMBENT. */
	// ??????
	public static final int INCUMBENT = 1;

	/** The Constant LEAVE_OF_ABSENCE. */
	// ??????
	public static final int LEAVE_OF_ABSENCE = 2;

	/** The Constant HOLIDAY. */
	// ??????
	public static final int HOLIDAY = 3;

	@SuppressWarnings("rawtypes")
	public void handle(ScheduleCreatorExecutionCommand command, Optional<AsyncCommandHandlerContext> asyncTask) {
		System.out.println("Run batch service !");
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		command.setCompanyId(companyId);
		
		transactionService.execute(() -> preExecute(command, companyId));
		
		try {
			this.registerPersonalSchedule(command, command.getScheduleExecutionLog(), asyncTask, companyId);
		} catch (Exception ex) {
			command.setIsExForKBT(true);
			throw ex;
		}
	}

	private void preExecute(ScheduleCreatorExecutionCommand command, String companyId) {
		
		if (!command.isAutomatic()) {
			// update command

			val scheduleExecutionLog = this.scheduleExecutionLogRepository.findById(companyId, command.getExecutionId()).get();

			scheduleExecutionLog.setExecutionTimeToNow();
			scheduleExecutionLog.setExeAtrIsManual();

			// update domain execution log
			this.scheduleExecutionLogRepository.update(scheduleExecutionLog);

			// find execution content by id
			command.setContent(this.contentRepository.findByExecutionId(command.getExecutionId()).get());
			command.setScheduleExecutionLog(scheduleExecutionLog);
			
		} else {
			// ???????????????????????????????????????????????????????????????
			createExcutionLog(command, command.getScheduleExecutionLog());
		}
	}

	@Inject
	private DailyMonthlyprocessAdapterSch dailyMonthlyprocessAdapterSch;

	/**
	 * ???????????????????????????????????????: register Personal Schedule
	 *
	 * @param command
	 * @param scheduleExecutionLog
	 * @param context
	 */
	private void registerPersonalSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog, 
			@SuppressWarnings("rawtypes") Optional<AsyncCommandHandlerContext> asyncTask,
			String companyId) {

		String exeId = command.getExecutionId();
		DatePeriod period = scheduleExecutionLog.getPeriod();

		// get all data creator
		List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository.findAll(exeId);
		List<String> employeeIds = scheduleCreators.stream().map(item -> item.getEmployeeId()).collect(Collectors.toList());

		// EA No2017
		// ??????????????????????????????
		CreateScheduleMasterCache masterCache = this.acquireData(companyId, employeeIds, period);

		// at.record??????????????????????????????????????????????????????????????????????????????????????????????????????
		Object companySetting = scTimeAdapter.getCompanySettingForCalculation();
		AtomicBoolean checkStop = new AtomicBoolean(false);
		CacheCarrier carrier = new CacheCarrier();
		this.parallel.forEach(
				scheduleCreators.stream().sorted((a,b) -> a.getEmployeeId().compareTo(b.getEmployeeId())).collect(Collectors.toList()),
				scheduleCreator -> {					
					createScheOnePerson(command, scheduleExecutionLog, asyncTask, companyId, exeId, period, masterCache,
						companySetting, checkStop, carrier, scheduleCreator);

		});

		transactionService.execute(() -> postExecute(command, scheduleExecutionLog, asyncTask, exeId, companySetting));
	}

	@SuppressWarnings("rawtypes")
	private void createScheOnePerson(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog,
			Optional<AsyncCommandHandlerContext> asyncTask, String companyId, String exeId, DatePeriod period,
			CreateScheduleMasterCache masterCache, Object companySetting, AtomicBoolean checkStop, CacheCarrier carrier,
			ScheduleCreator scheduleCreator) {
		if (scheduleCreator == null)
			return;
		if (scheduleExecutionLog.getExeAtr() == ExecutionAtr.AUTOMATIC) {
			if (checkStop.get()) {
				return;
			}
			Optional<ExeStateOfCalAndSumImportSch> exeStateOfCalAndSumImportSch = dailyMonthlyprocessAdapterSch
					.executionStatus(exeId);
			if (exeStateOfCalAndSumImportSch.isPresent())
				if (exeStateOfCalAndSumImportSch.get() == ExeStateOfCalAndSumImportSch.START_INTERRUPTION) {
					checkStop.set(true);
					this.updateStatusScheduleExecutionLog(scheduleExecutionLog, CompletionStatus.INTERRUPTION);
					return;
				}
		} else {
			// check is client submit cancel
			if (asyncTask.map(c -> c.hasBeenRequestedToCancel()).orElse(false)) {
				// ??????????????????????????????????????????????????????????????????????????????(update domain ??????????????????????????????????????????)
				this.updateStatusScheduleExecutionLog(scheduleExecutionLog, CompletionStatus.INTERRUPTION);
				return;
			}
		}

		// ??????????????????????????????????????????????????????????????????????????????????????????
		StateAndValueDatePeriod stateAndValueDatePeriod = this.correctTargetPeriodAfterClosingStartDate(
				command.getCompanyId(), scheduleCreator.getEmployeeId(), period, masterCache.getEmpGeneralInfo());

		// ??????????????????????????????
		if (stateAndValueDatePeriod.state == StateValueDate.TARGET_PERIOD ) {
			DatePeriod dateAfterCorrection = stateAndValueDatePeriod.getValue();

			// process each by 2 months to make transaction small for performance
			final int unitMonthsOfTransaction = 2;
			dateAfterCorrection.forEachByMonths(unitMonthsOfTransaction, subPeriod -> {

				// ????????????????????????
				this.transaction.execute(
						command,
						scheduleExecutionLog,
						asyncTask,
						companyId,
						exeId,
						subPeriod,
						masterCache,
						companySetting,
						scheduleCreator,
						carrier);
			});
		} else {
			String errorContent = null;
			
			if(stateAndValueDatePeriod.state == StateValueDate.NO_TARGET_PERIOD)
			errorContent = this.internationalization.localize("Msg_1509").get();
			
			if(stateAndValueDatePeriod.state == StateValueDate.NO_EMPLOYMENT_HIST)
			errorContent = this.internationalization.localize("Msg_2196").get();
			
			// ?????????????????????????????????????????????????????????????????????????????????
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
					stateAndValueDatePeriod.value.end(), scheduleCreator.getEmployeeId());
			this.scheduleErrorLogRepository.add(scheduleErrorLog);

			scheduleCreator.updateToCreated();
			this.scheduleCreatorRepository.update(scheduleCreator);
		}
	}

	@SuppressWarnings("rawtypes")
	private void postExecute(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog,
			Optional<AsyncCommandHandlerContext> asyncTask, String exeId, Object companySetting) {
		scTimeAdapter.clearCompanySettingShareContainer(companySetting);

		if (scheduleExecutionLog.getExeAtr() == ExecutionAtr.AUTOMATIC) {
			val exeStateOfCalAndSumImportSch = dailyMonthlyprocessAdapterSch.executionStatus(exeId);
			if (exeStateOfCalAndSumImportSch.isPresent())
				if (exeStateOfCalAndSumImportSch.get() == ExeStateOfCalAndSumImportSch.START_INTERRUPTION) {
					return;
				}
		} else {
			if (asyncTask.map(c -> c.hasBeenRequestedToCancel()).orElse(false)) {
				asyncTask.ifPresent(c -> c.finishedAsCancelled());
			}
		}

		// EA???????????? No2378
		// ??????????????????????????????????????????????????????????????????????????????
		this.scheduleExecutionLogRepository.findById(command.getCompanyId(), scheduleExecutionLog.getExecutionId()).ifPresent(log -> {
				if (log.getCompletionStatus() != CompletionStatus.INTERRUPTION) 
					this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
			});
	}

	/**
	 * ????????????????????????
	 *
	 * @author danpv
	 */
	private void createExcutionLog(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog) {
		ScheduleCreateContent scheduleCreateContent = command.getContent();
		List<ScheduleCreator> scheduleCreators = command.getEmployeeIds().stream()
				.map(sId -> new ScheduleCreator(command.getExecutionId(), ExecutionStatus.NOT_CREATED, sId))
				.collect(Collectors.toList());
		// ???????????????????????????????????????????????????????????????
		this.executionLogCreationProcess(scheduleExecutionLog, scheduleCreateContent, scheduleCreators);
	}

	/**
	 * ????????????????????????
	 *
	 * @param scheduleExecutionLog
	 * @param scheduleCreateContent
	 * @param scheduleCreators
	 */
	private void executionLogCreationProcess(ScheduleExecutionLog scheduleExecutionLog,
			ScheduleCreateContent scheduleCreateContent, List<ScheduleCreator> scheduleCreators) {
		// ????????????????????????????????????????????????????????????????????????????????????
		this.scheduleExecutionLogRepository.addNew(scheduleExecutionLog);
		// ??????????????????????????????????????????????????????????????????????????????
		this.scheduleCreateContentRepository.addNew(scheduleCreateContent);
		// ?????????????????????????????????????????????????????????????????????????????????
		this.scheduleCreatorRepository.saveAllNew(scheduleCreators);
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param companyId
	 * @param listWorkType
	 * @param listWorkTimeSetting
	 * @param mapFixedWorkSetting
	 * @param listFlowWorkSetting
	 * @param listDiffTimeWorkSetting
	 */
	private CreateScheduleMasterCache acquireData(String companyId, List<String> employeeIds, DatePeriod period) {

		// ???????????????????????????
		// Imported(??????)??????????????????????????????????????????
		// ????????????????????????????????????????????????
		// EA???????????????No1675
		EmployeeGeneralInfoImported empGeneralInfo = this.scEmpGeneralInfoAdapter.getPerEmpInfo(employeeIds, period);

		// ?????????????????????????????????
		// ????????????????????????????????????????????????????????????????????????
		// ???????????????????????????????????????????????????????????????
		// <<Public>> ??????ID(List)?????????????????????????????????????????????????????????
		List<BusinessTypeOfEmployeeHis> listBusTypeOfEmpHis = this.businessTypeOfEmpHisService.find(employeeIds,
				period);
		empGeneralInfo.setListBusTypeOfEmpHis(listBusTypeOfEmpHis);
		// Imported(??????)??????????????????????????????????????????
		Map<String, List<EmploymentInfoImported>> mapEmploymentStatus = this.employmentStatusAdapter
				.findListOfEmployee(employeeIds, period).stream().collect(Collectors
						.toMap(EmploymentStatusImported::getEmployeeId, EmploymentStatusImported::getEmploymentInfo));

		// ?????????????????????????????????
		// EA No1828
		// ??????ID(List)?????????????????????????????????
		List<WorkCondItemDto> listWorkingConItem = this.acquireWorkingConditionInformation(employeeIds, period);

		// ?????????????????????????????????????????????
		// ??????????????????????????????????????????????????????
		// EA No2134
		List<ShortWorkTimeDto> listShortWorkTimeDto = this.scShortWorkTimeAdapter.findShortWorkTimes(employeeIds,
				period);

		// ???????????????????????????????????????????????? ???
		// ToDo
		// ????????????????????????
		// ??????????????????????????????ID???????????????
		List<EmployeeWorkingStatus> lstStatuTempos = new ArrayList<>();
		for (val id : employeeIds) {
			// ??????????????????
			for (val date : period.datesBetween()) {
				// ????????????????????????????????????????????????
				// ???Output???????????????????????????????????????
				EmployeeWorkingStatus.Require require = new EmployeeWorkingStatusRequireImpl(companyId, comHisAdapter,
						conditionRespo, empHisAdapter, leaHisAdapter, scheAdapter);
				EmployeeWorkingStatus manaStatuTempo = EmployeeWorkingStatus.create(require, id, date);
				lstStatuTempos.add(manaStatuTempo);
			}
		}
		// -----???

		// ????????????????????????????????? ???
		// EA???????????? No2282
		// ??????????????????????????????????????????????????????
		List<WorkType> lstWorkTypeInfo = workTypeRepository.findByCompanyId(companyId);
		// -----???
		// ??????????????????????????????????????????????????????????????? (Comment theo lu???ng c???a b??c B??nh)
		CreateScheduleMasterCache cache = new CreateScheduleMasterCache(empGeneralInfo, mapEmploymentStatus,
				listWorkingConItem, listShortWorkTimeDto, listBusTypeOfEmpHis, lstWorkTypeInfo, lstStatuTempos);

		// ?????????????????????????????????????????????????????? - ???????????? ??? ???????????????
		cache.getListWorkType().addAll(this.workTypeRepository.findNotDeprecateByCompanyId(companyId));
		// ??????????????????????????????????????????????????????????????????
		cache.getListWorkTimeSetting().addAll(this.workTimeSettingRepository.findActiveItems(companyId));
		// EA???????????? No2103
		List<String> listWorkTimeCodeFix = new ArrayList<>();
		List<String> listWorkTimeCodeFlow = new ArrayList<>();
		List<String> listWorkTimeCodeDiff = new ArrayList<>();
		cache.getListWorkTimeSetting().forEach(workTime -> {
			WorkTimeDivision workTimeDivision = workTime.getWorkTimeDivision();
			if (workTimeDivision.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK) {
				if (workTimeDivision.getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
					listWorkTimeCodeFix.add(workTime.getWorktimeCode().v());
				} else if (workTimeDivision.getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
					listWorkTimeCodeFlow.add(workTime.getWorktimeCode().v());
				} else {
					listWorkTimeCodeDiff.add(workTime.getWorktimeCode().v());
				}
			}
		});
		// ????????????????????????????????????????????????????????????
		if (!listWorkTimeCodeFix.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixOffdayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixOffdayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixHalfDayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			this.setDataForMap(cache.getMapFixedWorkSetting(), mapFixOffdayWorkRestTimezones,
					mapFixHalfDayWorkRestTimezones);
		}
		// ????????????????????????????????????????????????????????????
		if (!listWorkTimeCodeFlow.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowOffdayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowOffdayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowHalfDayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			this.setDataForMap(cache.getMapFlowWorkSetting(), mapFlowOffdayWorkRestTimezones,
					mapFlowHalfDayWorkRestTimezones);
		}
		// ????????????????????????????????????????????????????????????
		if (!listWorkTimeCodeDiff.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffOffdayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffOffdayWorkRestTimezones(companyId, listWorkTimeCodeDiff);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffHalfDayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffHalfDayWorkRestTimezones(companyId, listWorkTimeCodeDiff);

			this.setDataForMap(cache.getMapDiffTimeWorkSetting(), mapDiffOffdayWorkRestTimezones,
					mapDiffHalfDayWorkRestTimezones);
		}
		// ???????????????????????????
		return cache;
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param sIds
	 * @param datePeriod
	 * @return
	 */
	private List<WorkCondItemDto> acquireWorkingConditionInformation(List<String> sIds, DatePeriod datePeriod) {
		// EA???????????? No1829
		// ??????????????????????????????????????????????????????
		List<WorkingCondition> listWorkingCondition = this.workingConditionRepository.getBySidsAndDatePeriod(sIds,
				datePeriod);

		// ????????????????????????????????????????????????????????????
		List<WorkingConditionItem> listWorkingConditionItem = this.workingConditionItemRepository
				.getBySidsAndDatePeriod(sIds, datePeriod);
		// ??????????????????????????????????????????????????????
		Map<String, WorkingConditionItem> mapWorkingCondtionItem = listWorkingConditionItem.stream()
				.collect(Collectors.toMap(WorkingConditionItem::getHistoryId, x -> x));
		List<WorkCondItemDto> listWorkCondItemDto = new ArrayList<>();
		listWorkingCondition.forEach(x -> x.getDateHistoryItem().forEach(y -> {
			WorkingConditionItem workingConditionItem = mapWorkingCondtionItem.get(y.identifier());
			WorkCondItemDto workCondItemDto = new WorkCondItemDto(workingConditionItem);
			workCondItemDto.setDatePeriod(y.span());
			listWorkCondItemDto.add(workCondItemDto);
		}));

		return listWorkCondItemDto;
	}

	/**
	 *
	 * @param map
	 * @param map1
	 * @param map2
	 */
	private void setDataForMap(Map<String, WorkRestTimeZoneDto> map, Map<WorkTimeCode, List<AmPmWorkTimezone>> map1,
			Map<WorkTimeCode, List<AmPmWorkTimezone>> map2) {
		if (map1.size() >= map2.size()) {
			map1.forEach((key, value) -> {
				map.put(key.v(), new WorkRestTimeZoneDto(value, map2.get(key)));
			});
		} else {
			map2.forEach((key, value) -> {
				map.put(key.v(), new WorkRestTimeZoneDto(map1.get(key), value));
			});
		}
	}

	/**
	 * Update status schedule execution log.
	 *
	 * @param domain the domain
	 */
	private void updateStatusScheduleExecutionLog(ScheduleExecutionLog domain) {
		List<ScheduleErrorLog> scheduleErrorLogs = this.scheduleErrorLogRepository
				.findByExecutionId(domain.getExecutionId());

		// check exist data schedule error log
		if (CollectionUtil.isEmpty(scheduleErrorLogs)) {
			domain.setCompletionStatus(CompletionStatus.DONE);
		} else {
			domain.setCompletionStatus(CompletionStatus.COMPLETION_ERROR);
		}
		domain.updateExecutionTimeEndToNow();
		this.scheduleExecutionLogRepository.update(domain);
	}

	/**
	 * Update status schedule execution log.
	 *
	 * @param domain the domain
	 */
	private void updateStatusScheduleExecutionLog(ScheduleExecutionLog domain, CompletionStatus completionStatus) {
		// check exist data schedule error log
		domain.setCompletionStatus(completionStatus);
		domain.updateExecutionTimeEndToNow();
		this.scheduleExecutionLogRepository.update(domain);
	}

	/**
	 * ??????????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param companyId
	 * @param employeeId
	 * @param dateBeforeCorrection
	 * @param empGeneralInfo
	 * @return
	 */
	private StateAndValueDatePeriod correctTargetPeriodAfterClosingStartDate(String companyId, String employeeId,
			DatePeriod dateBeforeCorrection, EmployeeGeneralInfoImported empGeneralInfo) {
		// EA No1676
		Map<String, List<ExEmploymentHistItemImported>> mapEmploymentHist = empGeneralInfo.getEmploymentDto().stream()
				.collect(Collectors.toMap(ExEmploymentHistoryImported::getEmployeeId,
						ExEmploymentHistoryImported::getEmploymentItems));

		List<ExEmploymentHistItemImported> listEmpHistItem = mapEmploymentHist.get(employeeId);
		Optional<ExEmploymentHistItemImported> optEmpHistItem = Optional.empty();
		if (listEmpHistItem != null) {
			optEmpHistItem = listEmpHistItem.stream()
					.filter(empHistItem -> empHistItem.getPeriod().contains(dateBeforeCorrection.end())).findFirst();
		}
		// fix bug #113874
		if (!optEmpHistItem.isPresent()) {
			return new StateAndValueDatePeriod(dateBeforeCorrection, StateValueDate.NO_EMPLOYMENT_HIST); // false
		}

		// ??????????????????????????????????????????????????????????????????
		Optional<ClosureEmployment> optionalClosureEmployment = this.closureEmployment.findByEmploymentCD(companyId,
				optEmpHistItem.get().getEmploymentCode());
		if (!optionalClosureEmployment.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, StateValueDate.NO_TARGET_PERIOD); // false
		// ??????????????????????????????????????????
		Optional<Closure> optionalClosure = this.closureRepository.findById(companyId,
				optionalClosureEmployment.get().getClosureId());
		if (!optionalClosure.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, StateValueDate.NO_TARGET_PERIOD); // false
		// ???????????????????????????????????????????????????????????????
		DatePeriod dateP = ClosureService.getClosurePeriod(optionalClosure.get().getClosureId().value,
				optionalClosure.get().getClosureMonth().getProcessingYm(), optionalClosure);
		// Input?????????????????????????????????????????????????????????????????????
		DatePeriod dateAfterCorrection = dateBeforeCorrection;
		if (dateBeforeCorrection.start().before(dateP.start())) {
//			dateAfterCorrection = dateBeforeCorrection.cutOffWithNewStart(dateP.start());
			dateAfterCorrection = new DatePeriod(dateP.start(), dateAfterCorrection.end());
		}
		// Output??????????????????(?????????)????????????????????????????????????. ????????????????????????????????????
		if (dateAfterCorrection.start().beforeOrEquals(dateBeforeCorrection.end())) {
			// Out??????????????????(?????????)?????????Input????????????????????????????????????
//			dateAfterCorrection = dateAfterCorrection.cutOffWithNewEnd(dateBeforeCorrection.end());
			dateAfterCorrection = new DatePeriod(dateAfterCorrection.start(), dateBeforeCorrection.end());
			return new StateAndValueDatePeriod(dateAfterCorrection, StateValueDate.TARGET_PERIOD); // true
		}

		return new StateAndValueDatePeriod(dateAfterCorrection, StateValueDate.NO_TARGET_PERIOD); // false
	}

	@AllArgsConstructor
	public class EmployeeWorkingStatusRequireImpl implements EmployeeWorkingStatus.Require {

		String companyId = AppContexts.user().companyId();

		@Inject
		private EmpComHisAdapter comHisAdapter;

		@Inject
		private WorkingConditionRepository conditionRespo;

		@Inject
		private EmpLeaveHistoryAdapter empHisAdapter;

		@Inject
		private EmpLeaveWorkHistoryAdapter leaHisAdapter;

		@Inject
		private EmploymentHisScheduleAdapter scheAdapter;


		@Override
		public Optional<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(String employeeId, GeneralDate date) {
			val result = comHisAdapter.getEnrollmentPeriod(Arrays.asList(employeeId), new DatePeriod(date, date));
			if (result.isEmpty())
				return Optional.empty();
			return Optional.of(result.get(0));
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate date) {
			return conditionRespo.getWorkingConditionItemByEmpIDAndDate(companyId, date, employeeId);
		}

		@Override
		public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(String employeeId, GeneralDate date) {
			val result = empHisAdapter.getLeaveBySpecifyingPeriod(Arrays.asList(employeeId),
					new DatePeriod(date, date));
			if (result.isEmpty())
				return Optional.empty();
			return Optional.of(result.get(0));
		}

		@Override
		public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(String employeeId, GeneralDate date) {
			val result = leaHisAdapter.getHolidayPeriod(Arrays.asList(employeeId), new DatePeriod(date, date));
			if (result.isEmpty())
				return Optional.empty();
			return Optional.of(result.get(0));
		}

		@Override
		public Optional<EmploymentPeriodImported> getEmploymentHistory(String employeeId, GeneralDate date) {
			val result = scheAdapter.getEmploymentPeriod(Arrays.asList(employeeId), new DatePeriod(date, date));
			if (result.isEmpty())
				return Optional.empty();
			return Optional.of(result.get(0));
		}


		@Override
		public List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate, List<String> lstEmpId) {
			return empAffiliationInforAdapter.getEmpOrganization(baseDate, lstEmpId);
		}

	}
}
