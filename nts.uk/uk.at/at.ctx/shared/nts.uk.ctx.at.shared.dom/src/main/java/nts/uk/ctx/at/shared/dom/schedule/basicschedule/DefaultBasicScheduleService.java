/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DefaultBasicScheduleService implements BasicScheduleService {

	@Inject
	public WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	/**
	 *  就業時間帯の必須チェック
	 */
	@Override
	public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> workType = workTypeRepo.findByPK(companyId, workTypeCode);

		if (!workType.isPresent()) {
			return SetupType.OPTIONAL;
		}
		DailyWork dailyWork = workType.get().getDailyWork();
		WorkTypeUnit workTypeUnit = dailyWork.getWorkTypeUnit();
		// All day
		if (WorkTypeUnit.OneDay == workTypeUnit) {

			WorkTypeClassification workTypeClass = dailyWork.getOneDay();

			if (WorkTypeClassification.AnnualHoliday == workTypeClass
					|| WorkTypeClassification.YearlyReserved == workTypeClass
					|| WorkTypeClassification.SubstituteHoliday == workTypeClass
					|| WorkTypeClassification.Absence == workTypeClass
					|| WorkTypeClassification.SpecialHoliday == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation == workTypeClass) {

				return this.checkRequiredOfInputType(workTypeClass);
			}

			if (WorkTypeClassification.Attendance == workTypeClass
					|| WorkTypeClassification.HolidayWork == workTypeClass
					|| WorkTypeClassification.Shooting == workTypeClass) {
				return SetupType.REQUIRED;
			}

			if (WorkTypeClassification.AnnualHoliday == workTypeClass
					|| WorkTypeClassification.YearlyReserved == workTypeClass
					|| WorkTypeClassification.SpecialHoliday == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation == workTypeClass
					|| WorkTypeClassification.ContinuousWork == workTypeClass
					|| WorkTypeClassification.Closure == workTypeClass
					|| WorkTypeClassification.LeaveOfAbsence == workTypeClass) {
				return SetupType.OPTIONAL;
			}

			if (WorkTypeClassification.Holiday == workTypeClass || WorkTypeClassification.Absence == workTypeClass
					|| WorkTypeClassification.Pause == workTypeClass) {
				return SetupType.NOT_REQUIRED;
			}
		}

		// Half day
		if (WorkTypeUnit.MonringAndAfternoon == workTypeUnit) {
			WorkStyle workStyle = this.checkWorkDay(workTypeCode);
			if (WorkStyle.ONE_DAY_REST == workStyle) {

				SetupType morningWorkStyle = this.checkRequiredOfInputType(dailyWork.getMorning());
				SetupType afternoonWorkStyle = this.checkRequiredOfInputType(dailyWork.getAfternoon());

				return this.checkRequired(morningWorkStyle, afternoonWorkStyle);
			} else {
				return SetupType.REQUIRED;
			}
		}
		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}
	
	@Override
	public SetupType checkNeedWorkTimeSetByList(String workTypeCode, List<WorkType> listWorkType) {
		Optional<WorkType> workType = listWorkType.stream()
				.filter(x -> x.getWorkTypeCode().v().equals(workTypeCode)).findFirst();

		if (!workType.isPresent()) {
			return SetupType.OPTIONAL;
		}
		DailyWork dailyWork = workType.get().getDailyWork();
		WorkTypeUnit workTypeUnit = dailyWork.getWorkTypeUnit();
		// All day
		if (WorkTypeUnit.OneDay == workTypeUnit) {

			WorkTypeClassification workTypeClass = dailyWork.getOneDay();

			if (WorkTypeClassification.AnnualHoliday == workTypeClass
					|| WorkTypeClassification.YearlyReserved == workTypeClass
					|| WorkTypeClassification.SubstituteHoliday == workTypeClass
					|| WorkTypeClassification.Absence == workTypeClass
					|| WorkTypeClassification.SpecialHoliday == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation == workTypeClass) {

				return this.checkRequiredOfInputType(workTypeClass);
			}

			if (WorkTypeClassification.Attendance == workTypeClass
					|| WorkTypeClassification.HolidayWork == workTypeClass
					|| WorkTypeClassification.Shooting == workTypeClass) {
				return SetupType.REQUIRED;
			}

			if (WorkTypeClassification.AnnualHoliday == workTypeClass
					|| WorkTypeClassification.YearlyReserved == workTypeClass
					|| WorkTypeClassification.SpecialHoliday == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation == workTypeClass
					|| WorkTypeClassification.ContinuousWork == workTypeClass
					|| WorkTypeClassification.Closure == workTypeClass
					|| WorkTypeClassification.LeaveOfAbsence == workTypeClass) {
				return SetupType.OPTIONAL;
			}

			if (WorkTypeClassification.Holiday == workTypeClass || WorkTypeClassification.Absence == workTypeClass
					|| WorkTypeClassification.Pause == workTypeClass) {
				return SetupType.NOT_REQUIRED;
			}
		}

		// Half day
		if (WorkTypeUnit.MonringAndAfternoon == workTypeUnit) {
			WorkStyle workStyle = this.checkWorkDay(workTypeCode);
			if (WorkStyle.ONE_DAY_REST == workStyle) {

				SetupType morningWorkStyle = this.checkRequiredOfInputType(dailyWork.getMorning());
				SetupType afternoonWorkStyle = this.checkRequiredOfInputType(dailyWork.getAfternoon());

				return this.checkRequired(morningWorkStyle, afternoonWorkStyle);
			} else {
				return SetupType.REQUIRED;
			}
		}
		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}

	/**
	 * 入力必須区分チェック
	 */
	@Override
	public SetupType checkRequiredOfInputType(WorkTypeClassification workTypeClass) {
		// TO-DO
		// Because of this stage WORK_SETTING_FOR_VACATION is not made
		// holidayWorkSetting - 休暇時の勤務設定 is not made
		// so return SetupType.OPTIONAL
		return SetupType.OPTIONAL;
	}

	/**
	 * 1日半日出勤・1日休日系の判定
	 */
	@Override
	public WorkStyle checkWorkDay(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> workTypeOpt = workTypeRepo.findByPK(companyId, workTypeCode);

		if (!workTypeOpt.isPresent()) {
			return null;
		}

		WorkType workType = workTypeOpt.get();
		DailyWork dailyWork = workTypeOpt.get().getDailyWork();

		// All day
		if (workType.isOneDay()) {
			if (dailyWork.IsLeaveForADay()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.ONE_DAY_WORK;
		}

		// Half day
		if (dailyWork.IsLeaveForMorning()) {
			if (dailyWork.IsLeaveForAfternoon()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.AFTERNOON_WORK;
		}

		if (dailyWork.IsLeaveForAfternoon()) {
			return WorkStyle.MORNING_WORK;
		}

		return WorkStyle.ONE_DAY_WORK;
	}
	
	@Override
	public WorkStyle checkWorkDay(Optional<WorkType> workTypeOpt) {

		if (!workTypeOpt.isPresent()) {
			return null;
		}

		WorkType workType = workTypeOpt.get();
		DailyWork dailyWork = workTypeOpt.get().getDailyWork();

		// All day
		if (workType.isOneDay()) {
			if (dailyWork.IsLeaveForADay()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.ONE_DAY_WORK;
		}

		// Half day
		if (dailyWork.IsLeaveForMorning()) {
			if (dailyWork.IsLeaveForAfternoon()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.AFTERNOON_WORK;
		}

		if (dailyWork.IsLeaveForAfternoon()) {
			return WorkStyle.MORNING_WORK;
		}

		return WorkStyle.ONE_DAY_WORK;
	}
	
	@Override
	public WorkStyle checkWorkDayByList(String workTypeCode, List<WorkType> listWorkType) {
		Optional<WorkType> workTypeOpt = listWorkType.stream()
				.filter(workType -> workType.getWorkTypeCode().v().equals(workTypeCode)).findFirst();

		if (!workTypeOpt.isPresent()) {
			return null;
		}

		WorkType workType = workTypeOpt.get();
		DailyWork dailyWork = workTypeOpt.get().getDailyWork();

		// All day
		if (workType.isOneDay()) {
			if (dailyWork.IsLeaveForADay()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.ONE_DAY_WORK;
		}

		// Half day
		if (dailyWork.IsLeaveForMorning()) {
			if (dailyWork.IsLeaveForAfternoon()) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.AFTERNOON_WORK;
		}

		if (dailyWork.IsLeaveForAfternoon()) {
			return WorkStyle.MORNING_WORK;
		}

		return WorkStyle.ONE_DAY_WORK;
	}
	
	@Override
	public SetupType checkRequired(SetupType morningWorkStyle, SetupType afternoonWorkStyle) {

		if (morningWorkStyle == SetupType.REQUIRED) {
			return SetupType.REQUIRED;
		}

		if (morningWorkStyle == SetupType.NOT_REQUIRED) {
			return afternoonWorkStyle;
		}

		if (afternoonWorkStyle == SetupType.REQUIRED) {
			return SetupType.REQUIRED;
		}

		if (afternoonWorkStyle == SetupType.NOT_REQUIRED || afternoonWorkStyle == SetupType.OPTIONAL) {
			return SetupType.OPTIONAL;
		}

		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService#
	 * checkPairWorkTypeWorkTime(java.lang.String, java.lang.String)
	 */
	@Override
	public void checkPairWorkTypeWorkTime(String workTypeCode, String workTimeCode) {
		SetupType setupType = this.checkNeededOfWorkTimeSetting(workTypeCode);

		// In case of Required and work time is not set.
		if (setupType == SetupType.REQUIRED && !this.isWorkTimeValid(workTimeCode)) {
			throw new BusinessException("Msg_435");
		}

		// In case of Not Required and work time is set.
		if (setupType == SetupType.NOT_REQUIRED && this.isWorkTimeValid(workTimeCode)) {
			throw new BusinessException("Msg_434");
		}
	}
	
	/**
	 * check Pair WorkType and WorkTime With List WorkType
	 * @param workTypeCode
	 * @param workTimeCode
	 * @param listWorkType
	 */
	@Override
	public String checkPairWTypeTimeWithLstWType(String workTypeCode, String workTimeCode, List<WorkType> listWorkType) {
		SetupType setupType = this.checkNeedWorkTimeSetByList(workTypeCode, listWorkType);

		// In case of Required and work time is not set.
		if (setupType == SetupType.REQUIRED && !this.isWorkTimeValid(workTimeCode)) {
			return "Msg_435";
		}

		// In case of Not Required and work time is set.
		if (setupType == SetupType.NOT_REQUIRED && this.isWorkTimeValid(workTimeCode)) {
			return "Msg_434";
		}
		
		return null;
	}

	@Override
	public void checkWorkTypeMaster(String companyId, String worktypeCode) {
		// call repository find work type by code
		Optional<WorkType> optionalWorkType = this.workTypeRepo.findByPK(companyId, worktypeCode);

		// check work type not exist
		if (!optionalWorkType.isPresent()) {
			throw new BusinessException("Msg_436");
		}

		// work type exist => check deprecate of work type is 廃止する
		if (optionalWorkType.isPresent()
				&& optionalWorkType.get().getDeprecate() == DeprecateClassification.Deprecated) {
			throw new BusinessException("Msg_468");
		}
	}

	@Override
	public void checkWorkTimeMater(String companyId, String workTimeCode) {
		// check default work time code
		// 設定されていない（就業時間帯コード＝NULL || 就業時間帯コード＝000）
		if (StringUtil.isNullOrEmpty(workTimeCode, false)) {
			return;
		}

		// call repository find work time by code
		// ドメインモデル「就業時間帯の設定」に該当の就業時間帯コードが存在するかチェックする
		Optional<WorkTimeSetting> optionalWorkTime = this.workTimeRepository.findByCode(companyId, workTimeCode);

		// check work time not exits
		if (!optionalWorkTime.isPresent()) {
			throw new BusinessException("Msg_437");
		}

		// work time exits => check display attr not display
		if (optionalWorkTime.isPresent() && optionalWorkTime.get().getAbolishAtr().value == AbolishAtr.ABOLISH.value) {
			throw new BusinessException("Msg_469");
		}
	}

	/**
	 * Checks if is work time valid.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return true, if is work time valid
	 */
	private boolean isWorkTimeValid(String workTimeCode) {
		if (StringUtil.isNullOrEmpty(workTimeCode, true) || StringUtil.isNullOrEmpty(workTimeCode, true)) {
			return false;
		}
		return true;
	}

	/**
	 * Check Enum WorkTypeClassification.
	 * 
	 * @param morningType
	 * @return true/false
	 */
	public boolean checkType(WorkTypeClassification workTypeClassification) {
		if (WorkTypeClassification.Holiday == workTypeClassification
				|| WorkTypeClassification.Pause == workTypeClassification
				|| WorkTypeClassification.AnnualHoliday == workTypeClassification
				|| WorkTypeClassification.YearlyReserved == workTypeClassification
				|| WorkTypeClassification.SpecialHoliday == workTypeClassification
				|| WorkTypeClassification.TimeDigestVacation == workTypeClassification
				|| WorkTypeClassification.SubstituteHoliday == workTypeClassification
				|| WorkTypeClassification.Absence == workTypeClassification
				|| WorkTypeClassification.ContinuousWork == workTypeClassification
				|| WorkTypeClassification.Closure == workTypeClassification
				|| WorkTypeClassification.LeaveOfAbsence == workTypeClassification) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean isReverseStartAndEndTime(TimeWithDayAttr scheduleStartClock, TimeWithDayAttr scheduleEndClock) {
		return scheduleStartClock.greaterThanOrEqualTo(scheduleEndClock);
	}

}
