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
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.executionlog.ScheduleExecutionLogFinder;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogInfoDto;
import nts.uk.ctx.at.schedule.dom.adapter.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.WorkplaceDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSetRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.schedule.dom.employeeinfo.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.ScheduleManagementControl;
import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.ScheduleManagementControlRepository;
import nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol.UseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;;

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
	
	/** The cre set repository. */
	@Inject
	private PersonalWorkScheduleCreSetRepository creSetRepository;
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;
	
	/** The control repository. */
	@Inject
	private ScheduleManagementControlRepository controlRepository;
	
	/** The content repository. */
	@Inject
	private ScheduleCreateContentRepository contentRepository;
	
	/** The internationalization. */
	@Inject
	private IInternationalization internationalization;
	
	
	/** The sc workplace adapter. */
	@Inject
	private ScWorkplaceAdapter scWorkplaceAdapter;
	
	/** The setter. */
	private TaskDataSetter setter;
	
	/** The command. */
	private ScheduleCreatorExecutionCommand command;
	
	/** The content. */
	private ScheduleCreateContent content;
		
	/** The to date. */
	private Date toDate;
	
	/** The Constant DEFAULT_VALUE. */
	private static final Integer DEFAULT_VALUE = 0;

	/** The Constant TOTAL_RECORD. */
	private static final String TOTAL_RECORD = "TOTAL_RECORD";

	/** The Constant SUCCESS_CNT. */
	private static final String SUCCESS_CNT = "SUCCESS_CNT";

	/** The Constant FAIL_CNT. */
	private static final String FAIL_CNT = "FAIL_CNT";
	
	/** The Constant NEXT_DAY_MONTH. */
	public static final int NEXT_DAY_MONTH = 1;
	
	/** The Constant ZERO_DAY_MONTH. */
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
		command = context.getCommand();

		Optional<ScheduleExecutionLog> optionalScheduleExecutionLog = this.scheduleExecutionLogRepository
				.findById(companyId, command.getExecutionId());

		// check exist data
		if (optionalScheduleExecutionLog.isPresent()) {
			ScheduleExecutionLog domain = optionalScheduleExecutionLog.get();
			domain.setExecutionDateTime(
					new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now()));
			this.scheduleExecutionLogRepository.update(domain);
			Optional<ScheduleCreateContent> optionalContent = this.contentRepository
					.findByExecutionId(command.getExecutionId());
			
			content = optionalContent.get();
			
			List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository
					.findAll(command.getExecutionId());

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
			Optional<ScheduleManagementControl> optionalScheduleManagementControl = this.controlRepository
					.findById(domain.getEmployeeId());

			// check exist data schedule management control
			if (optionalScheduleManagementControl.isPresent()) {
				ScheduleManagementControl scheduleManagementControl = optionalScheduleManagementControl
						.get();

				// check use manager control use
				if (scheduleManagementControl.getScheduleManagementAtr().equals(UseAtr.USE)) {

					// check processExecutionAtr reconfig
					if (content.getReCreateContent()
							.getProcessExecutionAtr().value == ProcessExecutionAtr.RECONFIG.value) {
						this.resetSchedule();
					} else {

						// check parameter CreateMethodAtr
						if (content
								.getCreateMethodAtr().value == CreateMethodAtr.PERSONAL_INFO.value) {
							this.createScheduleBasedPerson(domain, scheduleExecutionLog);
						}
					}
				}
			}
			domain.updateToCreated();
			this.scheduleCreatorRepository.update(domain);
			ScheduleExecutionLogInfoDto dto = this.finder.findInfoById(scheduleExecutionLog.getExecutionId());
			setter.updateData(SUCCESS_CNT, dto.getTotalNumberCreated());
			setter.updateData(FAIL_CNT, dto.getTotalNumberError());
		});
		this.updateStatusScheduleExecutionLog(scheduleExecutionLog);
	}
	
	/**
	 * Reset schedule.
	 */
	// スケジュールを再設定する
	private void resetSchedule(){
		
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
	private void createScheduleBasedPerson(ScheduleCreator creator, ScheduleExecutionLog domain) {
		Optional<PersonalWorkScheduleCreSet> optionalPersonalWorkScheduleCreSet = this.creSetRepository
				.findById(creator.getEmployeeId());
		
		// check exist data PersonalWorkScheduleCreSet
		if (optionalPersonalWorkScheduleCreSet.isPresent()) {
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet = optionalPersonalWorkScheduleCreSet.get();
			
			// check domain WorkScheduleBasicCreMethod is BUSINESS_DAY_CALENDAR
			if (personalWorkScheduleCreSet.getBasicCreateMethod()
					.equals(WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR)) {
				// call create WorkSchedule
				this.createWorkScheduleByBusinessDayCalenda(domain,personalWorkScheduleCreSet);
			}
		}
	}
	
	/**
	 * Creates the work schedule by business day calenda.
	 *
	 * @param domain the domain
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 */
	// 営業日カレンダーで勤務予定を作成する
	private void createWorkScheduleByBusinessDayCalenda(ScheduleExecutionLog domain,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		
		// get to day by start period date 
		toDate = domain.getPeriod().getStartDate().date();
		
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
					if (content.getImplementAtr().value == ImplementAtr.RECREATE.value) {
						
						// check parameter ReCreateAtr onlyUnconfirm
						if(content.getReCreateContent().getReCreateAtr().value == ReCreateAtr.ONLYUNCONFIRM.value){
							
							// check confirmedAtr of basic schedule
							if(basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)){
								this.getWorktype(personalWorkScheduleCreSet);
							}
						}else {
							this.getWorktype(personalWorkScheduleCreSet);
						}
					}
				}
			}
			// (button Interrupt)
			toDate = this.nextDay(toDate);
		}
	}
	
	
	/**
	 * Gets the worktype.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktype
	 */
	// 勤務種類を取得する
	private void getWorktype(PersonalWorkScheduleCreSet personalWorkScheduleCreSet){
		this.getBasicWorkSetting(personalWorkScheduleCreSet);
	}
	
	/**
	 * Gets the basic work setting.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the basic work setting
	 */
	// 基本勤務設定を取得する
	private void getBasicWorkSetting(PersonalWorkScheduleCreSet personalWorkScheduleCreSet){
		this.getBusinessDayCalendar(personalWorkScheduleCreSet);
	}
	
	/**
	 * Gets the business day calendar.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the business day calendar
	 */
	// 営業日カレンダーから「稼働日区分」を取得する
	private void getBusinessDayCalendar(PersonalWorkScheduleCreSet personalWorkScheduleCreSet){
		
		// check 営業日カレンダーの参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceBusinessDayCalendar()
				.equals(WorkScheduleMasterReferenceAtr.WORKPLACE)) {
			
			Optional<WorkplaceDto> optionalWorkplace = this.scWorkplaceAdapter.findWorkplaceById(
					personalWorkScheduleCreSet.getEmployeeId(), GeneralDate.legacyDate(toDate));
			
			if(optionalWorkplace.isPresent()){
				
			} else {
				
				// add log error employee => 602
				this.addError(personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}
			
		} else
		// CLASSIFICATION
		{

		}
	}

	/**
	 * Adds the error.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 */
	private void addError(String employeeId, String messageId) {
		List<ScheduleErrorLog> errorLogs = this.scheduleErrorLogRepository
				.findByEmployeeId(content.getExecutionId(), employeeId);
		if (CollectionUtil.isEmpty(errorLogs)) {
			this.scheduleErrorLogRepository.add(this.toScheduleErrorLog(employeeId, messageId));
		}
	}
	
	/**
	 * Gets the schedule work hour.
	 *
	 * @return the schedule work hour
	 */
	// 勤務予定時間帯を取得する
	private void getScheduleWorkHour(){
		
	}
	
	
	
	/**
	 * To schedule error log.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 * @return the schedule error log
	 */
	private ScheduleErrorLog toScheduleErrorLog(String employeeId, String messageId) {
		return new ScheduleErrorLog(new ScheduleErrorLogGetMemento() {

			/**
			 * Gets the execution id.
			 *
			 * @return the execution id
			 */
			@Override
			public String getExecutionId() {
				return command.getExecutionId();
			}

			/**
			 * Gets the error content.
			 *
			 * @return the error content
			 */
			@Override
			public String getErrorContent() {
				return internationalization.getMessage(messageId).get();
			}

			/**
			 * Gets the employee id.
			 *
			 * @return the employee id
			 */
			@Override
			public String getEmployeeId() {
				return employeeId;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see nts.uk.ctx.at.schedule.dom.executionlog.
			 * ScheduleErrorLogGetMemento#getDate()
			 */
			@Override
			public GeneralDate getDate() {
				return GeneralDate.legacyDate(toDate);
			}

		});
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
