/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicScheduleResetCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicScheduleHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeMonthlyPatternHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTimeHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTypeHandler;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
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
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;;

/**
 * The Class ScheduleCreatorExecutionCommandHandler.
 */
@Stateful
public class ScheduleCreatorExecutionCommandHandler
		extends AsyncCommandHandler<ScheduleCreatorExecutionCommand> {
		
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
		
	/** The sche cre exe work time handler. */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	
	/** The sche cre exe work type handler. */
	@Inject
	private ScheCreExeWorkTypeHandler scheCreExeWorkTypeHandler;
	
	/** The sche cre exe basic schedule handler. */
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;
	
	@Inject
	private ScheCreExeMonthlyPatternHandler scheCreExeMonthlyPatternHandler;
	
	
	/** The Constant DEFAULT_CODE. */
	public static final String DEFAULT_CODE = "000";
	
	/** The Constant NEXT_DAY_MONTH. */
	public static final int NEXT_DAY_MONTH = 1;
	
	/** The Constant ZERO_DAY_MONTH. */
	public static final int ZERO_DAY_MONTH = 0;
	
	
	/** The Constant MUL_YEAR. */
	public static final int MUL_YEAR = 10000;
	
	/** The Constant MUL_MONTH. */
	public static  final int MUL_MONTH = 100;
	
	/** The Constant SHIFT1. */
	public static  final int SHIFT1 = 1;
	
	/** The Constant SHIFT2. */
	public static  final int SHIFT2 = 2;
	
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

		// update command
		command.setCompanyId(companyId);
		command.setIsDeleteBeforInsert(false);

		// find execution log by id
		ScheduleExecutionLog domain = this.scheduleExecutionLogRepository.findById(companyId, command.getExecutionId())
				.get();

		// update execution time to now
		domain.setExecutionTimeToNow();

		// update domain execution log
		this.scheduleExecutionLogRepository.update(domain);

		// find execution content by id
		ScheduleCreateContent scheCreContent = this.contentRepository.findByExecutionId(command.getExecutionId()).get();
		command.setContent(scheCreContent);

		command.setConfirm(scheCreContent.getConfirm());
		// register personal schedule
		this.registerPersonalSchedule(command, domain, context);

	}

	/**
	 * Next day.
	 *
	 * @param day the day
	 * @return the general date
	 */
	public GeneralDate nextDay(GeneralDate day) {
		return day.addDays(NEXT_DAY_MONTH);
	}
	/**
	 * Register personal schedule.
	 *
	 * @param command the command
	 * @param scheduleExecutionLog the schedule execution log
	 * @param scheduleCreators the schedule creators
	 * @param context the context
	 */
	// 個人スケジュールを登録する
	private void registerPersonalSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {

		// get all data creator
		List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository.findAll(command.getExecutionId());

		// get info by context
		val asyncTask = context.asAsync();
		
		for (ScheduleCreator domain : scheduleCreators) {
			
			// check is client submit cancel
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				// ドメインモデル「スケジュール作成実行ログ」を更新する(update domain 「スケジュール作成実行ログ」)
				this.updateStatusScheduleExecutionLog(scheduleExecutionLog, CompletionStatus.INTERRUPTION);
				break;
			}
			
			// check processExecutionAtr reconfig
			if (command.getContent().getReCreateContent().getProcessExecutionAtr() == ProcessExecutionAtr.RECONFIG) {
				BasicScheduleResetCommand commandReset = new BasicScheduleResetCommand();
				commandReset.setCompanyId(command.getCompanyId());
				commandReset.setConfirm(command.getContent().getConfirm());
				commandReset.setEmployeeId(domain.getEmployeeId());
				commandReset.setExecutionId(command.getExecutionId());
				commandReset.setReCreateAtr(command.getContent().getReCreateContent().getReCreateAtr().value);
				commandReset.setResetAtr(command.getContent().getReCreateContent().getResetAtr());
				commandReset.setTargetStartDate(scheduleExecutionLog.getPeriod().start());
				commandReset.setTargetEndDate(scheduleExecutionLog.getPeriod().end());
				this.resetSchedule(commandReset, context);
			} else {

				// check parameter CreateMethodAtr
				if (command.getContent().getCreateMethodAtr() == CreateMethodAtr.PERSONAL_INFO) {
					this.createScheduleBasedPerson(command, domain, scheduleExecutionLog, context);
				}
			}
			
			domain.updateToCreated();
			this.scheduleCreatorRepository.update(domain);
		}
		
		// find execution log by id
		ScheduleExecutionLog scheExeLog = this.scheduleExecutionLogRepository.findById(
				command.getCompanyId(), scheduleExecutionLog.getExecutionId()).get();
		if (scheExeLog.getCompletionStatus() != CompletionStatus.INTERRUPTION) {
			System.out.println("not hasBeenRequestedToCancel: " + asyncTask.hasBeenRequestedToCancel() + "&exeid="+ scheduleExecutionLog.getExecutionId());
			this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
		}
	}
	
	/**
	 * Reset schedule.
	 *
	 * @param command the command
	 * @param creator the creator
	 * @param domain the domain
	 */
	// スケジュールを再設定する
	private void resetSchedule(BasicScheduleResetCommand command,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		
		// get info by context
		val asyncTask = context.asAsync();
		GeneralDate toDate = command.getTargetStartDate();

		// loop start period date => end period date
		while (toDate.beforeOrEquals(command.getTargetEndDate())) {

			// check is client submit cancel
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				break;
			}
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(command.getEmployeeId(),
					toDate);
			if (optionalBasicSchedule.isPresent()){
				
				command.setWorkingCode(optionalBasicSchedule.get().getWorkTypeCode());
				command.setWorkTypeCode(optionalBasicSchedule.get().getWorkTimeCode());
				
				if (command.getReCreateAtr() == ReCreateAtr.ALL_CASE.value) {
					this.scheCreExeBasicScheduleHandler.resetAllDataToCommandSave(command, toDate);
				}else if(optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.UNSETTLED){
					this.scheCreExeBasicScheduleHandler.resetAllDataToCommandSave(command, toDate);
				}
				
			}
			toDate = this.nextDay(toDate);
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
	 * Creates the schedule based person.
	 *
	 * @param command the command
	 * @param creator the creator
	 * @param domain the domain
	 */
	// 個人情報をもとにスケジュールを作成する
	private void createScheduleBasedPerson(ScheduleCreatorExecutionCommand command, ScheduleCreator creator,
			ScheduleExecutionLog domain, CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {

		// get info by context
		val asyncTask = context.asAsync();

		// get to day by start period date
		command.setToDate(domain.getPeriod().start());

		// loop start period date => end period date
		while (command.getToDate().beforeOrEquals(domain.getPeriod().end())) {

			// check is client submit cancel ［中断］(Interrupt)
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				// ドメインモデル「スケジュール作成実行ログ」を更新する(update domain 「スケジュール作成実行ログ」)
				this.updateStatusScheduleExecutionLog(domain, CompletionStatus.INTERRUPTION);
				break;
			}
			Optional<WorkingConditionItem> workingConditionItem = this.scheCreExeWorkTimeHandler
					.getLaborConditionItem(command.getCompanyId(), creator.getEmployeeId(), command.getToDate());

			// check is use manager
			if (workingConditionItem.isPresent()
					&& workingConditionItem.get().getScheduleManagementAtr() == ManageAtr.USE
					&& workingConditionItem.get().getScheduleMethod().isPresent()
					) {
				if (workingConditionItem.get().getScheduleMethod().get()
							.getBasicCreateMethod() == WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR) {
					this.createWorkScheduleByBusinessDayCalenda(command, domain, workingConditionItem.get(), context);
				} else if (workingConditionItem.get().getScheduleMethod().get()
						.getBasicCreateMethod() == WorkScheduleBasicCreMethod.MONTHLY_PATTERN) {
					//create schedule by monthly pattern
					this.scheCreExeMonthlyPatternHandler.createScheduleWithMonthlyPattern(command, workingConditionItem.get());
				} else {
					//TODO no something
				}
			}
			command.setToDate(this.nextDay(command.getToDate()));
		}
	}
	
	/**
	 * Creates the work schedule by business day calenda.
	 *
	 * @param command the command
	 * @param domain the domain
	 * @param workingConditionItem the working condition item
	 * @param context the context
	 */
	// 営業日カレンダーで勤務予定を作成する
	private void createWorkScheduleByBusinessDayCalenda(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog domain, WorkingConditionItem workingConditionItem,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		// get status employment
		EmploymentStatusDto employmentStatus = this.scheCreExeWorkTimeHandler
				.getStatusEmployment(workingConditionItem.getEmployeeId(), command.getToDate());

		// status employment equal RETIREMENT (退職)
		if (employmentStatus.getStatusOfEmployment() == RETIREMENT) {
			return;
		}

		// status employment not equal BEFORE_JOINING (入社前)
		if (employmentStatus.getStatusOfEmployment() != BEFORE_JOINING) {
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
					.find(workingConditionItem.getEmployeeId(), command.getToDate());

			// check exist data basic schedule
			if (optionalBasicSchedule.isPresent()) {
				BasicSchedule basicSchedule = optionalBasicSchedule.get();
				
				// 登録前削除区分をTrue（削除する）とする
				command.setIsDeleteBeforInsert(true); // FIX BUG #87113
				// check parameter implementAtr recreate (入力パラメータ「実施区分」を判断)
				if (command.getContent().getImplementAtr().value == ImplementAtr.RECREATE.value) {
					this.createWorkScheduleByRecreate(command, basicSchedule, workingConditionItem);
				}
			} else {
				// 登録前削除区分をTrue（削除する）とする
				command.setIsDeleteBeforInsert(false); // FIX BUG #87113
				
				// not exist data basic schedule
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, workingConditionItem);
			}
		}
	}
	
	/**
	 * Creates the work schedule by recreate.
	 *
	 * @param command the command
	 * @param basicSchedule the basic schedule
	 * @param workingConditionItem the working condition item
	 */
	private void createWorkScheduleByRecreate(ScheduleCreatorExecutionCommand command, BasicSchedule basicSchedule,
			WorkingConditionItem workingConditionItem) {

		// 入力パラメータ「再作成区分」を判断
		// check parameter ReCreateAtr onlyUnconfirm 
		if (command.getContent().getReCreateContent().getReCreateAtr().value == ReCreateAtr.ONLY_UNCONFIRM.value) {//［未確定データのみ］
			// 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
			// check confirmedAtr of basic schedule
			if (basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {//未確定
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, workingConditionItem);
			}
		} else {
			this.scheCreExeWorkTypeHandler.createWorkSchedule(command, workingConditionItem);
		}
	}

}