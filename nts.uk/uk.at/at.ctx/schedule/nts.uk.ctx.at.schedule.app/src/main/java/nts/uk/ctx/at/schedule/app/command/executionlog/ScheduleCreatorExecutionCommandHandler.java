/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.math.BigDecimal;
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
import nts.uk.ctx.at.schedule.dom.employeeinfo.TimeZoneScheduledMasterAtr;
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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
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
	
	
	/** The workplace basic work repository. */
	@Inject
	private WorkplaceBasicWorkRepository workplaceBasicWorkRepository;
	
	/** The calendar work place repository. */
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;
	
	/** The calendar company repository. */
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;
	
	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The work time repository. */
	@Inject
	private WorkTimeRepository workTimeRepository;
		
	/** The setter. */
	private TaskDataSetter setter;
	
	/** The command. */
	private ScheduleCreatorExecutionCommand command;
	
	/** The content. */
	private ScheduleCreateContent content;
		
	/** The to date. */
	private Date toDate;
	
	/** The company id. */
	private String companyId;
	
	/** The Constant DEFAULT_VALUE. */
	private static final Integer DEFAULT_VALUE = 0;

	/** The Constant TOTAL_RECORD. */
	private static final String TOTAL_RECORD = "TOTAL_RECORD";

	/** The Constant SUCCESS_CNT. */
	private static final String SUCCESS_CNT = "SUCCESS_CNT";

	/** The Constant FAIL_CNT. */
	private static final String FAIL_CNT = "FAIL_CNT";
	
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
		companyId = loginUserContext.companyId();

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
	 * @param creator the creator
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
		toDate = domain.getPeriod().start().date();
		
		// loop start period date => end period date
		while(toDate.before(this.nextDay(domain.getPeriod().end().date()))){
			
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
		Optional<String> optionalWorktypeCode = this.getWorktypeCode(personalWorkScheduleCreSet);
		if (!this.checkExistError(personalWorkScheduleCreSet.getEmployeeId())) {
			this.getWorktime(optionalWorktypeCode.get(), personalWorkScheduleCreSet);
		}
	}
	
	
	/**
	 * Gets the basic work setting.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the basic work setting
	 */
	// 基本勤務設定を取得する
	private Optional<BasicWorkSetting> getBasicWorkSetting(
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<Integer> optionalBusinessDayCalendar = this
				.getBusinessDayCalendar(personalWorkScheduleCreSet);
		if (optionalBusinessDayCalendar.isPresent()) {
			this.getWorktypeCode(personalWorkScheduleCreSet);
			return this.getBasicWorkSettingByWorkdayDivision(personalWorkScheduleCreSet,
					optionalBusinessDayCalendar.get());
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the worktype code.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktype code
	 */
	// 在職状態に対応する「勤務種類コード」を取得する
	private Optional<String> getWorktypeCode(PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		String worktypeCode = null;
		// check 就業時間帯の参照先 of 勤務予定の時間帯マスタ参照区分 == 個人曜日別
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal()
				.getReferenceWorkingHours().value == TimeZoneScheduledMasterAtr.PERSONAL_DAY_OF_WEEK.value) {
			worktypeCode = this.convertWorktypeCodeByDayOfWeek();
		} else
		// マスタ参照区分に従う、個人勤務日別
		{
			worktypeCode = this.convertWorktypeCodeByWorkingStatus();
		}
		Optional<WorkType> optionalWorktype = this.workTypeRepository.findByPK(companyId, worktypeCode);
		
		if(!optionalWorktype.isPresent()){
			this.addError(personalWorkScheduleCreSet.getEmployeeId(), "Msg_590");
		}
		else {
			return Optional.of(worktypeCode);
		}
		return Optional.empty();
	}

	/**
	 * Convert worktype code by day of week.
	 *
	 * @return the string
	 */
	// 個人曜日別と在職状態から「勤務種類コード」を変換する
	private String convertWorktypeCodeByDayOfWeek() {
		
		return "001";
	}
	
	/**
	 * Convert worktype code by working status.
	 *
	 * @return the string
	 */
	// 在職状態から「勤務種類コード」を変換する
	private String convertWorktypeCodeByWorkingStatus(){
		return "001";
	}
	
	/**
	 * Gets the business day calendar.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the business day calendar
	 */
	// 営業日カレンダーから「稼働日区分」を取得する
	private Optional<Integer> getBusinessDayCalendar(
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		// check 営業日カレンダーの参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceBusinessDayCalendar()
				.equals(WorkScheduleMasterReferenceAtr.WORKPLACE)) {

			Optional<WorkplaceDto> optionalWorkplace = this.scWorkplaceAdapter.findWorkplaceById(
					personalWorkScheduleCreSet.getEmployeeId(), GeneralDate.legacyDate(toDate));

			if (optionalWorkplace.isPresent()) {
				WorkplaceDto workplaceDto = optionalWorkplace.get();
				
				List<String> workplaceIds = this.findLelvelWorkplace(workplaceDto.getWorkplaceId());

				return this.getWorkdayDivisionByWkp(personalWorkScheduleCreSet.getEmployeeId(), workplaceIds);
			} else {
				// add log error employee => 602
				this.addError(personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}

		} else
		// CLASSIFICATION
		{

		}
		return Optional.empty();

	}
	
	/**
	 * Gets the basic work setting by workday division.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @param workdayDivision the workday division
	 * @return the basic work setting by workday division
	 */
	// 「稼働日区分」に対応する「基本勤務設定」を取得する
	private Optional<BasicWorkSetting> getBasicWorkSettingByWorkdayDivision(
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet, int workdayDivision) {
		// check 営業日カレンダーの参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceBusinessDayCalendar()
				.equals(WorkScheduleMasterReferenceAtr.WORKPLACE)) {

			Optional<WorkplaceDto> optionalWorkplace = this.scWorkplaceAdapter.findWorkplaceById(
					personalWorkScheduleCreSet.getEmployeeId(), GeneralDate.legacyDate(toDate));

			if (optionalWorkplace.isPresent()) {
				WorkplaceDto workplaceDto = optionalWorkplace.get();

				List<String> workplaceIds = this.findLelvelWorkplace(workplaceDto.getWorkplaceId());

				return this.getBasicWorkSetting(workplaceIds, workdayDivision);

			} else {

				// add log error employee => 602
				this.addError(personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}

		} else
		// CLASSIFICATION
		{

		}
		return Optional.empty();
	}

	/**
	 * Adds the error.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 */
	private void addError(String employeeId, String messageId) {
		if (!this.checkExistError(employeeId)) {
			this.scheduleErrorLogRepository.add(this.toScheduleErrorLog(employeeId, messageId));
		}
	}
	
	/**
	 * Check exist error.
	 *
	 * @param employeeId the employee id
	 * @return true, if successful
	 */
	private boolean checkExistError(String employeeId) {
		List<ScheduleErrorLog> errorLogs = this.scheduleErrorLogRepository.findByEmployeeId(content.getExecutionId(),
				employeeId);
		if (CollectionUtil.isEmpty(errorLogs)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the schedule work hour.
	 *
	 * @param worktypeCode the worktype code
	 * @param worktimeCode the worktime code
	 * @return the schedule work hour
	 */
	// 勤務予定時間帯を取得する
	private Optional<String> getScheduleWorkHour(String worktypeCode, String worktimeCode){
		
		WorkStyle  workStyle = this.basicScheduleService.checkWorkDay(worktypeCode);
		
		if (workStyle.value == WorkStyle.ONE_DAY_REST.value) {
			return Optional.empty();
		}
		
		if (workStyle.value == WorkStyle.AFTERNOON_WORK.value || workStyle.value == WorkStyle.MORNING_WORK.value) {
			this.getHalfDayWorkingHours(worktimeCode);
		}
		
		if(workStyle.value == WorkStyle.ONE_DAY_WORK.value){
			
		}
		return Optional.empty();
		
	}
	
	/**
	 * Gets the half day working hours.
	 *
	 * @param worktimeCode the worktime code
	 * @return the half day working hours
	 */
	// 午前または午後の勤務時間帯を取得
	private void getHalfDayWorkingHours(String worktimeCode){
		
	}
		
	/**
	 * Find lelvel workplace.
	 *
	 * @param workplaceId the workplace id
	 * @return the list
	 */
	// 所属職場を含む上位職場を取得
	private List<String> findLelvelWorkplace(String workplaceId) {
		return this.scWorkplaceAdapter.findWpkIdList(companyId, workplaceId, toDate);
	}
	
	
	/**
	 * Gets the basic work setting by wkp ids.
	 *
	 * @param workplaceId the workplace id
	 * @param workDayAtr the work day atr
	 * @return the basic work setting by wkp ids
	 */
	private Optional<BasicWorkSetting> getBasicWorkSettingByWkpIds(String workplaceId,
			int workDayAtr) {
		List<String> workplaceIds = this.findLelvelWorkplace(workplaceId);

		// loop work place id
		for (String wkpId : workplaceIds) {
			Optional<BasicWorkSetting> optional = this.getBasicWorkSettingByWkpId(wkpId,
					workDayAtr);
			if (optional.isPresent()) {
				return optional;
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the basic work setting by wkp id.
	 *
	 * @param workplaceId the workplace id
	 * @param workDayAtr the work day atr
	 * @return the basic work setting by wkp id
	 */
	
	private Optional<BasicWorkSetting> getBasicWorkSettingByWkpId(String workplaceId, int workDayAtr) {
		return this.workplaceBasicWorkRepository.findById(workplaceId).map(setting -> {
			for (BasicWorkSetting basicWorkSetting : setting.getBasicWorkSetting()) {
				if (basicWorkSetting.getWorkdayDivision().value == workDayAtr) {
					return basicWorkSetting;
				}
			}
			return null;
		});
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
	
	/**
	 * Gets the workday division by wkp.
	 *
	 * @param employeeId the employee id
	 * @param workplaceIds the workplace ids
	 * @return the workday division by wkp
	 */
	// 職場の稼働日区分を取得する
	public Optional<Integer> getWorkdayDivisionByWkp(String employeeId, List<String> workplaceIds) {
		for (String workplaceId : workplaceIds) {
			Optional<CalendarWorkplace> optionalCalendarWorkplace = this.calendarWorkPlaceRepository
					.findCalendarWorkplaceByDate(workplaceId,
							this.toYearMonthDate(GeneralDate.legacyDate(toDate)));
			// check exist data WorkplaceBasicWork
			if (optionalCalendarWorkplace.isPresent()) {
				return Optional.of(optionalCalendarWorkplace.get().getWorkingDayAtr().value);
			}
		}

		Optional<CalendarCompany> optionalCalendarCompany = this.calendarCompanyRepository
				.findCalendarCompanyByDate(companyId,
						this.toYearMonthDate(GeneralDate.legacyDate(toDate)));
		if (optionalCalendarCompany.isPresent()) {
			return Optional.of(optionalCalendarCompany.get().getWorkingDayAtr().value);
		}
		// add error messageId Msg_588
		this.addError(employeeId, "Msg_588");
		return Optional.empty();
	}

	/**
	 * To year month date.
	 *
	 * @param baseDate the base date
	 * @return the big decimal
	 */
	private BigDecimal toYearMonthDate(GeneralDate baseDate) {
		return new BigDecimal(
				baseDate.year() * MUL_YEAR + baseDate.month() * MUL_MONTH + baseDate.day());
	}
	
	/**
	 * Gets the basic work setting.
	 *
	 * @param workplaceIds the workplace ids
	 * @param workdayAtr the workday atr
	 * @return the basic work setting
	 */
	// 職場の基本勤務設定を取得する
	private Optional<BasicWorkSetting> getBasicWorkSetting(List<String> workplaceIds,
			int workdayAtr) {
		for (String workplaceId : workplaceIds) {
			Optional<WorkplaceBasicWork> optionalWorkplaceBasicWork = this.workplaceBasicWorkRepository
					.findById(workplaceId);

			// check exist data WorkplaceBasicWork
			if (optionalWorkplaceBasicWork.isPresent()) {
				return this.toBasicWorkSetting(optionalWorkplaceBasicWork.get(), workdayAtr);
			}

		}
		return Optional.empty();
	}
	
	/**
	 * To basic work setting.
	 *
	 * @param domain the domain
	 * @param workdayAtr the workday atr
	 * @return the optional
	 */
	private Optional<BasicWorkSetting> toBasicWorkSetting(WorkplaceBasicWork domain,
			int workdayAtr) {
		for (BasicWorkSetting basicWorkSetting : domain.getBasicWorkSetting()) {
			if (basicWorkSetting.getWorkdayDivision().value == workdayAtr) {
				return Optional.ofNullable(basicWorkSetting);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the worktype code leave holiday type.
	 *
	 * @param employeeId the employee id
	 * @param worktypeCode the worktype code
	 * @param leaveHolidayType the leave holiday type
	 * @return the worktype code leave holiday type
	 */
	// 休業休職の勤務種類コードを返す
	private String getWorktypeCodeLeaveHolidayType(String employeeId, String worktypeCode, int leaveHolidayType) {
		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(worktypeCode);
		if (workStyle.equals(WorkStyle.ONE_DAY_REST)) {
			return worktypeCode;
		}
		// find work type
		WorkType worktype = this.workTypeRepository.findByPK(companyId, worktypeCode).get();

		if (this.checkHolidayWork(worktype.getDailyWork())) {
			// 休日出勤
			return worktypeCode;
		} else {
			List<WorkTypeSet> worktypeSets = this.workTypeRepository.findWorkTypeSetCloseAtr(worktypeCode,
					leaveHolidayType);
			if (CollectionUtil.isEmpty(worktypeSets)) {
				this.addError(employeeId, "Msg_601");
			}
			return worktypeSets.get(0).getWorkTypeCd().v();
		}
	}
	
	/**
	 * Check holiday work.
	 *
	 * @param dailyWork the daily work
	 * @return true, if successful
	 */
	// ? = 休日出勤
	private boolean checkHolidayWork(DailyWork dailyWork){
		if(dailyWork.getWorkTypeUnit().value == WorkTypeUnit.OneDay.value){
			return dailyWork.getOneDay().value == WorkTypeClassification.HolidayWork.value;
		}
		return (dailyWork.getMorning().value == WorkTypeClassification.HolidayWork.value
				|| dailyWork.getAfternoon().value == WorkTypeClassification.HolidayWork.value);
	}
	
	/**
	 * Gets the worktime.
	 *
	 * @param worktypeCode the worktype code
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktime
	 */
	// 就業時間帯を取得する
	private void getWorktime(String worktypeCode, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		this.getBasicWorkSetting(personalWorkScheduleCreSet);
		this.getWorkingTimeZoneCode(worktypeCode, personalWorkScheduleCreSet);
	}
	
	/**
	 * Gets the working time zone code.
	 *
	 * @param worktypeCode the worktype code
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the working time zone code
	 */
	// 在職状態に対応する「就業時間帯コード」を取得する
	private void getWorkingTimeZoneCode(String worktypeCode, PersonalWorkScheduleCreSet personalWorkScheduleCreSet){
		
		String worktimeCode = null;
		switch (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceWorkingHours()) {
		case FOLLOW_MASTER_REFERENCE:
			worktimeCode = this.convertWorkingHoursEmploymentStatus();
			break;

		case PERSONAL_WORK_DAILY:
			worktimeCode = this.convertWorkingHoursPersonalWork();
		case PERSONAL_DAY_OF_WEEK:
			worktimeCode = this.convertWorkingHoursPersonalDayofWeek();
			break;
		}
		
		// check not exist data work 
		if (!this.workTimeRepository.findByCode(companyId, worktimeCode).isPresent()) {
			this.addError(personalWorkScheduleCreSet.getEmployeeId(), "Msg_591");
		} else {
			this.getScheduleWorkHour(worktypeCode, worktimeCode);
		}
	}
	
	/**
	 * Convert working hours employment status.
	 *
	 * @return the string
	 */
	// 在職状態から「就業時間帯コードを変換する」に変更
	private String convertWorkingHoursEmploymentStatus(){
		return "000";
	}
	
	/**
	 * Convert working hours personal work.
	 *
	 * @return the string
	 */
	// 個人勤務日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalWork(){
		return "000";
	}
	
	/**
	 * Convert working hours personal dayof week.
	 *
	 * @return the string
	 */
	// 個人曜日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalDayofWeek(){
		return "000";
	}
	
	/**
	 * Gets the schedule break time.
	 *
	 * @param worktimeCode the worktime code
	 * @return the schedule break time
	 */
	// 休憩予定時間帯を取得する
	private void getScheduleBreakTime(String worktypeCode, String worktimeCode) {
		if (worktypeCode == null || worktypeCode.equals(DEFAULT_CODE)) {
			return;
		}

		Optional<WorkType> optionalWorktype = this.workTypeRepository.findByPK(companyId, worktypeCode);
		if (optionalWorktype.isPresent()) {
			WorkType workType = optionalWorktype.get();

			if (this.checkHolidayWork(workType.getDailyWork())) {

				Optional<WorkTime> optionalWorktime = this.workTimeRepository.findByCode(companyId, worktimeCode);

				if (optionalWorktime.isPresent()) {
					WorkTime workTime = optionalWorktime.get();
					if (WorkTimeDailyAtr.Enum_Regular_Work.value == workTime.getWorkTimeDivision()
							.getWorkTimeDailyAtr().value) {
						switch (workTime.getWorkTimeDivision().getWorkTimeMethodSet()) {
						case Enum_Fixed_Work:

							break;

						default:
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Gets the schedule determination atr.
	 *
	 * @return the schedule determination atr
	 */
	// 予定確定区分を取得
	private void getScheduleDeterminationAtr(boolean isConfirm){
		if (isConfirm) {
			
		} else {

		}
	}

}


