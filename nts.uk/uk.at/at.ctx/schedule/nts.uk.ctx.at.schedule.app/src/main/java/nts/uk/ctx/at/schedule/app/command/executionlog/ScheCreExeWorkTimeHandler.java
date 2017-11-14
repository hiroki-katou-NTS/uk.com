/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
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
	
	/** The personal labor condition repository. */
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	/** The work time repository. */
	@Inject
	private WorkTimeRepository workTimeRepository;
	
	/** The sche cre exe error log handler. */
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The work time set repository. */
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;
	
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
	 * Convert working hours employment status.
	 *
	 * @return the string
	 */
	// 在職状態から「就業時間帯コードを変換する」に変更
	private String convertWorkingHoursEmploymentStatus(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet, String workTimeCode) {
		EmploymentStatusDto employmentStatus = this.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(),
				command.getToDate());

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
	 * Gets the work time zone code in office.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
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
							command.getToDate());
			
			if (!optionalPersonalLaborCondition.isPresent()) {
				return DEFAULT_CODE;
			}
			PersonalLaborCondition personalLaborCondition = null;
			if (optionalPersonalLaborCondition.isPresent()) {
				personalLaborCondition = optionalPersonalLaborCondition.get();
			}
			
			
			// check holiday work type
			if (this.checkHolidayWork(worktype.getDailyWork())
					&& this.checkHolidaySetting(worktype)) {
				if (this.checkExistWorkTimeCodeBySingleDaySchedule(
						personalLaborCondition.getWorkCategory().getPublicHolidayWork())) {
					return this.getWorkTimeCodeBySingleDaySchedule(
							personalLaborCondition.getWorkCategory().getPublicHolidayWork());
				}

				Optional<HolidayAtr> optionalHolidayAtr = this.getHolidayAtrWorkType(worktype);
				if (optionalHolidayAtr.isPresent()) {
					switch (optionalHolidayAtr.get()) {
						// case 法定内休日
						case STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									personalLaborCondition.getWorkCategory().getInLawBreakTime())) {
								return this
										.getWorkTimeCodeBySingleDaySchedule(personalLaborCondition
												.getWorkCategory().getInLawBreakTime());
							}

							break;
						// case 法定外休日
						case NON_STATUTORY_HOLIDAYS :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									personalLaborCondition.getWorkCategory()
											.getOutsideLawBreakTime())) {
								return this
										.getWorkTimeCodeBySingleDaySchedule(personalLaborCondition
												.getWorkCategory().getOutsideLawBreakTime());
							}
							break;
						// case 祝日
						default :
							if (this.checkExistWorkTimeCodeBySingleDaySchedule(
									personalLaborCondition.getWorkCategory()
											.getHolidayAttendanceTime())) {
								return this
										.getWorkTimeCodeBySingleDaySchedule(personalLaborCondition
												.getWorkCategory().getHolidayAttendanceTime());
							}
							break;
					}

					// default work time code
					return this.getHolidayWorkOfPersonalCondition(personalLaborCondition);
				}
			} else {
				if (this.checkDefaultWorkTimeCode(basicWorkSetting)) {
					return DEFAULT_CODE;
				}

				if (personalLaborCondition.getWorkCategory().getWeekdayTime().getWorkTimeCode()
						.isPresent()) {
					return personalLaborCondition.getWorkCategory().getWeekdayTime()
							.getWorkTimeCode().get().v();
				}
			}
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
				command.getToDate());
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
	 * Check default work time code.
	 *
	 * @param basicWorkSetting the basic work setting
	 * @return true, if successful
	 */
	public boolean checkDefaultWorkTimeCode(BasicWorkSetting basicWorkSetting) {
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
	 * Gets the work time code of day of week personal condition.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the work time code of day of week personal condition
	 */
	public String getWorkTimeCodeOfDayOfWeekPersonalCondition(
			ScheduleCreatorExecutionCommand command, BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
				.findById(personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());
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
				command.getToDate());

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == INCUMBENT) {
			return this.getWorkTimeZoneCodeInOfficeDayOfWeek(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}
		return DEFAULT_CODE;
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

			// add error message 591 
			this.scheCreExeErrorLogHandler.addError(command,
					personalWorkScheduleCreSet.getEmployeeId(), "Msg_591");
		} else {
			return this.getScheduleWorkHour(command, basicWorkSetting.getWorktypeCode().v(),
					worktimeCode);
		}
		return Optional.empty();
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
	public Optional<Object> getWorktime(ScheduleCreatorExecutionCommand command,
			String worktypeCode, PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {
		Optional<BasicWorkSetting> optionalBasicWorkSetting = this.scheCreExeBasicWorkSettingHandler
				.getBasicWorkSetting(command, personalWorkScheduleCreSet);

		if (optionalBasicWorkSetting.isPresent()) {
			return this.getWorkingTimeZoneCode(command, optionalBasicWorkSetting.get(),
					personalWorkScheduleCreSet);
		}
		return Optional.empty();
	}
	
	/**
	 * Check null or defaul code.
	 *
	 * @param code the code
	 * @return true, if successful
	 */
	public boolean checkNullOrDefaulCode(String code) {
		return (code == null || DEFAULT_CODE.equals(code));
	}

}
