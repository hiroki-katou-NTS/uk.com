/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.employeeinfo.PersonalWorkScheduleCreSet;
import nts.uk.ctx.at.schedule.dom.employeeinfo.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

/**
 * The Class ScheCreExeWorkTypeHandler.
 */
@Stateless
public class ScheCreExeWorkTypeHandler {
	
	/** The sche cre exe basic work setting handler. */
	@Inject
	private ScheCreExeBasicWorkSettingHandler scheCreExeBasicWorkSettingHandler;
	
	/** The sche cre exe work time handler. */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	
	/** The sche cre exe error log handler. */
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;
	
	/** The personal labor condition repository. */
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	/** The sche cre exe basic schedule handler. */
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;
	
	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;
	
	
	/**
	 * Creates the work schedule.
	 *
	 * @param command the command
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 */
	// 営業日カレンダーで勤務予定を作成する
	public void createWorkSchedule(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		Optional<String> optionalWorktypeCode = this.getWorktype(command, personalWorkScheduleCreSet);

		if (optionalWorktypeCode.isPresent()) {

			String workTypeCode = optionalWorktypeCode.get();

			Optional<Object> optionalWorkTimeObj = this.scheCreExeWorkTimeHandler.getWorktime(command, workTypeCode,
					personalWorkScheduleCreSet);

			// object return is String
			if (optionalWorkTimeObj.isPresent() && optionalWorkTimeObj.get() instanceof String) {
				// update all basic schedule
				this.scheCreExeBasicScheduleHandler.updateAllDataToCommandSave(command,
						personalWorkScheduleCreSet.getEmployeeId(), workTypeCode, optionalWorkTimeObj.get().toString(),
						null);
			}

			// object return is WorkTimeSet
			if (optionalWorkTimeObj.isPresent() && optionalWorkTimeObj.get() instanceof WorkTimeSet) {
				WorkTimeSet workTimeSet = (WorkTimeSet) optionalWorkTimeObj.get();
				// update all basic schedule
				this.scheCreExeBasicScheduleHandler.updateAllDataToCommandSave(command,
						personalWorkScheduleCreSet.getEmployeeId(), workTypeCode, workTimeSet.getSiftCD(), workTimeSet);
			}
		}

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
	private String getWorktypeCodeInOffice(ScheduleCreatorExecutionCommand command, BasicWorkSetting basicWorkSetting,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// check default work time code
		if (this.scheCreExeWorkTimeHandler.checkDefaultWorkTimeCode(basicWorkSetting)) {
			return basicWorkSetting.getWorktypeCode().v();
		} else {

			// find work time code by day of week
			String worktimeCode = this.scheCreExeWorkTimeHandler.getWorkTimeCodeOfDayOfWeekPersonalCondition(command,
					basicWorkSetting, personalWorkScheduleCreSet);

			// check default work time code
			if (this.scheCreExeWorkTimeHandler.checkNullOrDefaulCode(worktimeCode)) {
				return basicWorkSetting.getWorktypeCode().v();
			}

			// find personal condition by id
			Optional<PersonalLaborCondition> optionalPersonalLaborCondition = this.personalLaborConditionRepository
					.findById(personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());

			// check exist data personal condition
			if (optionalPersonalLaborCondition.isPresent()) {

				// return work type code by holiday time
				return optionalPersonalLaborCondition.get().getWorkCategory().getHolidayTime().getWorkTypeCode().v();
			}
		}

		return ScheCreExeWorkTimeHandler.DEFAULT_CODE;
	}
	
	/**
	 * Gets the worktype code leave holiday type.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @param employmentStatus the employment status
	 * @return the worktype code leave holiday type
	 */
	// 休業休職の勤務種類コードを返す
	private String getWorktypeCodeLeaveHolidayType(ScheduleCreatorExecutionCommand command,
			BasicWorkSetting basicWorkSetting, PersonalWorkScheduleCreSet personalWorkScheduleCreSet,
			EmploymentStatusDto employmentStatus) {
		WorkStyle workStyle = this.basicScheduleService.checkWorkDay(basicWorkSetting.getWorktypeCode().v());
		if (workStyle.equals(WorkStyle.ONE_DAY_REST)) {
			return basicWorkSetting.getWorktypeCode().v();
		}
		// find work type
		WorkType worktype = this.workTypeRepository
				.findByPK(command.getCompanyId(), basicWorkSetting.getWorktypeCode().v()).get();

		if (this.scheCreExeWorkTimeHandler.checkHolidayWork(worktype.getDailyWork())) {
			// 休日出勤
			return basicWorkSetting.getWorktypeCode().v();
		} else {
			
			// find work type set by close atr employment status 
			List<WorkTypeSet> worktypeSets = this.workTypeRepository.findWorkTypeSetCloseAtr(command.getCompanyId(),
					employmentStatus.getLeaveHolidayType());

			// check empty work type set
			if (CollectionUtil.isEmpty(worktypeSets)) {
				
				// add message error log 601
				this.scheCreExeErrorLogHandler.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_601");
				return ScheCreExeWorkTimeHandler.DEFAULT_CODE;
			}
			return worktypeSets.get(FIRST_DATA).getWorkTypeCd().v();
		}
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
		EmploymentStatusDto employmentStatus = this.scheCreExeWorkTimeHandler.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());

		// employment status is INCUMBENT
		if (employmentStatus.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.INCUMBENT) {
			return this.getWorktypeCodeInOffice(command, basicWorkSetting,
					personalWorkScheduleCreSet);
		}

		// employment status is HOLIDAY or LEAVE_OF_ABSENCE
		if (employmentStatus.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.HOLIDAY
				|| employmentStatus
						.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.LEAVE_OF_ABSENCE) {
			return this.getWorktypeCodeLeaveHolidayType(command, basicWorkSetting,
					personalWorkScheduleCreSet, employmentStatus);
		}
		return ScheCreExeWorkTimeHandler.DEFAULT_CODE;
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
		EmploymentStatusDto employmentStatus = this.scheCreExeWorkTimeHandler.getStatusEmployment(
				personalWorkScheduleCreSet.getEmployeeId(), command.getToDate());

		// employment status is 在職
		if (employmentStatus.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.INCUMBENT) {
			return basicWorkSetting.getWorktypeCode().v();
		}

		// employment status is 休業 or 休職
		if (employmentStatus.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.HOLIDAY
				|| employmentStatus
						.getStatusOfEmployment() == ScheCreExeWorkTimeHandler.LEAVE_OF_ABSENCE) {
			return this.getWorktypeCodeLeaveHolidayType(command, basicWorkSetting,
					personalWorkScheduleCreSet, employmentStatus);
		}
		return ScheCreExeWorkTimeHandler.DEFAULT_CODE;
	}
	/**
	 * Gets the worktype code.
	 *
	 * @param command the command
	 * @param basicWorkSetting the basic work setting
	 * @param personalWorkScheduleCreSet the personal work schedule cre set
	 * @return the worktype code
	 */
	// 勤務種類を取得する
	private Optional<String> getWorktype(ScheduleCreatorExecutionCommand command,
			PersonalWorkScheduleCreSet personalWorkScheduleCreSet) {

		// get basic work setting.
		Optional<BasicWorkSetting> optionalBasicWorkSetting = this.scheCreExeBasicWorkSettingHandler
				.getBasicWorkSetting(command, personalWorkScheduleCreSet);

		if (optionalBasicWorkSetting.isPresent()) {
			BasicWorkSetting basicWorkSetting = optionalBasicWorkSetting.get();
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

			// get work type by code
			Optional<WorkType> optionalWorktype = this.workTypeRepository.findByPK(command.getCompanyId(),
					worktypeCode);

			if (optionalWorktype.isPresent()) {
				return Optional.of(worktypeCode);
			} else {
				// add error log message 590
				this.scheCreExeErrorLogHandler.addError(command, personalWorkScheduleCreSet.getEmployeeId(), "Msg_590");
			}
		}

		return Optional.empty();
	}

}
