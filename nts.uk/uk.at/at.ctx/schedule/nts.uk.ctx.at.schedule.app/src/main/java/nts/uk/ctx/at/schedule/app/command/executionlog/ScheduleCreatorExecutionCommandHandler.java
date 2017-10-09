/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ScheduleCreatorExecutionCommandHandler.
 */
@Stateful
public class ScheduleCreatorExecutionCommandHandler
		extends AsyncCommandHandler<ScheduleCreatorExecutionCommand> {

	/** The schedule execution log repository. */
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	/** The finder. */
	@Inject
	private ScheduleExecutionLogFinder finder;

	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;

	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;
	
	/** The personal work schedule cre set repository. */
	@Inject
	private PersonalWorkScheduleCreSetRepository personalWorkScheduleCreSetRepository;
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	/** The setter. */
	private TaskDataSetter setter;

	/** The default value. */
	private static final Integer DEFAULT_VALUE = 0;

	/** The total record. */
	private static final String TOTAL_RECORD = "TOTAL_RECORD";

	/** The success cnt. */
	private static final String SUCCESS_CNT = "SUCCESS_CNT";

	/** The fail cnt. */
	private static final String FAIL_CNT = "FAIL_CNT";
	
	/** The Constant NEXT_DAY_MONT. */
	public static final int NEXT_DAY_MONTH = 1;
	
	/** The Constant ZERO_DAY_MONT. */
	public static final int ZERO_DAY_MONTH = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.AsyncCommandHandler#handle(nts.arc.layer.app.
	 * command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		val asyncTask = context.asAsync();
		setter = asyncTask.getDataSetter();
		ScheduleCreatorExecutionCommand command = context.getCommand();

		String executionId = command.getExecutionId();

		Optional<ScheduleExecutionLog> optionalScheduleExecutionLog = this.scheduleExecutionLogRepository
				.findById(companyId, executionId);

		// check exist data
		if (optionalScheduleExecutionLog.isPresent()) {
			ScheduleExecutionLog domain = optionalScheduleExecutionLog.get();
			domain.setExecutionDateTime(
					new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now()));
			this.scheduleExecutionLogRepository.update(domain);
			List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository
					.findAll(executionId);

			setter.setData(TOTAL_RECORD, scheduleCreators.size());
			setter.setData(SUCCESS_CNT, DEFAULT_VALUE);
			setter.setData(FAIL_CNT, DEFAULT_VALUE);

			this.registerPersonalSchedule(domain, scheduleCreators);
		}

	}

	/**
	 * Register personal schedule.
	 *
	 * @param scheduleExecutionLog the schedule execution log
	 * @param scheduleCreators the schedule creators
	 */
	// 個人スケジュールを登録する
	private void registerPersonalSchedule(ScheduleExecutionLog scheduleExecutionLog,
			List<ScheduleCreator> scheduleCreators) {
		scheduleCreators.forEach(domain -> {
			domain.updateToCreated();
			this.scheduleCreatorRepository.update(domain);
			setter.updateData(SUCCESS_CNT, this.finder
					.findInfoById(scheduleExecutionLog.getExecutionId()).getTotalNumberCreated());
		});
		this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
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
	 * @param domain the domain
	 */
	// 個人情報をもとにスケジュールを作成する
	private void createScheduleBasedPerson(ScheduleExecutionLog domain,ScheduleCreatorExecutionCommand command) {
		Optional<PersonalWorkScheduleCreSet> optionalPersonalWorkScheduleCreSet = this.personalWorkScheduleCreSetRepository
				.findById(domain.getExecutionEmployeeId());
		
		// check exist data PersonalWorkScheduleCreSet
		if (optionalPersonalWorkScheduleCreSet.isPresent()) {
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet = optionalPersonalWorkScheduleCreSet.get();
			
			// check domain WorkScheduleBasicCreMethod is BUSINESS_DAY_CALENDAR
			if (personalWorkScheduleCreSet.getBasicCreateMethod()
					.equals(WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR)) {
				// call create WorkSchedule
				this.createWorkScheduleByBusinessDayCalenda(domain, command);
			}
		}
	}
	
	/**
	 * Creates the work schedule by business day calenda.
	 *
	 * @param domain the domain
	 */
	// 営業日カレンダーで勤務予定を作成する
	private void createWorkScheduleByBusinessDayCalenda(ScheduleExecutionLog domain,ScheduleCreatorExecutionCommand command){
		
		// get to day by start period date 
		Date toDate = domain.getPeriod().getStartDate().date();
		
		// loop start period date => end period date
		while(toDate.before(this.nextDay(domain.getPeriod().getEndDate().date()))){
			
			// get status employment
			if(getStatusEmployment(domain)){
				Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
						.find(domain.getExecutionEmployeeId(), GeneralDate.legacyDate(toDate));
				
				// check exist data basic schedule
				if(optionalBasicSchedule.isPresent()){
					
					BasicSchedule basicSchedule = optionalBasicSchedule.get();
					// check parameter implementAtr recreate
					if (command.getImplementAtr() == ImplementAtr.RECREATE.value) {
						
						// check parameter ReCreateAtr onlyUnconfirm
						if(command.getReCreateAtr() == ReCreateAtr.ONLYUNCONFIRM.value){
							
							// check confirmedAtr of basic schedule
							if(basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)){
								
								
							}
						}
					}
				}
			}
			// (button Interrupt)
			toDate = this.nextDay(toDate);
		}
	}
	
	/**
	 * Gets the status employment.
	 *
	 * @param domain the domain
	 * @return the status employment
	 */
	// アルゴリズム
	private boolean getStatusEmployment(ScheduleExecutionLog domain){
		// fake data
		return true;
	}
	/**
	 * Next day.
	 *
	 * @param day the day
	 * @return the date
	 */
	public Date nextDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, NEXT_DAY_MONTH); 
		return cal.getTime();
	}
}
