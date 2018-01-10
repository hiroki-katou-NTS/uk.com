/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
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

/**
 * The Class ScheCreExeWorkTimeHandler.
 */
@Stateless
public class ScheCreExeWorkTimeHandler {
	
	/** The sche cre exe basic work setting handler. */
	@Inject
	private ScheCreExeBasicWorkSettingHandler scheCreExeBasicWorkSettingHandler;
		
	/** The sc employment status adapter. */
	@Inject
	private ScEmploymentStatusAdapter scEmploymentStatusAdapter;
	
	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
		
	/** The work time repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	/** The sche cre exe error log handler. */
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The work time set repository. */
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;
	
	/** The working condition item repository. */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	/** The working condition repository. */
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	/** The Constant INCUMBENT. */
	// 在職
	public static final int INCUMBENT = 1;
	
	/** The Constant LEAVE_OF_ABSENCE. */
	// 休職
	public static final int LEAVE_OF_ABSENCE = 2;
	
	/** The Constant HOLIDAY. */
	// 休業
	public static final int HOLIDAY = 3;
	
	/** The Constant BEFORE_JOINING. */
	// 入社前
	public static final int BEFORE_JOINING = 4;
	
	/** The Constant RETIREMENT. */
	// 退職
	public static final int RETIREMENT = 6;
	
	/** The Constant DEFAULT_CODE. */
	public static final String DEFAULT_CODE = "000";
	
	/** The Constant SHIFT1. */
	public static  final int SHIFT1 = 1;
	
	/** The Constant SHIFT2. */
	public static  final int SHIFT2 = 2;
	
	/**
	 * Gets the status employment.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the status employment
	 */
	// アルゴリズム (Employment)
	public EmploymentStatusDto getStatusEmployment(String employeeId, GeneralDate baseDate) {
		return this.scEmploymentStatusAdapter.getStatusEmployment(employeeId, baseDate);
	}
	
	/**
	 * Gets the worktime.
	 *
	 * @param command the command
	 * @return the worktime
	 */
	// 就業時間帯を取得する
	public Optional<String> getWorktime(WorkTimeGetterCommand command) {

		Optional<BasicWorkSetting> optionalBasicWorkSetting = this.scheCreExeBasicWorkSettingHandler
				.getBasicWorkSetting(command.toBasicWorkSetting());

		if (optionalBasicWorkSetting.isPresent()) {
			WorkTimeZoneGetterCommand commandGetter = command.toWorkTimeZone();
			commandGetter.setWorkTypeCode(optionalBasicWorkSetting.get().getWorktypeCode().v());
			commandGetter.setWorkingCode(optionalBasicWorkSetting.get().getWorkingCode().v());
			return this.getWorkingTimeZoneCode(commandGetter);
		}
		return Optional.empty();
	}
	
	/**
	 * Check holiday work.
	 *
	 * @param dailyWork the daily work
	 * @return true, if successful
	 */
	// ? = 休日出勤
	public boolean checkHolidayWork(DailyWork dailyWork) {
		if (dailyWork.getWorkTypeUnit().value == WorkTypeUnit.OneDay.value) {
			return dailyWork.getOneDay().value == WorkTypeClassification.HolidayWork.value;
		}
		return (dailyWork.getMorning().value == WorkTypeClassification.HolidayWork.value
				|| dailyWork.getAfternoon().value == WorkTypeClassification.HolidayWork.value);
	}
	
	/**
	 * Check null or defaul code.
	 *
	 * @param workingCode the working code
	 * @return true, if successful
	 */
	public boolean checkNullOrDefaulCode(String workingCode) {
		return StringUtil.isNullOrEmpty(workingCode, false) || workingCode.equals(DEFAULT_CODE);
	}
	

	
	/**
	 * Gets the labor condition item.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the labor condition item
	 */
	// 労働条件項目を取得する
	public Optional<WorkingConditionItem> getLaborConditionItem(String companyId, String employeeId,
			GeneralDate baseDate) {
		Optional<WorkingCondition> optionalWorkingCondition = this.workingConditionRepository
				.getBySid(companyId, employeeId);
		if (!optionalWorkingCondition.isPresent()) {
			return Optional.empty();
		}

		// return data by call repository
		return this.workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
	}
	
	/**
	 * Gets the work time code of day of week personal condition.
	 *
	 * @param command the command
	 * @param workingConditionItem the working condition item
	 * @return the work time code of day of week personal condition
	 */
	public String getWorkTimeCodeOfDayOfWeekPersonalCondition(WorkTimeConvertCommand command,
			WorkingConditionItem workingConditionItem) {

		// get MONDAY of data
		if (DayOfWeek.MONDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getMonday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getMonday());
		}
		// get TUESDAY of data
		if (DayOfWeek.TUESDAY.getValue() ==  command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getTuesday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getTuesday());
		}
		// get WEDNESDAY of data
		if (DayOfWeek.WEDNESDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getWednesday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getWednesday());
		}
		// get THURSDAY of data
		if (DayOfWeek.THURSDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getThursday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getThursday());
		}
		// get FRIDAY of data
		if (DayOfWeek.FRIDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getFriday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getFriday());
		}
		// get SATURDAY of data
		if (DayOfWeek.SATURDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getSaturday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getSaturday());
		}
		// get SUNDAY of data
		if (DayOfWeek.SUNDAY.getValue() == command.getBaseGetter().getToDate().dayOfWeek() && this
				.checkExistWorkTimeCodeBySingleDaySchedule(workingConditionItem.getWorkDayOfWeek().getSunday())) {
			return this.getWorkTimeCodeBySingleDaySchedule(
					workingConditionItem.getWorkDayOfWeek().getSunday());
		}
		return DEFAULT_CODE;

	}
	
	/**
	 * Convert working hours employment status.
	 *
	 * @param command the command
	 * @return the string
	 */
	// 在職状態から「就業時間帯コードを変換する」に変更
	private String convertWorkingHoursEmploymentStatus(WorkTimeConvertCommand command) {

		// get employment status by id
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(command.getEmployeeId(),
				command.getBaseGetter().getToDate());

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return command.getWorkingCode();
		}
		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return null;
		}
		throw (new BusinessException("Employment status not found"));
	}
	
	/**
	 * Check holiday setting.
	 *
	 * @param workType the work type
	 * @return true, if successful
	 */
	private boolean checkHolidaySetting(WorkType workType) {
		
		// check daily work by one day
		if (workType.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {
			
			// find work type set by work day one day
			Optional<WorkTypeSet> optionalWorkTypeSet = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.OneDay).findAny();
			
			// check exist data
			if (optionalWorkTypeSet.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSet.get();
				
				// return by check
				return workTypeSet.getDigestPublicHd() == WorkTypeSetCheck.CHECK;
			}
		}
		
		// check daily work is morning or after noon 
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
			
			// find work type set by work day morning
			Optional<WorkTypeSet> optionalWorkTypeSetMonring = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.Monring).findAny();
			
			// check exist data by find
			if (optionalWorkTypeSetMonring.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetMonring.get();
				
				// return by check
				return workTypeSet.getDigestPublicHd() == WorkTypeSetCheck.CHECK;
			}
			
			// find work type set by work day afternoon
			Optional<WorkTypeSet> optionalWorkTypeSetAfternoon = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.Afternoon).findAny();
			
			// check exist data by find
			if (optionalWorkTypeSetAfternoon.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetAfternoon.get();
				
				// return by check
				return workTypeSet.getDigestPublicHd() == WorkTypeSetCheck.CHECK;
			}
		}

		return false;
	}
	
	/**
	 * Check exist work time code by single day schedule.
	 *
	 * @param optionalSingleDaySchedule the optional single day schedule
	 * @return true, if successful
	 */
	private boolean checkExistWorkTimeCodeBySingleDaySchedule(Optional<SingleDaySchedule> optionalSingleDaySchedule) {
		return optionalSingleDaySchedule.isPresent() && optionalSingleDaySchedule.get().getWorkTimeCode().isPresent();
	}
	
	
	/**
	 * Gets the work time code by single day schedule.
	 *
	 * @param optionalSingleDaySchedule the optional single day schedule
	 * @return the work time code by single day schedule
	 */
	private String getWorkTimeCodeBySingleDaySchedule(Optional<SingleDaySchedule> optionalSingleDaySchedule) {
		return optionalSingleDaySchedule.get().getWorkTimeCode().get().v();
	}
	
	/**
	 * Gets the holiday work of personal condition.
	 *
	 * @param workingConditionItem the working condition item
	 * @return the holiday work of personal condition
	 */
	private String getHolidayWorkOfWorkingConditionItem(WorkingConditionItem workingConditionItem) {
		if (workingConditionItem.getWorkCategory().getHolidayWork().getWorkTimeCode().isPresent()) {
			return workingConditionItem.getWorkCategory().getHolidayWork().getWorkTimeCode().get().v();
		}
		return DEFAULT_CODE;
	}
	
	/**
	 * Gets the holiday atr work type.
	 *
	 * @param workType the work type
	 * @return the holiday atr work type
	 */
	private Optional<HolidayAtr> getHolidayAtrWorkType(WorkType workType) {
		
		// check daily work by one day
		if (workType.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {

			// find work type set by one day 
			Optional<WorkTypeSet> optionalWorkTypeSet = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.OneDay).findAny();
			
			// check exist data work type set
			if (optionalWorkTypeSet.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSet.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
		}
		
		// check daily work by morning afternoon
		if (workType.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon) {
			
			// find work type set by morning
			Optional<WorkTypeSet> optionalWorkTypeSetMonring = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.Monring).findAny();
			
			// check exist data work type set
			if (optionalWorkTypeSetMonring.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetMonring.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
			
			// find work type set by afternoon
			Optional<WorkTypeSet> optionalWorkTypeSetAfternoon = workType.getWorkTypeSetList().stream()
					.filter(worktypeSet -> worktypeSet.getWorkAtr() == WorkAtr.Afternoon).findAny();
			
			// check exist data work type set
			if (optionalWorkTypeSetAfternoon.isPresent()) {
				WorkTypeSet workTypeSet = optionalWorkTypeSetAfternoon.get();
				return Optional.ofNullable(workTypeSet.getHolidayAtr());
			}
		}
		
		return Optional.empty();
	}
		
	/**
	 * Gets the work time zone code in office.
	 *
	 * @param command the command
	 * @param workingConditionItem the working condition item
	 * @return the work time zone code in office
	 */
	// 在職の「就業時間帯コード」を返す
	private String getWorkTimeZoneCodeInOffice(WorkTimeConvertCommand command) {

		// find work type by id
		Optional<WorkType> optionalWorkType = this.workTypeRepository.findByPK(command.getBaseGetter().getCompanyId(),
				command.getWorkTypeCode());

		Optional<WorkingConditionItem> optionalWorkingConditionItem = this.getLaborConditionItem(
				command.getBaseGetter().getCompanyId(), command.getEmployeeId(), command.getBaseGetter().getToDate());
		// check exist data
		if (optionalWorkType.isPresent() && optionalWorkingConditionItem.isPresent()) {
			WorkType worktype = optionalWorkType.get();
			WorkingConditionItem workingConditionItem = optionalWorkingConditionItem.get();

			
			// check holiday work type
			if (this.checkHolidayWork(worktype.getDailyWork()) && this.checkHolidaySetting(worktype)) {

				// check exist work time
				if (this.checkExistWorkTimeCodeBySingleDaySchedule(
						workingConditionItem.getWorkCategory().getPublicHolidayWork())) {
					return this.getWorkTimeCodeBySingleDaySchedule(
							workingConditionItem.getWorkCategory().getPublicHolidayWork());
				}

				// get holiday atr work type
				Optional<HolidayAtr> optionalHolidayAtr = this.getHolidayAtrWorkType(worktype);

				// check exist data
				if (optionalHolidayAtr.isPresent()) {
					switch (optionalHolidayAtr.get()) {
						// case 法定内休日
						case STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									workingConditionItem.getWorkCategory().getInLawBreakTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										workingConditionItem.getWorkCategory().getInLawBreakTime());
							}

							break;
						// case 法定外休日
						case NON_STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									workingConditionItem.getWorkCategory().getOutsideLawBreakTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										workingConditionItem.getWorkCategory().getOutsideLawBreakTime());
							}
							break;
						// case 祝日
						default :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									workingConditionItem.getWorkCategory().getHolidayAttendanceTime())) {
								return this.getWorkTimeCodeBySingleDaySchedule(
										workingConditionItem.getWorkCategory().getHolidayAttendanceTime());
							}
							break;
					}

					// default work time code
					return this.getHolidayWorkOfWorkingConditionItem(workingConditionItem);
				}
			} else {

				// check default code by working code
				if (command.getWorkingCode() == null) {
					return null;
				}

				// check exist data work category of week day
				if (workingConditionItem.getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent()) {
					return workingConditionItem.getWorkCategory().getWeekdayTime().getWorkTimeCode().get().v();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Convert working hours personal work.
	 *
	 * @param command the command
	 * @return the string
	 */
	// 個人勤務日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalWork(WorkTimeConvertCommand command) {
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(command.getEmployeeId(),
				command.getBaseGetter().getToDate());
		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorkTimeZoneCodeInOffice(command);
		}
		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == HOLIDAY
				|| employmentStatus.getStatusOfEmployment() == LEAVE_OF_ABSENCE) {
			return null;
		}
		throw (new BusinessException("Employment status not found"));
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
	 * Gets the work time zone code in office day of week.
	 *
	 * @param command the command
	 * @return the work time zone code in office day of week
	 */
	// 在職の「就業時間帯コード」を返す 2
	private String getWorkTimeZoneCodeInOfficeDayOfWeek(WorkTimeConvertCommand command) {

		// check default work time code by basic work setting
		if (command.getWorkingCode() == null) {
			return null;
		}
		
		// find work type by id
		Optional<WorkType> optional = this.workTypeRepository.findByPK(command.getBaseGetter().getCompanyId(),
				command.getWorkTypeCode());
		
		// check exist data work type
		if (optional.isPresent()) {
			if (this.checkHolidayDailyWork(optional.get().getDailyWork())) {
				return null;
			}
			
			Optional<WorkingConditionItem> optionalWorkingConditionItem = this.getLaborConditionItem(
					command.getBaseGetter().getCompanyId(), command.getEmployeeId(),
					command.getBaseGetter().getToDate());

			if (!optionalWorkingConditionItem.isPresent()) {
				return null;
			}
			return this.getWorkTimeCodeOfDayOfWeekPersonalCondition(command, optionalWorkingConditionItem.get());
		}
		return null;
	}
	
	/**
	 * Convert working hours personal dayof week.
	 *
	 * @param command the command
	 * @return the string
	 */
	// 個人曜日別と在職状態から「就業時間帯コード」を変換する
	private String convertWorkingHoursPersonalDayofWeek(WorkTimeConvertCommand command) {

		// get employment status by id
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(command.getEmployeeId(),
				command.getBaseGetter().getToDate());

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorkTimeZoneCodeInOfficeDayOfWeek(command);
		}
		return null;
	}

	/**
	 * Checks if is use second work.
	 *
	 * @param workTimeSet the work time set
	 * @return true, if is use second work
	 */
	private boolean isUseSecondWork(WorkTimeSet workTimeSet) {
		// get time zone
		List<Timezone> timezones = workTimeSet.getPrescribedTimezoneSetting().getTimezone();
		
		// check time shift 2
		for (Timezone item : timezones) {
			if (item.getWorkNo() == SHIFT2) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check end in shift 2.
	 *
	 * @param workTimeSet the work time set
	 * @return true, if successful
	 */
	private boolean checkEndInShift2(WorkTimeSet workTimeSet) {
		
		// get time morning end
		int morningEndTime = workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime().getDayTime();
		
		// get shift2
		Timezone shift2 = workTimeSet.getPrescribedTimezoneSetting().getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT2).findFirst().get();
		
		// get time start shift 2
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
		
		// work time set update morning
		if (this.checkEndInShift2(workTimeSet)) {
			
			// update end time shift 1 and remove shift 2
			workTimeSet.updateEndTimeShift1(workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
			workTimeSet.removeShift2();
		} else {
			
			// update end time shift 2
			workTimeSet.updateEndTimeShift2(workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
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
		
		// get afternoon start time
		int afternoonStartTime = workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime().getDayTime();
		
		// get time shift 1
		Timezone shift1 = workTimeSet.getPrescribedTimezoneSetting().getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT1).findFirst().get();
		
		// get end time shift 1
		int endTimeShift1 = shift1.getEnd().getDayTime();
		
		// check afternoon start time <= end time shift 1
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
		
		// check start in shift 1
		if (this.checkStartInShift1(workTimeSet)) {
			
			// update start time shift 1
			workTimeSet.updateStartTimeShift1(workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
		} else {
			
			// update start time shift 2 and remove shift 1
			workTimeSet.updateStartTimeShift2(workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
			workTimeSet.removeShift1();
		}
		return workTimeSet;
	}
	
	/**
	 * Gets the time zone.
	 *
	 * @param command the command
	 * @return the time zone
	 */
	// 変換した時間帯を返す
	private Optional<WorkTimeSet> getTimeZone(WorkTimeSetGetterCommand command) {

		// 所定時間帯を取得する
		WorkTimeSet workTimeSet = this.workTimeSetRepository
				.findByCode(command.getCompanyId(), command.getWorkingCode()).get();
		// 出勤休日区分を判断
		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(command.getWorktypeCode());
		// check work style
		if (workStyle.equals(WorkStyle.MORNING_WORK)) {
			if (this.isUseSecondWork(workTimeSet)) {
				workTimeSet = this.updateTimeMorning(workTimeSet);
			} else {
				// update time shift 1 to end time morning
				workTimeSet.updateEndTimeShift1(workTimeSet.getPrescribedTimezoneSetting().getMorningEndTime());
				workTimeSet.removeShift2();
			}
		} else {// if AFTERNOON_WORK
			if (this.isUseSecondWork(workTimeSet)) {
				workTimeSet = this.updateTimeAfternoon(workTimeSet);
			} else {
				// update time shift 1 and remove shift 2
				workTimeSet.updateStartTimeShift1(workTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());
				workTimeSet.removeShift2();
			}
		}
		return Optional.of(workTimeSet);
	}
	
	/**
	 * Gets the schedule work hour.
	 *
	 * @param command the command
	 * @return the schedule work hour
	 */
	// 勤務予定時間帯を取得する
	public Optional<WorkTimeSet> getScheduleWorkHour(WorkTimeSetGetterCommand command) {

		// call service check work day
		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(command.getWorktypeCode());
		switch (workStyle) {
			case ONE_DAY_REST :
				break;
			case ONE_DAY_WORK :
				Optional<WorkTimeSet> optionalWorkTimeSet = this.workTimeSetRepository
						.findByCode(command.getCompanyId(), command.getWorkingCode());
				if (optionalWorkTimeSet.isPresent()) {
					return Optional.of(optionalWorkTimeSet.get());
				}
				break;
			default :
				// morning or afternoon
				return this.getTimeZone(command);
		}
		return Optional.empty();
	}

	/**
	 * Gets the working time zone code.
	 *
	 * @param command the command
	 * @return the working time zone code
	 */
	// 在職状態に対応する「就業時間帯コード」を取得する
	private Optional<String> getWorkingTimeZoneCode(WorkTimeZoneGetterCommand command) {

		String worktimeCode = null;

		// check reference working hours
		if (command.getReferenceWorkingHours() == TimeZoneScheduledMasterAtr.FOLLOW_MASTER_REFERENCE.value) {
			worktimeCode = this.convertWorkingHoursEmploymentStatus(command.toWorkTimeConvert());
		}

		if (command.getReferenceWorkingHours() == TimeZoneScheduledMasterAtr.PERSONAL_WORK_DAILY.value) {
			worktimeCode = this.convertWorkingHoursPersonalWork(command.toWorkTimeConvert());
		}

		if (command.getReferenceWorkingHours() == TimeZoneScheduledMasterAtr.PERSONAL_DAY_OF_WEEK.value) {
			worktimeCode = this.convertWorkingHoursPersonalDayofWeek(command.toWorkTimeConvert());

		}

		// check work time code null
		if(worktimeCode == null){
			return Optional.of(worktimeCode);
		}
		
		// check not exist data work
		if (!this.workTimeRepository.findByCode(command.getBaseGetter().getCompanyId(), worktimeCode).isPresent()) {

			// add error message 591
			this.scheCreExeErrorLogHandler.addError(command.getBaseGetter(), command.getEmployeeId(), "Msg_591");
		} else {
			return Optional.of(worktimeCode);
		}
		return Optional.empty();
	}

}
