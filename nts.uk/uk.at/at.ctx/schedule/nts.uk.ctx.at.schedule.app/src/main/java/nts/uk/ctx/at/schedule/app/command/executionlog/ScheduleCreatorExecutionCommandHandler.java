/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
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
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
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
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class ScheduleCreatorExecutionCommandHandler.
 *///
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ScheduleCreatorExecutionCommandHandler extends AsyncCommandHandler<ScheduleCreatorExecutionCommand> {

//	@Inject
//	private ManagedParallelWithContext parallel;

	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;

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
	// 入社�	public static final int BEFORE_JOINING = 4;

	/** The Constant ON_LOAN. */
	// 出向中
	public static final int ON_LOAN = 5;

	/** The Constant RETIREMENT. */
	// 退職
	public static final int RETIREMENT = 6;

	/** The Constant INCUMBENT. */
	// 在職
	public static final int INCUMBENT = 1;

	/** The Constant LEAVE_OF_ABSENCE. */
	// 休�
	public static final int LEAVE_OF_ABSENCE = 2;

	/** The Constant HOLIDAY. */
	// 休業
	public static final int HOLIDAY = 3;

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.arc.layer.app.command.AsyncCommandHandler#handle(nts.arc.layer.app.
	 * command.CommandHandlerContext)
	 */

	@Override
	public void handle(CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		System.out.println("Run batch service !");
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		ScheduleCreatorExecutionCommand command = context.getCommand();

		if (!command.isAutomatic()) {
			ScheduleExecutionLog scheduleExecutionLog = new ScheduleExecutionLog();

			// update command
			command.setCompanyId(companyId);
//			command.setIsDeleteBeforInsert(false);

			// find execution log by id
			scheduleExecutionLog = this.scheduleExecutionLogRepository.findById(companyId, command.getExecutionId())
					.get();

			// update execution time to now
			scheduleExecutionLog.setExecutionTimeToNow();

			// set exeAtr is manual
			scheduleExecutionLog.setExeAtrIsManual();

			// update domain execution log
			this.scheduleExecutionLogRepository.update(scheduleExecutionLog);

			// find execution content by id
			ScheduleCreateContent scheCreContent = this.contentRepository.findByExecutionId(command.getExecutionId())
					.get();
			command.setContent(scheCreContent);
//			command.setConfirm(scheCreContent.getConfirm());
			// register personal schedule
			this.registerPersonalSchedule(command, scheduleExecutionLog, context, companyId);
		} else {
			// ドメインモッ�「スケジュール作�実行ログ」を新規登録する
			try {
				this.registerPersonalSchedule(command, command.getScheduleExecutionLog(), context, companyId);
			} catch (Exception ex) {
				command.setIsExForKBT(true);
				throw ex;
			} finally {
				if (command.getCountDownLatch() != null) {
					command.getCountDownLatch().countDown();
				}
			}
		}
	}

	@Inject
	private DailyMonthlyprocessAdapterSch dailyMonthlyprocessAdapterSch;

	/**
	 * 個人スケジュールを登録する: register Personal Schedule
	 *
	 * @param command
	 * @param scheduleExecutionLog
	 * @param context
	 */
	private void registerPersonalSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog, CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			String companyId) {

		String exeId = command.getExecutionId();
		DatePeriod period = scheduleExecutionLog.getPeriod();

		// パラメータ実施区刂�判�		if (scheduleExecutionLog.getExeAtr() == ExecutionAtr.AUTOMATIC) {
			// アルゴリズ�「実行ログ作�処琀�を実行す�			createExcutionLog(command, scheduleExecutionLog);
		}
		// get all data creator
		List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository.findAll(exeId);
		List<String> employeeIds = scheduleCreators.stream().map(item -> item.getEmployeeId())
				.collect(Collectors.toList());

		// EA No2017
		// マスタ惱を取得す�		CreateScheduleMasterCache masterCache = this.acquireData(companyId, employeeIds, period);

		// get info by context
		val asyncTask = context.asAsync();

		// at.recordの計算�琁�使用する共通�会社設定�、ここで取得しキャヂ�ュしておく
		Object companySetting = scTimeAdapter.getCompanySettingForCalculation();
		AtomicBoolean checkStop = new AtomicBoolean(false);
		CacheCarrier carrier = new CacheCarrier();
		scheduleCreators.stream()
			.sorted((a, b) -> a.getEmployeeId().compareTo(b.getEmployeeId()))
			.forEach(scheduleCreator -> {
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
							this.updateStatusScheduleExecutionLog(scheduleExecutionLog,
									CompletionStatus.INTERRUPTION);
							return;
						}
				} else {
					// check is client submit cancel
					if (asyncTask.hasBeenRequestedToCancel()) {
						// ドメインモッ�「スケジュール作�実行ログ」を更新する(update domain 「スケジュール作�実行ログ�
						this.updateStatusScheduleExecutionLog(scheduleExecutionLog, CompletionStatus.INTERRUPTION);
						return;
					}
				}

			// アルゴリズム「対象期間を締め開始日以降に補正する」を実行する
			StateAndValueDatePeriod stateAndValueDatePeriod = this.correctTargetPeriodAfterClosingStartDate(
					command.getCompanyId(), scheduleCreator.getEmployeeId(), period,
					masterCache.getEmpGeneralInfo());

			// 対象期間あり　の場合
			if (stateAndValueDatePeriod.state == StateValueDate.TARGET_PERIOD ) {
				DatePeriod dateAfterCorrection = stateAndValueDatePeriod.getValue();

				// process each by 2 months to make transaction small for performance
				final int unitMonthsOfTransaction = 2;
				dateAfterCorrection.forEachByMonths(unitMonthsOfTransaction, subPeriod -> {

					List<BasicSchedule> listBasicSchedule = this.basicScheduleRepository.findSomePropertyWithJDBC(
							Arrays.asList(scheduleCreator.getEmployeeId()), subPeriod);

					// 勤務予定作成する
					this.transaction.execute(command, scheduleExecutionLog, context, companyId, exeId,
							subPeriod, masterCache, listBasicSchedule, asyncTask, companySetting,
							scheduleCreator, carrier);
				});
			} else {
				String errorContent = null;
				
				if(stateAndValueDatePeriod.state == StateValueDate.NO_TARGET_PERIOD)
				errorContent = this.internationalization.localize("Msg_1509").get();
				
				if(stateAndValueDatePeriod.state == StateValueDate.NO_EMPLOYMENT_HIST)
				errorContent = this.internationalization.localize("Msg_426").get();
				
					// ドメインモッ�「スケジュール作�エラーログ」を登録する
					ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
							stateAndValueDatePeriod.value.end(), scheduleCreator.getEmployeeId());
					this.scheduleErrorLogRepository.add(scheduleErrorLog);

				scheduleCreator.updateToCreated();
				this.scheduleCreatorRepository.update(scheduleCreator);
			}
		});
		scTimeAdapter.clearCompanySettingShareContainer(companySetting);

		if (scheduleExecutionLog.getExeAtr() == ExecutionAtr.AUTOMATIC) {
			Optional<ExeStateOfCalAndSumImportSch> exeStateOfCalAndSumImportSch = dailyMonthlyprocessAdapterSch
					.executionStatus(exeId);
			if (exeStateOfCalAndSumImportSch.isPresent())
				if (exeStateOfCalAndSumImportSch.get() == ExeStateOfCalAndSumImportSch.START_INTERRUPTION) {
					return;
				}
			// EA修正履歴 No2378
			// ドメインモッ�「スケジュール作�実行ログ」を取得す�find execution log by id
			ScheduleExecutionLog scheExeLog = this.scheduleExecutionLogRepository
					.findById(command.getCompanyId(), scheduleExecutionLog.getExecutionId()).get();
			if (scheExeLog.getCompletionStatus() != CompletionStatus.INTERRUPTION) {
				this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
			}
		} else {
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
			}
			ScheduleExecutionLog scheExeLog = this.scheduleExecutionLogRepository
					.findById(command.getCompanyId(), scheduleExecutionLog.getExecutionId()).orElse(null);
			if (scheExeLog != null && scheExeLog.getCompletionStatus() != CompletionStatus.INTERRUPTION) {
				System.out.println("not hasBeenRequestedToCancel: " + asyncTask.hasBeenRequestedToCancel() + "&exeid="
						+ scheduleExecutionLog.getExecutionId());
				this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
			}
		}

	}

	/**
	 * 実行ログ作�処�	 *
	 * @author danpv
	 */
	private void createExcutionLog(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog) {
		ScheduleCreateContent scheduleCreateContent = command.getContent();
		List<ScheduleCreator> scheduleCreators = command.getEmployeeIds().stream()
				.map(sId -> new ScheduleCreator(command.getExecutionId(), ExecutionStatus.NOT_CREATED, sId))
				.collect(Collectors.toList());
		// アルゴリズ�「実行ログ作�処琀�を実行す�		this.executionLogCreationProcess(scheduleExecutionLog, scheduleCreateContent, scheduleCreators);
	}

	/**
	 * 実行ログ作�処�	 *
	 * @param scheduleExecutionLog
	 * @param scheduleCreateContent
	 * @param scheduleCreators
	 */
	private void executionLogCreationProcess(ScheduleExecutionLog scheduleExecutionLog,
			ScheduleCreateContent scheduleCreateContent, List<ScheduleCreator> scheduleCreators) {
		// ドメインモッ�「スケジュール作�実行ログ」を新規登録する
		this.scheduleExecutionLogRepository.addNew(scheduleExecutionLog);
		// ドメインモッ�「スケジュール作�冮�」を新規登録する
		this.scheduleCreateContentRepository.addNew(scheduleCreateContent);
		// ドメインモッ�「スケジュール作�対象耀�を新規登録する
		this.scheduleCreatorRepository.saveAllNew(scheduleCreators);
	}

	/**
	 * マスタ惱を取得す�	 *
	 * @param companyId
	 * @param listWorkType
	 * @param listWorkTimeSetting
	 * @param mapFixedWorkSetting
	 * @param listFlowWorkSetting
	 * @param listDiffTimeWorkSetting
	 */
	private CreateScheduleMasterCache acquireData(String companyId, List<String> employeeIds, DatePeriod period) {

		// 所属情報を取得す�		// Imported(就業)「社員の履歴惱」を取得す�		// 職場、�位〛�用、�類を取得す�		// EA修正履歴�No1675
		EmployeeGeneralInfoImported empGeneralInfo = this.scEmpGeneralInfoAdapter.getPerEmpInfo(employeeIds, period);

		// 勤務種別惱を取得す�		// ドメインモッ�「社員の勤務種別の履歴」を取得す�		// ドメインモッ�「社員の勤務種別」を取得す�		// <<Public>> 社員ID(List)、期間で期間�勤務種別惱を取得す�		List<BusinessTypeOfEmployeeHis> listBusTypeOfEmpHis = this.businessTypeOfEmpHisService.find(employeeIds,
				period);
		empGeneralInfo.setListBusTypeOfEmpHis(listBusTypeOfEmpHis);
		// Imported(就業)「社員の在職状態」を取得す�		Map<String, List<EmploymentInfoImported>> mapEmploymentStatus = this.employmentStatusAdapter
				.findListOfEmployee(employeeIds, period).stream().collect(Collectors
						.toMap(EmploymentStatusImported::getEmployeeId, EmploymentStatusImported::getEmploymentInfo));

		// 労働条件惱を取得す�		// EA No1828
		// 社員ID(List)から労働条件を取得す�		List<WorkCondItemDto> listWorkingConItem = this.acquireWorkingConditionInformation(employeeIds, period);

		// 社員の短時間勤務履歴を取得す�		// 社員の短時間勤務履歴を期間で取得す�		// EA No2134
		List<ShortWorkTimeDto> listShortWorkTimeDto = this.scShortWorkTimeAdapter.findShortWorkTimes(employeeIds,
				period);

		// 「社員の予定管琊�態」を取得す��		// ToDo
		// 社員一覧のルー�		// 「パラメータ」�社員ID一覧・期間
		List<ScheManaStatuTempo> lstStatuTempos = new ArrayList<>();
		for (val id : employeeIds) {
			// 期間のルー�			for (val date : period.datesBetween()) {
				// 「社員の予定管琊�態」を取得す�				// 「Output」�社員の予定管琊�態一覧
				ScheManaStatuTempo.Require require = new ScheManaStatuTempoImpl(companyId, comHisAdapter,
						conditionRespo, empHisAdapter, leaHisAdapter, scheAdapter);
				ScheManaStatuTempo manaStatuTempo = ScheManaStatuTempo.create(require, id, date);
				lstStatuTempos.add(manaStatuTempo);
			}
		}
		// -----�
		// 勤務種類情報を取得す��		// EA修正履歴 No2282
		// ドメインモッ�「勤務種類」を取得す�		List<WorkType> lstWorkTypeInfo = workTypeRepository.findWorkByDeprecate(companyId,
				DeprecateClassification.NotDeprecated.value);
		// -----�		// 勤務種別をテク定期間�社員惱を�れて返す (Comment theo luồng của bác Bình)
		CreateScheduleMasterCache cache = new CreateScheduleMasterCache(empGeneralInfo, mapEmploymentStatus,
				listWorkingConItem, listShortWorkTimeDto, listBusTypeOfEmpHis, lstWorkTypeInfo, lstStatuTempos);

		// ドメインモッ�「勤務種類」を取得す�- 廭�区�廭�しな�		cache.getListWorkType().addAll(this.workTypeRepository.findNotDeprecateByCompanyId(companyId));
		// ドメインモッ�「就業時間帯の設定」を取得す�		cache.getListWorkTimeSetting().addAll(this.workTimeSettingRepository.findActiveItems(companyId));
		// EA修正履歴 No2103
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
		// ドメインモッ�「固定勤務設定」を取得す�		if (!listWorkTimeCodeFix.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixOffdayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixOffdayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFixHalfDayWorkRestTimezones = this.fixedWorkSettingRepository
					.getFixHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFix);
			this.setDataForMap(cache.getMapFixedWorkSetting(), mapFixOffdayWorkRestTimezones,
					mapFixHalfDayWorkRestTimezones);
		}
		// ドメインモッ�「流動勤務設定」を取得す�		if (!listWorkTimeCodeFlow.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowOffdayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowOffdayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapFlowHalfDayWorkRestTimezones = this.flowWorkSettingRepository
					.getFlowHalfDayWorkRestTimezones(companyId, listWorkTimeCodeFlow);
			this.setDataForMap(cache.getMapFlowWorkSetting(), mapFlowOffdayWorkRestTimezones,
					mapFlowHalfDayWorkRestTimezones);
		}
		// ドメインモッ�「時差勤務設定」を取得す�		if (!listWorkTimeCodeDiff.isEmpty()) {
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffOffdayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffOffdayWorkRestTimezones(companyId, listWorkTimeCodeDiff);
			Map<WorkTimeCode, List<AmPmWorkTimezone>> mapDiffHalfDayWorkRestTimezones = this.diffTimeWorkSettingRepository
					.getDiffHalfDayWorkRestTimezones(companyId, listWorkTimeCodeDiff);

			this.setDataForMap(cache.getMapDiffTimeWorkSetting(), mapDiffOffdayWorkRestTimezones,
					mapDiffHalfDayWorkRestTimezones);
		}
		// 取得した情報を返す
		return cache;
	}

	/**
	 * 労働条件惱を取得す�	 *
	 * @param sIds
	 * @param datePeriod
	 * @return
	 */
	private List<WorkCondItemDto> acquireWorkingConditionInformation(List<String> sIds, DatePeriod datePeriod) {
		// EA修正履歴 No1829
		// ドメインモッ�「労働条件」を取得す�		List<WorkingCondition> listWorkingCondition = this.workingConditionRepository.getBySidsAndDatePeriod(sIds,
				datePeriod);

		// ドメインモッ�「労働条件頛�」を取得す�		List<WorkingConditionItem> listWorkingConditionItem = this.workingConditionItemRepository
				.getBySidsAndDatePeriod(sIds, datePeriod);
		// 取得した労働条件と労働条件頛�を返す
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
	 * アルゴリズ�「対象期間を�ゖ�始日以降に補正する」を実行す�	 *
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

		// ドメインモッ�「雇用に紐づく就業�め」を取�		Optional<ClosureEmployment> optionalClosureEmployment = this.closureEmployment.findByEmploymentCD(companyId,
				optEmpHistItem.get().getEmploymentCode());
		if (!optionalClosureEmployment.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, StateValueDate.NO_TARGET_PERIOD); // false
		// ドメインモッ�「�め」を取�		Optional<Closure> optionalClosure = this.closureRepository.findById(companyId,
				optionalClosureEmployment.get().getClosureId());
		if (!optionalClosure.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, StateValueDate.NO_TARGET_PERIOD); // false
		// アルゴリズ�「当月の期間を算�する」を実�		DatePeriod dateP = ClosureService.getClosurePeriod(optionalClosure.get().getClosureId().value,
				optionalClosure.get().getClosureMonth().getProcessingYm(), optionalClosure);
		// Input「対象開始日」と、取得した「開始年月日」を比�		DatePeriod dateAfterCorrection = dateBeforeCorrection;
		if (dateBeforeCorrection.start().before(dateP.start())) {
			dateAfterCorrection = dateBeforeCorrection.cutOffWithNewStart(dateP.start());
		}
		// Output「対象開始日(補正�」に、取得した「�め期� 開始日年月日」を設定す�		if (dateAfterCorrection.start().beforeOrEquals(dateBeforeCorrection.end())) {
			// Out「対象終亗�(補正�」に、Input「対象終亗�」を設定す�			dateAfterCorrection = dateAfterCorrection.cutOffWithNewEnd(dateBeforeCorrection.end());
			return new StateAndValueDatePeriod(dateAfterCorrection, StateValueDate.TARGET_PERIOD); // true
		}

		return new StateAndValueDatePeriod(dateAfterCorrection, StateValueDate.NO_TARGET_PERIOD); // false
	}

	@AllArgsConstructor
	public static class ScheManaStatuTempoImpl implements ScheManaStatuTempo.Require {
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
	}
}