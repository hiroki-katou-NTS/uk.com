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
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTimeHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTypeHandler;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleBasicCreMethod;
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
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
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
	
	/** The cre set repository. */
	@Inject
	private PersonalWorkScheduleCreSetRepository creSetRepository;
	
	/** The content repository. */
	@Inject
	private ScheduleCreateContentRepository contentRepository;
		
	/** The personal labor condition repository. */
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
		
	/** The sche cre exe work time handler. */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	
	/** The sche cre exe work type handler. */
	@Inject
	private ScheCreExeWorkTypeHandler scheCreExeWorkTypeHandler;
	
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
		command.setIsConfirm(false);
		command.setIsDeleteBeforInsert(false);

		// find execution log by id
		Optional<ScheduleExecutionLog> optionalScheduleExecutionLog = this.scheduleExecutionLogRepository
				.findById(companyId, command.getExecutionId());

		// check exist data
		if (optionalScheduleExecutionLog.isPresent()) {
			
			// update execution time to now
			ScheduleExecutionLog domain = optionalScheduleExecutionLog.get();
			domain.setExecutionTimeToNow();
			
			// update domain execution log
			this.scheduleExecutionLogRepository.update(domain);
			
			// find execution content by id
			Optional<ScheduleCreateContent> optionalContent = this.contentRepository
					.findByExecutionId(command.getExecutionId());

			// check exist data content 
			if (optionalContent.isPresent()) {
				command.setContent(optionalContent.get());
			}

			// get all data creator
			List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository.findAll(command.getExecutionId());

			// register personal schedule
			this.registerPersonalSchedule(command, domain, scheduleCreators, context);
		}

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
			ScheduleExecutionLog scheduleExecutionLog, List<ScheduleCreator> scheduleCreators,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		val asyncTask = context.asAsync();
		for (ScheduleCreator domain : scheduleCreators) {
			
			// check is client submit cancel
			if (asyncTask.hasBeenRequestedToCancel()) {
				
				asyncTask.finishedAsCancelled();
				break;
			}
			
			// check processExecutionAtr reconfig
			if (command.getContent().getReCreateContent().getProcessExecutionAtr() == ProcessExecutionAtr.RECONFIG) {
				this.resetSchedule(command, domain, scheduleExecutionLog);
			} else {

				// check parameter CreateMethodAtr
				if (command.getContent().getCreateMethodAtr() == CreateMethodAtr.PERSONAL_INFO) {
					this.createScheduleBasedPerson(command, domain, scheduleExecutionLog);
				}
			}
			
			domain.updateToCreated();
			this.scheduleCreatorRepository.update(domain);
		}
		this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
	}
	
	/**
	 * Reset schedule.
	 *
	 * @param command the command
	 * @param creator the creator
	 * @param domain the domain
	 */
	// スケジュールを再設定する
	private void resetSchedule(ScheduleCreatorExecutionCommand command, ScheduleCreator creator,
			ScheduleExecutionLog domain) {
		// get to day by start period date
		command.setToDate(domain.getPeriod().start());

		// loop start period date => end period date
		while (command.getToDate().beforeOrEquals(domain.getPeriod().end())) {

			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
					.find(creator.getEmployeeId(), command.getToDate());
			if (optionalBasicSchedule.isPresent()
					&& command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ONLY_UNCONFIRM
					&& optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.CONFIRMED) {
			}
			// (button Interrupt)
			command.setToDate(this.nextDay(command.getToDate()));
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
	private void createScheduleBasedPerson(ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator, ScheduleExecutionLog domain) {
		Optional<PersonalWorkScheduleCreSet> optionalPersonalWorkScheduleCreSet = this.creSetRepository
				.findById(creator.getEmployeeId());

		// check exist data PersonalWorkScheduleCreSet
		if (optionalPersonalWorkScheduleCreSet.isPresent()) {
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet = optionalPersonalWorkScheduleCreSet
					.get();

			// check domain WorkScheduleBasicCreMethod is BUSINESS_DAY_CALENDAR
			if (personalWorkScheduleCreSet.getBasicCreateMethod()
					.equals(WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR)) {
				// call create WorkSchedule
				this.createWorkScheduleByBusinessDayCalenda(command, domain,
						personalWorkScheduleCreSet);
			}
		}
	}
	
	/**
	 * Creates the work schedule by business day calenda.
	 *
	 * @param command the command
	 * @param domain the domain
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 */
	// 営業日カレンダーで勤務予定を作成する
	private void createWorkScheduleByBusinessDayCalenda(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog domain, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// get to day by start period date
		command.setToDate(domain.getPeriod().start());

		// loop start period date => end period date
		while (command.getToDate().beforeOrEquals(domain.getPeriod().end())) {

			Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
					.findById(personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());

			// check is use manager
			if (optionalPersonalLaborCondition.isPresent()
					&& optionalPersonalLaborCondition.get().isUseScheduleManagement()) {
				if (this.createWorkScheduleByBusinessDayCalendaUseManager(command, domain,
						personalWorkScheduleCreSet)) {
					return;
				}
			}

			// (button Interrupt)
			command.setToDate(this.nextDay(command.getToDate()));
		}
	}
	
	/**
	 * Creates the work schedule by business day calenda use manager.
	 *
	 * @param command the command
	 * @param domain the domain
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return true, if successful
	 */
	private boolean createWorkScheduleByBusinessDayCalendaUseManager(
			ScheduleCreatorExecutionCommand command, ScheduleExecutionLog domain,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		// get status employment
		EmploymentStatusDto employmentStatus = this.scheCreExeWorkTimeHandler.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());

		// status employment equal RETIREMENT (退職)
		if (employmentStatus.getStatusOfEmployment() == RETIREMENT) {
			return true;
		}

		// status employment not equal BEFORE_JOINING (入社前)
		if (employmentStatus.getStatusOfEmployment() != BEFORE_JOINING) {
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(
					personalWorkScheduleCreSet.getEmployeeId(),
					command.getToDate());

			// check exist data basic schedule
			if (optionalBasicSchedule.isPresent()) {

				BasicSchedule basicSchedule = optionalBasicSchedule.get();
				this.createWorkScheduleByImplementAtr(command, basicSchedule,
						personalWorkScheduleCreSet);
			} else {
				// not exist data basic schedule
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, personalWorkScheduleCreSet);
			}
		}
		return false;
	}
	
	/**
	 * Creates the work schedule by implement atr.
	 *
	 * @param command the command
	 * @param basicSchedule the basic schedule
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 */
	private void createWorkScheduleByImplementAtr(ScheduleCreatorExecutionCommand command,
			BasicSchedule basicSchedule, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// check parameter implementAtr recreate
		if (command.getContent().getImplementAtr().value == ImplementAtr.RECREATE.value) {
			this.createWorkScheduleByRecreate(command, basicSchedule, personalWorkScheduleCreSet);
		}
	}
	
	/**
	 * Creates the work schedule by recreate.
	 *
	 * @param command the command
	 * @param basicSchedule the basic schedule
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 */
	private void createWorkScheduleByRecreate(ScheduleCreatorExecutionCommand command,
			BasicSchedule basicSchedule, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// check parameter ReCreateAtr onlyUnconfirm
		if (command.getContent().getReCreateContent()
				.getReCreateAtr().value == ReCreateAtr.ONLY_UNCONFIRM.value) {

			// check confirmedAtr of basic schedule
			if (basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, personalWorkScheduleCreSet);
			}
		} else {
			this.scheCreExeWorkTypeHandler.createWorkSchedule(command, personalWorkScheduleCreSet);
		}
	}
	
}