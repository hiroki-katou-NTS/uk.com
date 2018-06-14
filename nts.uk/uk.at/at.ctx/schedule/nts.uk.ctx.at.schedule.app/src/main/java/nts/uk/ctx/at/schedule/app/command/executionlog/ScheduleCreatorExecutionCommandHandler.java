/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicScheduleResetCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicScheduleHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeMonthlyPatternHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTypeHandler;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentStatusImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.ScEmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * The Class ScheduleCreatorExecutionCommandHandler.
 */
@Stateless
public class ScheduleCreatorExecutionCommandHandler extends AsyncCommandHandler<ScheduleCreatorExecutionCommand> {

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

	/** The sche cre exe work type handler. */
	@Inject
	private ScheCreExeWorkTypeHandler scheCreExeWorkTypeHandler;

	/** The sche cre exe basic schedule handler. */
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;

	@Inject
	private ScheCreExeMonthlyPatternHandler scheCreExeMonthlyPatternHandler;

	/** The create content repository. */
	@Inject
	private ScheduleCreateContentRepository scheduleCreateContentRepository;

	@Inject
	private I18NResourcesForUK internationalization;

	@Inject
	private ClosureEmploymentRepository closureEmployment;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ClosureService closureService;

	@Inject
	private ScEmployeeGeneralInfoAdapter scEmpGeneralInfoAdapter;

	@Inject
	private EmploymentStatusAdapter employmentStatusAdapter;

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

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
	// 入社前
	public static final int BEFORE_JOINING = 4;

	/** The Constant RETIREMENT. */
	// 退職
	public static final int RETIREMENT = 6;

	/** The Constant INCUMBENT. */
	// 在職
	public static final int INCUMBENT = 1;

	/** The Constant LEAVE_OF_ABSENCE. */
	// 休職
	public static final int LEAVE_OF_ABSENCE = 2;

	/** The Constant HOLIDAY. */
	// 休業
	public static final int HOLIDAY = 3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.AsyncCommandHandler#handle(nts.arc.layer.app.
	 * command.CommandHandlerContext)
	 */
	@Override
	public void handle(CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		ScheduleCreatorExecutionCommand command = context.getCommand();

		if (!command.isAutomatic()) {
			ScheduleExecutionLog scheduleExecutionLog = new ScheduleExecutionLog();

			// update command
			command.setCompanyId(companyId);
			command.setIsDeleteBeforInsert(false);

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

			command.setConfirm(scheCreContent.getConfirm());
			// register personal schedule

			this.registerPersonalSchedule(command, scheduleExecutionLog, context);
			return;
		}

		ScheduleExecutionLog scheduleExecutionLogAuto = ScheduleExecutionLog.creator(companyId,
				command.getScheduleExecutionLog().getExecutionId(), loginUserContext.employeeId(),
				command.getScheduleExecutionLog().getPeriod(), command.getScheduleExecutionLog().getExeAtr());
		this.registerPersonalSchedule(command, scheduleExecutionLogAuto, context);
	}

	/**
	 * Next day.
	 *
	 * @param day
	 *            the day
	 * @return the general date
	 */
	public GeneralDate nextDay(GeneralDate day) {
		return day.addDays(NEXT_DAY_MONTH);
	}

	/**
	 * Reset schedule.
	 *
	 * @param command
	 *            the command
	 * @param creator
	 *            the creator
	 * @param domain
	 *            the domain
	 */
	// スケジュールを再設定する
	private void resetSchedule(BasicScheduleResetCommand command,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod dateAfterCorrection) {

		// get info by context
		val asyncTask = context.asAsync();
		GeneralDate toDate = dateAfterCorrection.start();

		// loop start period date => end period date
		while (toDate.beforeOrEquals(dateAfterCorrection.end())) {

			// check is client submit cancel
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				break;
			}
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(command.getEmployeeId(),
					toDate);
			if (optionalBasicSchedule.isPresent()) {

				command.setWorkingCode(optionalBasicSchedule.get().getWorkTimeCode());
				command.setWorkTypeCode(optionalBasicSchedule.get().getWorkTypeCode());

				if (command.getReCreateAtr() == ReCreateAtr.ALL_CASE.value
						|| optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.UNSETTLED) {
					this.scheCreExeBasicScheduleHandler.resetAllDataToCommandSave(command, toDate);
				}
			}
			toDate = this.nextDay(toDate);
		}
	}

	/**
	 * Update status schedule execution log.
	 *
	 * @param domain
	 *            the domain
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
	 * @param domain
	 *            the domain
	 */
	private void updateStatusScheduleExecutionLog(ScheduleExecutionLog domain, CompletionStatus completionStatus) {
		// check exist data schedule error log
		domain.setCompletionStatus(completionStatus);
		domain.updateExecutionTimeEndToNow();
		this.scheduleExecutionLogRepository.update(domain);
	}

	/**
	 * 個人情報をもとにスケジュールを作成する-Creates the schedule based person.
	 * 
	 * @param command
	 * @param creator
	 * @param domain
	 * @param context
	 * @param dateAfterCorrection
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 */
	private void createScheduleBasedPerson(ScheduleCreatorExecutionCommand command, ScheduleCreator creator,
			ScheduleExecutionLog domain, CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod dateAfterCorrection, EmployeeGeneralInfoImported empGeneralInfo,
			Map<String, List<EmploymentInfoImported>> mapEmploymentStatus, List<WorkCondItemDto> listWorkingConItem) {

		// get info by context
		val asyncTask = context.asAsync();

		// get to day by start period date
		command.setToDate(dateAfterCorrection.start());

		// loop start period date => end period date
		while (command.getToDate().beforeOrEquals(dateAfterCorrection.end())) {

			// check is client submit cancel ［中断］(Interrupt)
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				// ドメインモデル「スケジュール作成実行ログ」を更新する(update domain 「スケジュール作成実行ログ」)
				this.updateStatusScheduleExecutionLog(domain, CompletionStatus.INTERRUPTION);
				break;
			}

			// 労働条件情報からパラメータ.社員ID、ループ中の対象日から該当する労働条件項目を取得する
			// EA修正履歴 No1830
			Optional<WorkCondItemDto> workingConditionItem = listWorkingConItem.stream()
					.filter(x -> x.getDatePeriod().contains(command.getToDate())
							&& creator.getEmployeeId().equals(x.getEmployeeId()))
					.findFirst();

			if (!workingConditionItem.isPresent()) {
				String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
				// ドメインモデル「スケジュール作成エラーログ」を登録する
				ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
						command.getToDate(), creator.getEmployeeId());
				this.scheduleErrorLogRepository.add(scheduleErrorLog);
			}

			if (workingConditionItem.isPresent()
					&& workingConditionItem.get().getScheduleManagementAtr() == ManageAtr.USE
					&& workingConditionItem.get().getScheduleMethod().isPresent()) {
				if (workingConditionItem.get().getScheduleMethod().get()
						.getBasicCreateMethod() == WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR) {
					// アルゴリズム「営業日カレンダーで勤務予定を作成する」を実行する
					this.createWorkScheduleByBusinessDayCalenda(command, workingConditionItem.get(), empGeneralInfo,
							mapEmploymentStatus, listWorkingConItem);
				} else if (workingConditionItem.get().getScheduleMethod().get()
						.getBasicCreateMethod() == WorkScheduleBasicCreMethod.MONTHLY_PATTERN) {
					// アルゴリズム「月間パターンで勤務予定を作成する」を実行する
					// create schedule by monthly pattern
					this.scheCreExeMonthlyPatternHandler.createScheduleWithMonthlyPattern(command,
							workingConditionItem.get(), empGeneralInfo, mapEmploymentStatus, listWorkingConItem);
				} else {
					// アルゴリズム「個人曜日別で勤務予定を作成する」を実行する
					// TODO
				}
			}

			command.setToDate(this.nextDay(command.getToDate()));
		}

	}

	/**
	 * 営業日カレンダーで勤務予定を作成する
	 * 
	 * Creates the work schedule by business day calendar.
	 * 
	 * @param command
	 * @param workingConditionItem
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 */
	private void createWorkScheduleByBusinessDayCalenda(ScheduleCreatorExecutionCommand command,
			WorkCondItemDto workingConditionItem, EmployeeGeneralInfoImported empGeneralInfo,
			Map<String, List<EmploymentInfoImported>> mapEmploymentStatus, List<WorkCondItemDto> listWorkingConItem) {
		// 「社員の在職状態」から該当社員、該当日の在職状態を取得する
		// EA No1689
		List<EmploymentInfoImported> listEmploymentInfo = mapEmploymentStatus.get(workingConditionItem.getEmployeeId());
		Optional<EmploymentInfoImported> optEmploymentInfo = Optional.empty();
		if (listEmploymentInfo != null) {
			optEmploymentInfo = listEmploymentInfo.stream()
					.filter(employmentInfo -> employmentInfo.getStandardDate().equals(command.getToDate())).findFirst();
		}

		// status employment equal RETIREMENT (退職)
		if (!optEmploymentInfo.isPresent() || optEmploymentInfo.get().getEmploymentState() == RETIREMENT) {
			return;
		}

		// status employment not equal BEFORE_JOINING (入社前)
		if (optEmploymentInfo.get().getEmploymentState() != BEFORE_JOINING) {
			// ドメインモデル「勤務予定基本情報」を取得する(lấy dữ liệu domain 「勤務予定基本情報」)
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
					.find(workingConditionItem.getEmployeeId(), command.getToDate());

			if (optionalBasicSchedule.isPresent()) {
				BasicSchedule basicSchedule = optionalBasicSchedule.get();

				// 登録前削除区分をTrue（削除する）とする
				command.setIsDeleteBeforInsert(true); // FIX BUG #87113
				// check parameter implementAtr recreate (入力パラメータ「実施区分」を判断)
				// 入力パラメータ「実施区分」を判断(kiểm tra parameter 「実施区分」)
				if (command.getContent().getImplementAtr().value == ImplementAtr.RECREATE.value) {
					this.createWorkScheduleByRecreate(command, basicSchedule, workingConditionItem, optEmploymentInfo,
							empGeneralInfo, mapEmploymentStatus, listWorkingConItem);
				}
			} else {
				// EA No1841
				ScheMasterInfo scheMasterInfo = new ScheMasterInfo(null);
				BasicSchedule basicSche = new BasicSchedule(null, scheMasterInfo);
				if (ImplementAtr.RECREATE == command.getContent().getImplementAtr()
						&& !this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command,
								basicSche, optEmploymentInfo, workingConditionItem, empGeneralInfo)) {
					return;
				}

				// 登録前削除区分をTrue（削除する）とする
				command.setIsDeleteBeforInsert(false); // FIX BUG #87113

				// not exist data basic schedule
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, workingConditionItem, empGeneralInfo,
						mapEmploymentStatus, listWorkingConItem);
			}
		}
	}

	/**
	 * Creates the work schedule by recreate.
	 * 
	 * @param command
	 * @param basicSchedule
	 * @param workingConditionItem
	 * @param optEmploymentInfo
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 */
	private void createWorkScheduleByRecreate(ScheduleCreatorExecutionCommand command, BasicSchedule basicSchedule,
			WorkCondItemDto workingConditionItem, Optional<EmploymentInfoImported> optEmploymentInfo,
			EmployeeGeneralInfoImported empGeneralInfo, Map<String, List<EmploymentInfoImported>> mapEmploymentStatus,
			List<WorkCondItemDto> listWorkingConItem) {
		// 入力パラメータ「再作成区分」を判断 - check parameter ReCreateAtr onlyUnconfirm
		// 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
		// (kiểm tra thông tin 「予定確定区分」 của domain 「勤務予定基本情報」)
		if (command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ALL_CASE
				|| basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {
			// アルゴリズム「スケジュール作成判定処理」を実行する
			if (this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command, basicSchedule,
					optEmploymentInfo, workingConditionItem, empGeneralInfo)) {
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, workingConditionItem, empGeneralInfo,
						mapEmploymentStatus, listWorkingConItem);
			}
		}
	}

	/**
	 * 個人スケジュールを登録する: register Personal Schedule
	 * 
	 * @param command
	 * @param scheduleExecutionLog
	 * @param context
	 */
	private void registerPersonalSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog, CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {

		String exeId = command.getExecutionId();

		// パラメータ実施区分を判定 (phán đoán param 実施区分 )
		if (scheduleExecutionLog.getExeAtr() == ExecutionAtr.AUTOMATIC) {
			ScheduleCreateContent scheduleCreateContent = command.getContent();
			List<ScheduleCreator> scheduleCreators = command.getEmployeeIds().stream()
					.map(sId -> new ScheduleCreator(exeId, ExecutionStatus.NOT_CREATED, sId))
					.collect(Collectors.toList());
			// アルゴリズム「実行ログ作成処理」を実行する
			this.executionLogCreationProcess(scheduleExecutionLog, scheduleCreateContent, scheduleCreators);
		}

		DatePeriod dateBeforeCorrection = new DatePeriod(scheduleExecutionLog.getPeriod().start(),
				scheduleExecutionLog.getPeriod().end());

		// get all data creator
		List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository.findAll(exeId);
		List<String> employeeIds = scheduleCreators.stream().map(item -> item.getEmployeeId())
				.collect(Collectors.toList());
		// EA No1675
		// Imported(就業)「社員の履歴情報」を取得する
		EmployeeGeneralInfoImported empGeneralInfo = this.scEmpGeneralInfoAdapter.getPerEmpInfo(employeeIds,
				scheduleExecutionLog.getPeriod());
		// Imported(就業)「社員の在職状態」を取得する
		List<EmploymentStatusImported> listEmploymentStatus = this.employmentStatusAdapter
				.findListOfEmployee(employeeIds, dateBeforeCorrection);
		Map<String, List<EmploymentInfoImported>> mapEmploymentStatus = listEmploymentStatus.stream().collect(
				Collectors.toMap(EmploymentStatusImported::getEmployeeId, EmploymentStatusImported::getEmploymentInfo));

		// 労働条件情報を取得する
		// EA No1828
		List<WorkCondItemDto> listWorkingConItem = this.acquireWorkingConditionInformation(employeeIds,
				dateBeforeCorrection);

		// get info by context
		val asyncTask = context.asAsync();

		for (ScheduleCreator scheduleCreator : scheduleCreators) {

			// check is client submit cancel
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				// ドメインモデル「スケジュール作成実行ログ」を更新する(update domain 「スケジュール作成実行ログ」)
				this.updateStatusScheduleExecutionLog(scheduleExecutionLog, CompletionStatus.INTERRUPTION);
				break;
			}
			// アルゴリズム「対象期間を締め開始日以降に補正する」を実行する
			StateAndValueDatePeriod stateAndValueDatePeriod = this.correctTargetPeriodAfterClosingStartDate(
					command.getCompanyId(), scheduleCreator.getEmployeeId(), dateBeforeCorrection, empGeneralInfo);
			if (!stateAndValueDatePeriod.state)
				continue;

			DatePeriod dateAfterCorrection = stateAndValueDatePeriod.getValue();

			// パラメータ.処理実行区分を判断-check processExecutionAtr reconfig
			if (command.getContent().getReCreateContent().getProcessExecutionAtr() == ProcessExecutionAtr.RECONFIG) {
				BasicScheduleResetCommand commandReset = new BasicScheduleResetCommand();
				commandReset.setCompanyId(command.getCompanyId());
				commandReset.setConfirm(command.getContent().getConfirm());
				commandReset.setEmployeeId(scheduleCreator.getEmployeeId());
				commandReset.setExecutionId(exeId);
				commandReset.setReCreateAtr(command.getContent().getReCreateContent().getReCreateAtr().value);
				commandReset.setResetAtr(command.getContent().getReCreateContent().getResetAtr());
				commandReset.setTargetStartDate(scheduleExecutionLog.getPeriod().start());
				commandReset.setTargetEndDate(scheduleExecutionLog.getPeriod().end());
				// スケジュールを再設定する (Thiết lập lại schedule)
				this.resetSchedule(commandReset, context, dateAfterCorrection);
			} else {
				// 入力パラメータ「作成方法区分」を判断-check parameter CreateMethodAtr
				if (command.getContent().getCreateMethodAtr() == CreateMethodAtr.PERSONAL_INFO) {
					this.createScheduleBasedPerson(command, scheduleCreator, scheduleExecutionLog, context,
							dateAfterCorrection, empGeneralInfo, mapEmploymentStatus, listWorkingConItem);
				}
			}
			scheduleCreator.updateToCreated();
			this.scheduleCreatorRepository.update(scheduleCreator);
		}

		// find execution log by id
		ScheduleExecutionLog scheExeLog = this.scheduleExecutionLogRepository
				.findById(command.getCompanyId(), scheduleExecutionLog.getExecutionId()).get();
		if (scheExeLog.getCompletionStatus() != CompletionStatus.INTERRUPTION) {
			System.out.println("not hasBeenRequestedToCancel: " + asyncTask.hasBeenRequestedToCancel() + "&exeid="
					+ scheduleExecutionLog.getExecutionId());
			this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
		}
	}

	/**
	 * 実行ログ作成処理
	 * 
	 * @param scheduleExecutionLog
	 * @param scheduleCreateContent
	 * @param scheduleCreators
	 */
	private void executionLogCreationProcess(ScheduleExecutionLog scheduleExecutionLog,
			ScheduleCreateContent scheduleCreateContent, List<ScheduleCreator> scheduleCreators) {
		// ドメインモデル「スケジュール作成実行ログ」を新規登録する
		this.scheduleExecutionLogRepository.add(scheduleExecutionLog);
		// ドメインモデル「スケジュール作成内容」を新規登録する
		this.scheduleCreateContentRepository.add(scheduleCreateContent);
		// ドメインモデル「スケジュール作成対象者」を新規登録する
		this.scheduleCreatorRepository.saveAll(scheduleCreators);
	}

	/**
	 * アルゴリズム「対象期間を締め開始日以降に補正する」を実行する
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
			if (!optEmpHistItem.isPresent()) {
				return new StateAndValueDatePeriod(dateBeforeCorrection, false);
			}
		}

		// ドメインモデル「雇用に紐づく就業締め」を取得
		Optional<ClosureEmployment> optionalClosureEmployment = this.closureEmployment.findByEmploymentCD(companyId,
				optEmpHistItem.get().getEmploymentCode());
		if (!optionalClosureEmployment.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, false);
		// ドメインモデル「締め」を取得
		Optional<Closure> optionalClosure = this.closureRepository.findById(companyId,
				optionalClosureEmployment.get().getClosureId());
		if (!optionalClosure.isPresent())
			return new StateAndValueDatePeriod(dateBeforeCorrection, false);
		// アルゴリズム「当月の期間を算出する」を実行
		DatePeriod dateP = this.closureService.getClosurePeriod(optionalClosure.get().getClosureId().value,
				optionalClosure.get().getClosureMonth().getProcessingYm());
		// Input「対象開始日」と、取得した「開始年月日」を比較
		DatePeriod dateAfterCorrection = dateBeforeCorrection;
		if (dateBeforeCorrection.start().before(dateP.start())) {
			dateAfterCorrection = dateBeforeCorrection.cutOffWithNewStart(dateP.start());
		}
		// Output「対象開始日(補正後)」に、取得した「締め期間. 開始日年月日」を設定する
		if (dateAfterCorrection.start().beforeOrEquals(dateBeforeCorrection.end())) {
			// Out「対象終了日(補正後)」に、Input「対象終了日」を設定する
			dateAfterCorrection = dateAfterCorrection.cutOffWithNewEnd(dateBeforeCorrection.end());
			return new StateAndValueDatePeriod(dateAfterCorrection, true);
		}

		return new StateAndValueDatePeriod(dateAfterCorrection, false);
	}

	/**
	 * 労働条件情報を取得する
	 * 
	 * @param sIds
	 * @param datePeriod
	 * @return
	 */
	private List<WorkCondItemDto> acquireWorkingConditionInformation(List<String> sIds, DatePeriod datePeriod) {
		// EA修正履歴 No1829
		List<WorkingCondition> listWorkingCondition = this.workingConditionRepository.getBySidsAndDatePeriod(sIds,
				datePeriod);

		List<WorkingConditionItem> listWorkingConditionItem = this.workingConditionItemRepository
				.getBySidsAndDatePeriod(sIds, datePeriod);
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

}