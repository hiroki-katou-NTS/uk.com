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
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommandHandler;
import nts.uk.ctx.at.schedule.dom.adapter.ScClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScShortWorkTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ClassificationDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.WorkplaceDto;
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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSetRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;;

/**
 * The Class ScheduleCreatorExecutionCommandHandler.
 */
@Stateful
public class ScheduleCreatorExecutionCommandHandler
		extends AsyncCommandHandler<ScheduleCreatorExecutionCommand> {
	
	/** The basic save. */
	@Inject
	private BasicScheduleSaveCommandHandler basicSave;
	
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
	
	/** The control repository. */
	@Inject
	private ScheduleManagementControlRepository controlRepository;
	
	/** The content repository. */
	@Inject
	private ScheduleCreateContentRepository contentRepository;
	
	/** The internationalization. */
	@Inject
	private I18NResourcesForUK internationalization;
	
	
	/** The sc workplace adapter. */
	@Inject
	private ScWorkplaceAdapter scWorkplaceAdapter;
	
	/** The sc classification adapter. */
	@Inject
	private ScClassificationAdapter scClassificationAdapter;
	
	/** The classification basic work repository. */
	@Inject
	private ClassifiBasicWorkRepository classificationBasicWorkRepository;
	
	/** The company basic work repository. */
	@Inject
	private CompanyBasicWorkRepository companyBasicWorkRepository;
	
	/** The calendar class repository. */
	@Inject
	private CalendarClassRepository calendarClassRepository;
		
	/** The work place basic work repository. */
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
	
	/** The work time set repository. */
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;
		
	/** The sc employment status adapter. */
	@Inject
	private ScEmploymentStatusAdapter scEmploymentStatusAdapter;
	
	/** The personal labor condition repository. */
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	/** The sc short work time adapter. */
	@Inject
	private ScShortWorkTimeAdapter scShortWorkTimeAdapter;
	
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
	protected void handle(CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		
		
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		ScheduleCreatorExecutionCommand command = context.getCommand();
		command.setCompanyId(companyId);

		Optional<ScheduleExecutionLog> optionalScheduleExecutionLog;
		optionalScheduleExecutionLog = this.scheduleExecutionLogRepository.findById(companyId,
				command.getExecutionId());

		// check exist data
		if (optionalScheduleExecutionLog.isPresent()) {
			ScheduleExecutionLog domain = optionalScheduleExecutionLog.get();
			domain.setExecutionDateTime(
					new ExecutionDateTime(GeneralDateTime.now(), GeneralDateTime.now()));
			this.scheduleExecutionLogRepository.update(domain);
			Optional<ScheduleCreateContent> optionalContent = this.contentRepository
					.findByExecutionId(command.getExecutionId());

			if (optionalContent.isPresent()) {
				command.setContent(optionalContent.get());
			}

			List<ScheduleCreator> scheduleCreators = this.scheduleCreatorRepository
					.findAll(command.getExecutionId());

			this.registerPersonalSchedule(command, domain, scheduleCreators, context);
		}

	}

	/**
	 * Register personal schedule.
	 *
	 * @param scheduleExecutionLog the schedule execution log
	 * @param scheduleCreators the schedule creators
	 */
	// 個人スケジュールを登録する
	private void registerPersonalSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog, List<ScheduleCreator> scheduleCreators,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context) {
		val asyncTask = context.asAsync();
		for (ScheduleCreator domain : scheduleCreators) {
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				break;
			}
			Optional<ScheduleManagementControl> optionalScheduleManagementControl;
			optionalScheduleManagementControl = this.controlRepository
					.findById(domain.getEmployeeId());

			// check exist data schedule management control
			if (optionalScheduleManagementControl.isPresent()) {
				ScheduleManagementControl scheduleManagementControl = optionalScheduleManagementControl
						.get();

				// check use manager control use
				if (scheduleManagementControl.getScheduleManagementAtr().equals(UseAtr.USE)) {

					// check processExecutionAtr reconfig
					if (command.getContent().getReCreateContent()
							.getProcessExecutionAtr().value == ProcessExecutionAtr.RECONFIG.value) {
						this.resetSchedule(command, domain, scheduleExecutionLog);
					} else {

						// check parameter CreateMethodAtr
						if (command.getContent()
								.getCreateMethodAtr().value == CreateMethodAtr.PERSONAL_INFO.value) {
							this.createScheduleBasedPerson(command, domain, scheduleExecutionLog);
						}
					}
				}
			}
			domain.updateToCreated();
			this.scheduleCreatorRepository.update(domain);
			// insert basic schedule
			BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
			commandSave.setConfirmedAtr(this.getConfirmedAtr(true, ConfirmedAtr.CONFIRMED).value);
			commandSave = toCommandSave(commandSave, domain.getEmployeeId(), "001", "001");
			this.basicSave.handle(commandSave);
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
		command.setToDate(domain.getPeriod().start().date());

		// loop start period date => end period date
		while (command.getToDate().before(this.nextDay(domain.getPeriod().end().date()))) {

			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
					.find(creator.getEmployeeId(), GeneralDate.legacyDate(command.getToDate()));
			if (optionalBasicSchedule.isPresent()
					&& command.getContent().getReCreateContent().getReCreateAtr()
							.equals(ReCreateAtr.ONLYUNCONFIRM)
					&& optionalBasicSchedule.get().getConfirmedAtr()
							.equals(ConfirmedAtr.CONFIRMED)) {
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
		command.setToDate(domain.getPeriod().start().date());

		// loop start period date => end period date
		while (command.getToDate().before(this.nextDay(domain.getPeriod().end().date()))) {

			Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
					.findById(personalWorkScheduleCreSet.getEmployeeId(),
							GeneralDate.legacyDate(command.getToDate()));

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
	 */
	private boolean createWorkScheduleByBusinessDayCalendaUseManager(
			ScheduleCreatorExecutionCommand command, ScheduleExecutionLog domain,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		// get status employment
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));

		// status employment equal RETIREMENT (退職)
		if (employmentStatus.getStatusOfEmployment() == RETIREMENT) {
			return true;
		}

		// status employment not equal BEFORE_JOINING (入社前)
		if (employmentStatus.getStatusOfEmployment() != BEFORE_JOINING) {
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(
					personalWorkScheduleCreSet.getEmployeeId(),
					GeneralDate.legacyDate(command.getToDate()));

			// check exist data basic schedule
			if (optionalBasicSchedule.isPresent()) {

				BasicSchedule basicSchedule = optionalBasicSchedule.get();
				this.createWorkScheduleByImplementAtr(command, basicSchedule,
						personalWorkScheduleCreSet);
			}
		}
		return false;
	}
	
	/**
	 * Creates the work schedule by implement atr.
	 *
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
				.getReCreateAtr().value == ReCreateAtr.ONLYUNCONFIRM.value) {

			// check confirmedAtr of basic schedule
			if (basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {
				this.getWorktype(command, personalWorkScheduleCreSet);
			}
		} else {
			this.getWorktype(command, personalWorkScheduleCreSet);
		}
	}
	
	
	/**
	 * Gets the work type.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the work type
	 */
	// 勤務種類を取得する
	private void getWorktype(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<BasicWorkSetting> optionalBasicWorkSetting = this.getBasicWorkSetting(command,
				personalWorkScheduleCreSet);
		if (optionalBasicWorkSetting.isPresent()) {
			Optional<String> optionalWorktypeCode = this.getWorktypeCode(command,
					optionalBasicWorkSetting.get(), personalWorkScheduleCreSet);
			if (!this.checkExistError(command, personalWorkScheduleCreSet.getEmployeeId())) {
				Optional<Object> optionalWorkTimeObj = this.getWorktime(command,
						optionalWorktypeCode.get(), personalWorkScheduleCreSet);
				if (optionalWorkTimeObj.isPresent()
						&& optionalWorkTimeObj.get() instanceof String) {
					this.getScheduleBreakTime(command, optionalWorktypeCode.get(),
							optionalWorkTimeObj.get().toString());
					
					
				}
			}
		}
	}
	
	/**
	 * Gets the basic work setting.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the basic work setting
	 */
	// 基本勤務設定を取得する
	private Optional<BasicWorkSetting> getBasicWorkSetting(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<Integer> optionalBusinessDayCalendar = this.getBusinessDayCalendar(command,
				personalWorkScheduleCreSet);
		if (optionalBusinessDayCalendar.isPresent()) {
			return this.getBasicWorkSettingByWorkdayDivision(command, personalWorkScheduleCreSet,
					optionalBusinessDayCalendar.get());
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the work type code.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the work type code
	 */
	// 在職状態に対応する「勤務種類コード」を取得する
	private Optional<String> getWorktypeCode(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		String worktypeCode = null;
		// check 就業時間帯の参照先 of 勤務予定の時間帯マスタ参照区分 == 個人曜日別
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal()
				.getReferenceWorkingHours().value == TimeZoneScheduledMasterAtr.PERSONAL_DAY_OF_WEEK.value) {
			worktypeCode = this.convertWorktypeCodeByDayOfWeekPersonal(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		} else
		// マスタ参照区分に従う、個人勤務日別
		{
			worktypeCode = this.convertWorktypeCodeByWorkingStatus(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		Optional<WorkType> optionalWorktype = this.workTypeRepository
				.findByPK(command.getCompanyId(), worktypeCode);

		if (!optionalWorktype.isPresent()) {
			this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_590");
		} else {
			return Optional.of(worktypeCode);
		}
		return Optional.empty();
	}

	/**
	 * Convert worktype code by day of week personal.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the string
	 */
	// 個人曜日別と在職状態から「勤務種類コード」を変換する
	private String convertWorktypeCodeByDayOfWeekPersonal(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		EmploymentStatusDto employmentStatus = this.scEmploymentStatusAdapter.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));

		// employment status is INCUMBENT 
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorktypeCodeInOffice(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		
		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return this.getWorktypeCodeLeaveHolidayType(command, basicWorkSetting,
					personalWorkScheduleCreSet, employmentStatus);
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the worktype code in office.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktype code in office
	 */
	// 在職の勤務種類コードを返す
	private String getWorktypeCodeInOffice(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// check default work time code
		if (this.checkDefaultWorkTimeCode(basicWorkSetting)) {
			return basicWorkSetting.getWorktypeCode().v();
		} else {
			String worktime = this.getWorkTimeCodeOfDayOfWeekPersonalCondition(command,
					basicWorkSetting, personalWorkScheduleCreSet);
			if (worktime == null || worktime.equals(DEFAULT_CODE)) {
				return basicWorkSetting.getWorktypeCode().v();
			}

			Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
					.findById(personalWorkScheduleCreSet.getEmployeeId(),
							GeneralDate.legacyDate(command.getToDate()));
			if (optionalPersonalLaborCondition.isPresent()) {
				return optionalPersonalLaborCondition.get().getWorkCategory().getHolidayTime()
						.getWorkTypeCode().v();
			}
		}

		return DEFAULT_CODE;
	}

	/**
	 * Convert worktype code by working status.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the string
	 */
	// 在職状態から「勤務種類コード」を変換する
	private String convertWorktypeCodeByWorkingStatus(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		EmploymentStatusDto employmentStatus = this.scEmploymentStatusAdapter.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));

		// employment status is 在職
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return basicWorkSetting.getWorktypeCode().v();
		}

		// employment status is 休業 or 休職
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return this.getWorktypeCodeLeaveHolidayType(command, basicWorkSetting,
					personalWorkScheduleCreSet, employmentStatus);
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the business day calendar.
	 *
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the business day calendar
	 */
	// 営業日カレンダーから「稼働日区分」を取得する
	private Optional<Integer> getBusinessDayCalendar(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		// check 営業日カレンダーの参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceBusinessDayCalendar()
				.equals(WorkScheduleMasterReferenceAtr.WORKPLACE)) {

			Optional<WorkplaceDto> optionalWorkplace = this.scWorkplaceAdapter.findWorkplaceById(
					personalWorkScheduleCreSet.getEmployeeId(),
					GeneralDate.legacyDate(command.getToDate()));

			if (optionalWorkplace.isPresent()) {
				WorkplaceDto workplaceDto = optionalWorkplace.get();
				List<String> workplaceIds = this.findLevelWorkplace(command,
						workplaceDto.getWorkplaceId());
				return this.getWorkdayDivisionByWkp(command,
						personalWorkScheduleCreSet.getEmployeeId(), workplaceIds);
			} else {
				// add log error employee => 602
				this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}

		} else
		// CLASSIFICATION
		{
			Optional<ClassificationDto> optionalClassification = this.scClassificationAdapter
					.findByDate(personalWorkScheduleCreSet.getEmployeeId(),
							GeneralDate.legacyDate(command.getToDate()));
			if (optionalClassification.isPresent()) {
				ClassificationDto classificationDto = optionalClassification.get();
				return this.getWorkdayDivisionByClass(command,
						personalWorkScheduleCreSet.getEmployeeId(),
						classificationDto.getClassificationCode());

			} else {
				// add log error employee => 602
				this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}
		}
		return Optional.empty();

	}
	
	/**
	 * Gets the workday division by class.
	 *
	 * @param employeeId the employee id
	 * @param classficationCode the classfication code
	 * @return the workday division by class
	 */
	// 分類の稼働日区分を取得する
	private Optional<Integer> getWorkdayDivisionByClass(ScheduleCreatorExecutionCommand command,
			String employeeId, String classficationCode) {
		Optional<CalendarClass> optionalCalendarClass = this.calendarClassRepository
				.findCalendarClassByDate(command.getCompanyId(), classficationCode,
						this.toYearMonthDate(GeneralDate.legacyDate(command.getToDate())));
		if (optionalCalendarClass.isPresent()) {
			return Optional.ofNullable(optionalCalendarClass.get().getWorkingDayAtr().value);
		} else {
			Optional<CalendarCompany> optionalCalendarCompany = this.calendarCompanyRepository
					.findCalendarCompanyByDate(command.getCompanyId(),
							this.toYearMonthDate(GeneralDate.legacyDate(command.getToDate())));
			if (optionalCalendarCompany.isPresent()) {
				return Optional.ofNullable(optionalCalendarCompany.get().getWorkingDayAtr().value);
			}
			// add error messageId Msg_588
			this.addError(command, employeeId, "Msg_588");
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
			ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet, int workdayDivision) {
		// check 営業日カレンダーの参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceBusinessDayCalendar()
				.equals(WorkScheduleMasterReferenceAtr.WORKPLACE)) {

			Optional<WorkplaceDto> optionalWorkplace = this.scWorkplaceAdapter.findWorkplaceById(
					personalWorkScheduleCreSet.getEmployeeId(),
					GeneralDate.legacyDate(command.getToDate()));

			if (optionalWorkplace.isPresent()) {
				WorkplaceDto workplaceDto = optionalWorkplace.get();

				List<String> workplaceIds = this.findLevelWorkplace(command,
						workplaceDto.getWorkplaceId());

				return this.getBasicWorkSetting(workplaceIds, workdayDivision);

			} else {
				// add log error employee => 602
				this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}

		}
		// 営業日カレンダーの参照先 is 分類 (referenceBusinessDayCalendar is CLASSIFICATION)
		else {
			Optional<ClassificationDto> optionalClass = this.scClassificationAdapter.findByDate(
					personalWorkScheduleCreSet.getEmployeeId(),
					GeneralDate.legacyDate(command.getToDate()));
			if (optionalClass.isPresent()) {
				return this.getBasicWorkSettingByClassification(command,
						personalWorkScheduleCreSet.getEmployeeId(),
						optionalClass.get().getClassificationCode(), workdayDivision);
			} else {
				this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_602");
			}
		}
		return Optional.empty();
	}

	/**
	 * Gets the basic work setting by classification.
	 *
	 * @param classificationCode the classification code
	 * @param workdayAtr the workday atr
	 * @return the basic work setting by classification
	 */
	// 分類の基本勤務設定を取得する
	private Optional<BasicWorkSetting> getBasicWorkSettingByClassification(
			ScheduleCreatorExecutionCommand command, String employeeId, String classificationCode,
			int workdayAtr) {
		Optional<ClassificationBasicWork> optionalClassificationBasicWork = this.classificationBasicWorkRepository
				.findAll(command.getCompanyId(), classificationCode);
		if (optionalClassificationBasicWork.isPresent()) {
			return this.toBasicWorkSettingClassification(optionalClassificationBasicWork.get(),
					workdayAtr);
		} else {
			Optional<CompanyBasicWork> optionalCompanyBasicWork = this.companyBasicWorkRepository
					.findAll(command.getCompanyId());

			if (optionalCompanyBasicWork.isPresent()) {
				return this.toBasicWorkSettingCompany(optionalCompanyBasicWork.get(), workdayAtr);
			} else {
				this.addError(command, employeeId, "Msg_589");
			}
		}
		return Optional.empty();
	}

	/**
	 * To basic work setting classification.
	 *
	 * @param domain the domain
	 * @param workdayAtr the workday atr
	 * @return the optional
	 */
	private Optional<BasicWorkSetting> toBasicWorkSettingClassification(ClassificationBasicWork domain,
			int workdayAtr) {
		for (BasicWorkSetting basicWorkSetting : domain.getBasicWorkSetting()) {
			if (basicWorkSetting.getWorkdayDivision().value == workdayAtr) {
				return Optional.ofNullable(basicWorkSetting);
			}
		}
		return Optional.empty();
	}

	/**
	 * To basic work setting company.
	 *
	 * @param domain the domain
	 * @param workdayAtr the workday atr
	 * @return the optional
	 */
	private Optional<BasicWorkSetting> toBasicWorkSettingCompany(CompanyBasicWork domain,
			int workdayAtr) {
		for (BasicWorkSetting basicWorkSetting : domain.getBasicWorkSetting()) {
			if (basicWorkSetting.getWorkdayDivision().value == workdayAtr) {
				return Optional.ofNullable(basicWorkSetting);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * Adds the error.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 */
	private void addError(ScheduleCreatorExecutionCommand command, String employeeId,
			String messageId) {
		if (!this.checkExistError(command, employeeId)) {
			this.scheduleErrorLogRepository
					.add(this.toScheduleErrorLog(command, employeeId, messageId));
		}
	}
	
	/**
	 * Check exist error.
	 *
	 * @param employeeId the employee id
	 * @return true, if successful
	 */
	private boolean checkExistError(ScheduleCreatorExecutionCommand command, String employeeId) {
		List<ScheduleErrorLog> errorLogs = this.scheduleErrorLogRepository
				.findByEmployeeId(command.getContent().getExecutionId(), employeeId);
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
	private Optional<Object> getScheduleWorkHour(ScheduleCreatorExecutionCommand command,
			String worktypeCode, String worktimeCode) {

		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(worktypeCode);
		switch (workStyle) {
			case ONE_DAY_REST :
				break;
			case ONE_DAY_WORK :
				return Optional.of(this.workTimeSetRepository.findByCode(command.getCompanyId(),
						worktimeCode));
			default :
				// morning or afternoon
				return this.getTimeZone(command, worktypeCode, worktimeCode);
		}
		return Optional.of(worktypeCode);

	}
	
		
	/**
	 * Gets the time zone.
	 *
	 * @param command the command
	 * @param worktypeCode the worktype code
	 * @param worktimeCode the worktime code
	 * @return the time zone
	 */
	// 変換した時間帯を返す
	private Optional<Object> getTimeZone(ScheduleCreatorExecutionCommand command, String worktypeCode,
			String worktimeCode) {

		// 所定時間帯を取得する
		WorkTimeSet workTimeSet = this.workTimeSetRepository
				.findByCode(command.getCompanyId(), worktimeCode).get();
		// 出勤休日区分を判断
		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(worktypeCode);
		// check workstyle
		if (workStyle.equals(WorkStyle.MORNING_WORK)) {
			if (this.isUseSecondWork(workTimeSet)) {
				workTimeSet = this.updateTimeMorning(workTimeSet);
			} else {
				// chuyen gio ket thuc ca 1 thanh gio ket thuc buoi sang
				workTimeSet.updateEndTimeShift1(
						workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
				workTimeSet.removeShift2();
			}
		} else {// if AFTERNOON_WORK
			if (this.isUseSecondWork(workTimeSet)) {
				workTimeSet = this.updateTimeAfternoon(workTimeSet);
			} else {
				// chuyen gio bat dau ca 1 thanh gio bat dau buoi chieu
				workTimeSet.updateStartTimeShift1(
						workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
				workTimeSet.removeShift2();
			}
		}
		return Optional.of(workTimeSet);
	}
	
	/**
	 * Check end in shift 2.
	 *
	 * @param workTimeSet the work time set
	 * @return true, if successful
	 */
	private boolean checkEndInShift2(WorkTimeSet workTimeSet) {
		int morningEndTime = workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()
				.getDayTime();
		Timezone shift2 = workTimeSet.getPrescribedTimezoneSetting().getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT2).findFirst().get();
		int startTimeShift2 = shift2.getStart().getDayTime();
		if (morningEndTime <= startTimeShift2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Update time morning.
	 *
	 * @param workTimeSet the work time set
	 * @return the work time set
	 */
	private WorkTimeSet updateTimeMorning(WorkTimeSet workTimeSet) {
		if (this.checkEndInShift2(workTimeSet)) {
			workTimeSet.updateEndTimeShift1(
					workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
			workTimeSet.removeShift2();
		} else {
			workTimeSet.updateEndTimeShift2(
					workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
		}
		return workTimeSet;
	}
	
	/**
	 * Check start in shift 1.
	 *
	 * @param workTimeSet the work time set
	 * @return true, if successful
	 */
	private boolean checkStartInShift1(WorkTimeSet workTimeSet) {
		int afternoonStartTime = workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()
				.getDayTime();
		Timezone shift1 = workTimeSet.getPrescribedTimezoneSetting().getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT1).findFirst().get();
		int endTimeShift1 = shift1.getEnd().getDayTime();
		if (afternoonStartTime <= endTimeShift1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Update time afternoon.
	 *
	 * @param workTimeSet the work time set
	 * @return the work time set
	 */
	private WorkTimeSet updateTimeAfternoon(WorkTimeSet workTimeSet) {
		if (this.checkStartInShift1(workTimeSet)) {
			workTimeSet.updateStartTimeShift1(
					workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
		} else {
			workTimeSet.updateStartTimeShift2(
					workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
			workTimeSet.removeShift1();
		}
		return workTimeSet;
	}
	
	/**
	 * Checks if is use second work.
	 *
	 * @param workTimeSet the work time set
	 * @return true, if is use second work
	 */
	private boolean isUseSecondWork(WorkTimeSet workTimeSet) {
		List<Timezone> timezones = workTimeSet.getPrescribedTimezoneSetting().getTimezone();
		for (Timezone item : timezones) {
			if (item.getWorkNo() == SHIFT2) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Find level work place.
	 *
	 * @param workplaceId the work place id
	 * @return the list
	 */
	// 所属職場を含む上位職場を取得
	private List<String> findLevelWorkplace(ScheduleCreatorExecutionCommand command,
			String workplaceId) {
		return this.scWorkplaceAdapter.findWpkIdList(command.getCompanyId(), workplaceId,
				command.getToDate());
	}
			
	/**
	 * To schedule error log.
	 *
	 * @param employeeId the employee id
	 * @param messageId the message id
	 * @return the schedule error log
	 */
	private ScheduleErrorLog toScheduleErrorLog(ScheduleCreatorExecutionCommand command,
			String employeeId, String messageId) {
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
				return messageId+" "+internationalization.localize(messageId).get();
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
				return GeneralDate.legacyDate(command.getToDate());
			}

		});
	}
	
	/**
	 * Gets the status employment.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the status employment
	 */
	// アルゴリズム (Employment)
	private EmploymentStatusDto getStatusEmployment(String employeeId, GeneralDate baseDate) {
		return this.scEmploymentStatusAdapter.getStatusEmployment(employeeId, baseDate);
	}
	
	/**
	 * Gets the short work time.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the short work time
	 */
	// アルゴリズム (WorkTime)
	private Optional<ShortWorkTimeDto> getShortWorkTime(String employeeId, GeneralDate baseDate) {
		return this.scShortWorkTimeAdapter.findShortWorkTime(employeeId, baseDate);
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
	public Optional<Integer> getWorkdayDivisionByWkp(ScheduleCreatorExecutionCommand command,
			String employeeId, List<String> workplaceIds) {
		for (String workplaceId : workplaceIds) {
			Optional<CalendarWorkplace> optionalCalendarWorkplace = this.calendarWorkPlaceRepository
					.findCalendarWorkplaceByDate(workplaceId,
							this.toYearMonthDate(GeneralDate.legacyDate(command.getToDate())));
			// check exist data WorkplaceBasicWork
			if (optionalCalendarWorkplace.isPresent()) {
				return Optional.of(optionalCalendarWorkplace.get().getWorkingDayAtr().value);
			}
		}

		Optional<CalendarCompany> optionalCalendarCompany = this.calendarCompanyRepository
				.findCalendarCompanyByDate(command.getCompanyId(),
						this.toYearMonthDate(GeneralDate.legacyDate(command.getToDate())));
		if (optionalCalendarCompany.isPresent()) {
			return Optional.of(optionalCalendarCompany.get().getWorkingDayAtr().value);
		}
		// add error messageId Msg_588
		this.addError(command, employeeId, "Msg_588");
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
	 * @param workdayAtr the work day atr
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
	 * @param command the command
	 * @param employeeId the employee id
	 * @param worktypeCode the worktype code
	 * @param leaveHolidayType the leave holiday type
	 * @return the worktype code leave holiday type
	 */
	// 休業休職の勤務種類コードを返す
	private String getWorktypeCodeLeaveHolidayType(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet,
			EmploymentStatusDto employmentStatus) {
		WorkStyle workStyle = this.basicScheduleService
				.checkWorkDay(basicWorkSetting.getWorktypeCode().v());
		if (workStyle.equals(WorkStyle.ONE_DAY_REST)) {
			return basicWorkSetting.getWorktypeCode().v();
		}
		// find work type
		WorkType worktype = this.workTypeRepository
				.findByPK(command.getCompanyId(), basicWorkSetting.getWorktypeCode().v()).get();

		if (this.checkHolidayWork(worktype.getDailyWork())) {
			// 休日出勤
			return basicWorkSetting.getWorktypeCode().v();
		} else {
			List<WorkTypeSet> worktypeSets = this.workTypeRepository.findWorkTypeSetCloseAtr(
					command.getCompanyId(), employmentStatus.getLeaveHolidayType());
			
			if (CollectionUtil.isEmpty(worktypeSets)) {
				this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_601");
				return DEFAULT_CODE;
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
	 * @param command the command
	 * @param worktypeCode the worktype code
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktime
	 */
	// 就業時間帯を取得する
	private Optional<Object> getWorktime(ScheduleCreatorExecutionCommand command,
			String worktypeCode, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<BasicWorkSetting> optionalBasicWorkSetting = this.getBasicWorkSetting(command,
				personalWorkScheduleCreSet);

		if (optionalBasicWorkSetting.isPresent()) {
			return this.getWorkingTimeZoneCode(command, optionalBasicWorkSetting.get(),
					personalWorkScheduleCreSet);
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the working time zone code.
	 *
	 * @param worktypeCode the worktype code
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the working time zone code
	 */
	// 在職状態に対応する「就業時間帯コード」を取得する
	private Optional<Object> getWorkingTimeZoneCode(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		String worktimeCode = null;
		switch (personalWorkScheduleCreSet.getWorkScheduleBusCal().getReferenceWorkingHours()) {
			case FOLLOW_MASTER_REFERENCE :
				worktimeCode = this.convertWorkingHoursEmploymentStatus(command,
						personalWorkScheduleCreSet, basicWorkSetting.getWorkingCode().v());
				break;

			case PERSONAL_WORK_DAILY :
				worktimeCode = this.convertWorkingHoursPersonalWork(command, basicWorkSetting,
						personalWorkScheduleCreSet);
				break;
				
			case PERSONAL_DAY_OF_WEEK :
				worktimeCode = this.convertWorkingHoursPersonalDayofWeek(command, basicWorkSetting,
						personalWorkScheduleCreSet);
				break;
		}

		// check not exist data work
		if (!this.workTimeRepository.findByCode(command.getCompanyId(), worktimeCode).isPresent()) {
			this.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_591");
		} else {
			return this.getScheduleWorkHour(command, basicWorkSetting.getWorktypeCode().v(),
					worktimeCode);
		}
		return Optional.empty();
	}
	
	/**
	 * Convert working hours employment status.
	 *
	 * @return the string
	 */
	// 在職状態から「就業時間帯コードを変換する」に変更
	private String convertWorkingHoursEmploymentStatus(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet, String workTimeCode) {
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return workTimeCode;
		}
		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return DEFAULT_CODE;
		}

		return DEFAULT_CODE;
	}
	
	/**
	 * Convert working hours personal work.
	 *
	 * @return the string
	 */
	// 個人勤務日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalWork(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));
		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorkTimeZoneCodeInOffice(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return DEFAULT_CODE;
		}

		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the work time zone code in office.
	 *
	 * @return the work time zone code in office
	 */
	// 在職の「就業時間帯コード」を返す
	private String getWorkTimeZoneCodeInOffice(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<WorkType> optionalWorkType = this.workTypeRepository
				.findByPK(command.getCompanyId(), basicWorkSetting.getWorktypeCode().v());
		// check exist data
		if (optionalWorkType.isPresent()) {
			WorkType worktype = optionalWorkType.get();

			Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
					.findById(personalWorkScheduleCreSet.getEmployeeId(),
							GeneralDate.legacyDate(command.getToDate()));
			// check holiday work type
			if (this.checkHolidayWork(worktype.getDailyWork())
					&& this.checkHolidaySetting(worktype)) {
				if (this.checkExistWorkTimeCodeBySingleDaySchedule(optionalPersonalLaborCondition
						.get().getWorkCategory().getPublicHolidayWork())) {
					return this.getWorkTimeCodeBySingleDaySchedule(optionalPersonalLaborCondition
							.get().getWorkCategory().getPublicHolidayWork());
				}

				Optional<HolidayAtr> optionalHolidayAtr = this.getHolidayAtrWorkType(worktype);
				if (optionalHolidayAtr.isPresent()) {
					switch (optionalHolidayAtr.get()) {
						// case 法定内休日
						case STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									optionalPersonalLaborCondition.get().getWorkCategory()
											.getInLawBreakTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										optionalPersonalLaborCondition.get().getWorkCategory()
												.getInLawBreakTime());
							}

							break;
						// case 法定外休日
						case NON_STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									optionalPersonalLaborCondition.get().getWorkCategory()
											.getOutsideLawBreakTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										optionalPersonalLaborCondition.get().getWorkCategory()
												.getOutsideLawBreakTime());
							}
							break;
						// case 祝日
						default :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									optionalPersonalLaborCondition.get().getWorkCategory()
											.getHolidayAttendanceTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										optionalPersonalLaborCondition.get().getWorkCategory()
												.getHolidayAttendanceTime());
							}
							break;
					}

					// default work time code
					if (optionalPersonalLaborCondition.isPresent()) {
						return this.getHolidayWorkOfPersonalCondition(
								optionalPersonalLaborCondition.get());
					}
				}
			} else {
				if (this.checkDefaultWorkTimeCode(basicWorkSetting)) {
					return DEFAULT_CODE;
				}

				if (optionalPersonalLaborCondition.isPresent() && optionalPersonalLaborCondition
						.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent()) {
					return optionalPersonalLaborCondition.get().getWorkCategory().getWeekdayTime()
							.getWorkTimeCode().get().v();
				}
			}
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the holiday work of personal condition.
	 *
	 * @param personalLaborCondition the personal labor condition
	 * @return the holiday work of personal condition
	 */
	private String getHolidayWorkOfPersonalCondition(
			PersonalLaborCondition personalLaborCondition) {
		if (personalLaborCondition.getWorkCategory().getHolidayWork().getWorkTimeCode()
				.isPresent()) {
			return personalLaborCondition.getWorkCategory().getHolidayWork().getWorkTimeCode().get()
					.v();
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Check holiday setting.
	 *
	 * @param workType the work type
	 * @return true, if successful
	 */
	private boolean checkHolidaySetting(WorkType workType) {
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.OneDay)) {
			Optional<WorkTypeSet> optionalWorkTypeSet = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.OneDay))
					.findAny();
			if (optionalWorkTypeSet.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSet.get();
				return workTypeSet.getDigestPublicHd().equals(WorkTypeSetCheck.CHECK);
			}
		}
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
			Optional<WorkTypeSet> optionalWorkTypeSetMonring = workType.getWorkTypeSetList()
					.stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.Monring))
					.findAny();
			if (optionalWorkTypeSetMonring.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetMonring.get();
				return workTypeSet.getDigestPublicHd().equals(WorkTypeSetCheck.CHECK);
			}
			Optional<WorkTypeSet> optionalWorkTypeSetAfternoon = workType.getWorkTypeSetList()
					.stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.Afternoon))
					.findAny();
			if (optionalWorkTypeSetAfternoon.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetAfternoon.get();
				return workTypeSet.getDigestPublicHd().equals(WorkTypeSetCheck.CHECK);
			}
		}

		return false;
	}
	
	/**
	 * Gets the holiday atr work type.
	 *
	 * @param workType the work type
	 * @return the holiday atr work type
	 */
	private Optional<HolidayAtr> getHolidayAtrWorkType(WorkType workType) {
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.OneDay)) {
			Optional<WorkTypeSet> optionalWorkTypeSet = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.OneDay))
					.findAny();
			if (optionalWorkTypeSet.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSet.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
		}
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
			Optional<WorkTypeSet> optionalWorkTypeSetMonring = workType.getWorkTypeSetList()
					.stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.Monring))
					.findAny();
			if (optionalWorkTypeSetMonring.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetMonring.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
			Optional<WorkTypeSet> optionalWorkTypeSetAfternoon = workType.getWorkTypeSetList()
					.stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr().equals(WorkAtr.Afternoon))
					.findAny();
			if (optionalWorkTypeSetAfternoon.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetAfternoon.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
		}
		return Optional.empty();
	}
		
	
	/**
	 * Gets the work time zone code in office day of week.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the work time zone code in office day of week
	 */
	// 在職の「就業時間帯コード」を返す 2
	private String getWorkTimeZoneCodeInOfficeDayOfWeek(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		if (this.checkDefaultWorkTimeCode(basicWorkSetting)) {
			return DEFAULT_CODE;
		}
		Optional<WorkType> optional = this.workTypeRepository.findByPK(command.getCompanyId(),
				basicWorkSetting.getWorktypeCode().v());
		if (optional.isPresent()) {
			if (this.checkHolidayDailyWork(optional.get().getDailyWork())) {
				return DEFAULT_CODE;
			}
			return this.getWorkTimeCodeOfDayOfWeekPersonalCondition(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		return DEFAULT_CODE;
	}

	/**
	 * Gets the work time code of day of week personal condition.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the work time code of day of week personal condition
	 */
	private String getWorkTimeCodeOfDayOfWeekPersonalCondition(
			ScheduleCreatorExecutionCommand command, BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
				.findById(personalWorkScheduleCreSet.getEmployeeId(),
						GeneralDate.legacyDate(command.getToDate()));
		if (optionalPersonalLaborCondition.isPresent()) {
			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getFriday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getFriday());
			}

			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getMonday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getMonday());
			}

			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getSaturday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getSaturday());
			}

			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getSunday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getSunday());
			}

			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getThursday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getThursday());
			}
			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getTuesday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getTuesday());
			}
			if (this.checkExistWorkTimeCodeBySingleDaySchedule(
					optionalPersonalLaborCondition.get().getWorkDayOfWeek().getWednesday())) {
				return this.getWorkTimeCodeBySingleDaySchedule(
						optionalPersonalLaborCondition.get().getWorkDayOfWeek().getWednesday());
			}

		}
		return DEFAULT_CODE;
	}

	/**
	 * Check exist work time code by single day schedule.
	 *
	 * @param optionalSingleDaySchedule the optional single day schedule
	 * @return true, if successful
	 */
	private boolean checkExistWorkTimeCodeBySingleDaySchedule(
			Optional<SingleDaySchedule> optionalSingleDaySchedule) {
		return optionalSingleDaySchedule.isPresent()
				&& optionalSingleDaySchedule.get().getWorkTimeCode().isPresent();
	}
	
	/**
	 * Gets the work time code by single day schedule.
	 *
	 * @param optionalSingleDaySchedule the optional single day schedule
	 * @return the work time code by single day schedule
	 */
	private String getWorkTimeCodeBySingleDaySchedule(
			Optional<SingleDaySchedule> optionalSingleDaySchedule) {
		return optionalSingleDaySchedule.get().getWorkTimeCode().get().v();
	}
	
	/**
	 * Check default work time code.
	 *
	 * @param basicWorkSetting the basic work setting
	 * @return true, if successful
	 */
	private boolean checkDefaultWorkTimeCode(BasicWorkSetting basicWorkSetting) {
		if (StringUtil.isNullOrEmpty(basicWorkSetting.getWorkingCode().v(), false)
				|| basicWorkSetting.getWorkingCode().v().equals(DEFAULT_CODE)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check holiday daily work.
	 *
	 * @param dailyWork the daily work
	 * @return true, if successful
	 */
	// ? = 休日
	private boolean checkHolidayDailyWork(DailyWork dailyWork){
		if(dailyWork.getWorkTypeUnit().value == WorkTypeUnit.OneDay.value){
			return dailyWork.getOneDay().value == WorkTypeClassification.Holiday.value;
		}
		return (dailyWork.getMorning().value == WorkTypeClassification.HolidayWork.value
				|| dailyWork.getAfternoon().value == WorkTypeClassification.HolidayWork.value);
	}
	/**
	 * Convert working hours personal dayof week.
	 *
	 * @return the string
	 */
	// 個人曜日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalDayofWeek(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				GeneralDate.legacyDate(command.getToDate()));

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorkTimeZoneCodeInOfficeDayOfWeek(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the schedule break time.
	 *
	 * @param worktimeCode the work time code
	 * @return the schedule break time
	 */
	// 休憩予定時間帯を取得する
	// TO DO
	private void getScheduleBreakTime(ScheduleCreatorExecutionCommand command, String worktypeCode,
			String worktimeCode) {
		if (worktypeCode == null || worktypeCode.equals(DEFAULT_CODE)) {
			return;
		}

		Optional<WorkType> optionalWorktype = this.workTypeRepository
				.findByPK(command.getCompanyId(), worktypeCode);
		if (optionalWorktype.isPresent()) {
			WorkType workType = optionalWorktype.get();

			if (this.checkHolidayWork(workType.getDailyWork())) {

				Optional<WorkTime> optionalWorktime = this.workTimeRepository
						.findByCode(command.getCompanyId(), worktimeCode);

				if (optionalWorktime.isPresent()) {
					WorkTime workTime = optionalWorktime.get();
					if (WorkTimeDailyAtr.Enum_Regular_Work.value == workTime.getWorkTimeDivision()
							.getWorkTimeDailyAtr().value) {
						switch (workTime.getWorkTimeDivision().getWorkTimeMethodSet()) {
							case Enum_Fixed_Work :

								break;

							default :
								break;
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the confirmed atr.
	 *
	 * @param isConfirmContent the is confirm content
	 * @param confirmedAtr the confirmed atr
	 * @return the confirmed atr
	 */
	// 予定確定区分を取得
	private ConfirmedAtr getConfirmedAtr(boolean isConfirmContent, ConfirmedAtr confirmedAtr) {
		if (isConfirmContent) {
			return ConfirmedAtr.CONFIRMED;
		} else {
			return confirmedAtr;
		}
	}
	
	/**
	 * To command save.
	 *
	 * @param command the command
	 * @param employeeId the employee id
	 * @param worktypeCode the worktype code
	 * @param worktimeCode the worktime code
	 * @return the basic schedule save command
	 */
	private BasicScheduleSaveCommand toCommandSave(BasicScheduleSaveCommand command,
			String employeeId, String worktypeCode, String worktimeCode) {
		command.setWorktypeCode(worktypeCode);
		command.setEmployeeId(employeeId);
		command.setWorktimeCode(worktimeCode);
		command.setYmd(GeneralDate.today());
		return command;
	}

}


