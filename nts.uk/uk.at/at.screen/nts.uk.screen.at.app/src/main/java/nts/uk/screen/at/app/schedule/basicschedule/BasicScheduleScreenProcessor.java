package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.shift.workpairpattern.ComPatternScreenDto;
import nts.uk.screen.at.app.shift.workpairpattern.WkpPatternScreenDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * Get data DB BASIC_SCHEDULE, WORKTIME, WORKTYPE, CLOSURE not through dom layer
 * 
 * @author sonnh1
 *
 */

@Stateless
public class BasicScheduleScreenProcessor {

	@Inject
	private BasicScheduleScreenRepository bScheduleScreenRepo;

	@Inject
	private ShClosurePub shClosurePub;

	/**
	 * @param params
	 * @return
	 */
	public List<BasicScheduleScreenDto> getByListSidAndDate(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getByListSidAndDate(params.employeeId, params.startDate, params.endDate);
	}

	/**
	 * get list workTime with abolishAtr = NOT_ABOLISH (in contrast to DISPLAY)
	 * 
	 * @return
	 */
	public List<WorkTimeScreenDto> getListWorkTime() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getListWorkTime(companyId, AbolishAtr.NOT_ABOLISH.value);
	}

	/**
	 * getPresentClosingPeriodExport to get startDate and endDate for screen
	 * KSU001.A
	 * 
	 * @return
	 */
	public PresentClosingPeriodExport getPresentClosingPeriodExport() {
		String companyId = AppContexts.user().companyId();
		int closureId = 1;
		return shClosurePub.find(companyId, closureId).get();
	}

	/**
	 * find by companyId and DeprecateClassification = Deprecated
	 * 
	 * @return List WorkTypeDto
	 */

	public List<WorkTypeScreenDto> findByCIdAndDeprecateCls1() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.findByCIdAndDeprecateCls1(companyId,
				DeprecateClassification.NotDeprecated.value);
	}

	/**
	 * Check state of list WorkTypeCode
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */

	public List<StateWorkTypeCodeDto> checkStateWorkTypeCode1(List<String> listWorkTypeCode,
			List<WorkTypeScreenDto> workTypeList) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = new ArrayList<StateWorkTypeCodeDto>();
		listWorkTypeCode.forEach(workTypeCode -> {
			WorkStyle workStyle = this.convertToWorkStyle(workTypeCode, workTypeList);
			if (workStyle != null) {
				lstStateWorkTypeCode.add(new StateWorkTypeCodeDto(workTypeCode, workStyle.value));
			}
		});
		return lstStateWorkTypeCode;
	}

	/**
	 * check Needed Of WorkTimeSetting
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */

	public List<StateWorkTypeCodeDto> checkNeededOfWorkTimeSetting1(List<String> lstWorkTypeCode, List<WorkTypeScreenDto> workTypeList) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = new ArrayList<StateWorkTypeCodeDto>();
		lstWorkTypeCode.forEach(workTypeCode -> {
			SetupType setupType = this.convertToSetupType(workTypeCode, workTypeList);
			lstStateWorkTypeCode.add(new StateWorkTypeCodeDto(workTypeCode, setupType.value));
		});
		return lstStateWorkTypeCode;
	}

	/**
	 * 
	 * @param params
	 * @return WorkEmpCombineDto
	 */
	public List<WorkEmpCombineScreenDto> getListWorkEmpCombine(ScheduleScreenSymbolParams params) {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getListWorkEmpCobine(companyId, params.getLstWorkTypeCode(),
				params.getLstWorkTimeCode());
	}

	/**
	 * 
	 * @return list ScheduleDisplayControlDto
	 */
	public ScheduleDisplayControlScreenDto getScheduleDisplayControl() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getScheduleDisControl(companyId).orElse(null);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<BasicScheduleScreenDto> getDataWorkScheTimezone(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getDataWorkScheTimezone(params.employeeId, params.startDate, params.endDate);
	}

	/**
	 * Get data ComPattern, ComPatternItem, ComWorkPairSet
	 * 
	 * @return
	 */
	public List<ComPatternScreenDto> getDataComPattern() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getDataComPattern(companyId);
	}

	/**
	 * Get data WkpPattern, WkpPatternItem, WkpWorkPairSet
	 * 
	 * @return
	 */
	public List<WkpPatternScreenDto> getDataWkpPattern(String workplaceId) {
		if (StringUtil.isNullOrEmpty(workplaceId, true)) {
			return Collections.emptyList();
		}
		return this.bScheduleScreenRepo.getDataWkpPattern(workplaceId);
	}

	/**
	 * 
	 * @param workTypeCode
	 * @param listWorkType
	 * @return
	 */
	private WorkStyle convertToWorkStyle(String workTypeCode, List<WorkTypeScreenDto> listWorkType) {
		Optional<WorkTypeScreenDto> workTypeOpt = listWorkType.stream()
				.filter(x -> x.getWorkTypeCode().equals(workTypeCode)).findFirst();

		if (!workTypeOpt.isPresent()) {
			return null;
		}

		WorkTypeScreenDto workTypeScreenDto = workTypeOpt.get();

		// All day
		if (workTypeScreenDto.getWorkTypeUnit() == WorkTypeUnit.OneDay.value) {
			if (this.isLeaveForADay(workTypeScreenDto.getOneDay())) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.ONE_DAY_WORK;
		}

		// Half day
		if (this.isLeaveForMorning(workTypeScreenDto.getMorning())) {
			if (this.isLeaveForAfternoon(workTypeScreenDto.getAfternoon())) {
				return WorkStyle.ONE_DAY_REST;
			}

			return WorkStyle.AFTERNOON_WORK;
		}

		if (this.isLeaveForAfternoon(workTypeScreenDto.getAfternoon())) {
			return WorkStyle.MORNING_WORK;
		}

		return WorkStyle.ONE_DAY_WORK;
	}

	/**
	 * 
	 * @param workTypeCode
	 * @param listWorkType
	 * @return
	 */
	private SetupType convertToSetupType(String workTypeCode, List<WorkTypeScreenDto> listWorkType) {
		Optional<WorkTypeScreenDto> workTypeOpt = listWorkType.stream()
				.filter(x -> x.getWorkTypeCode().equals(workTypeCode)).findFirst();

		if (!workTypeOpt.isPresent()) {
			throw new RuntimeException("NOT FOUND WORK TYPE");
		}
		WorkTypeScreenDto workTypeScreenDto = workTypeOpt.get();
		int workTypeUnit = workTypeScreenDto.getWorkTypeUnit();
		
		// All day
		if (WorkTypeUnit.OneDay.value == workTypeUnit) {

			int workTypeClass = workTypeScreenDto.getOneDay();

			if (WorkTypeClassification.AnnualHoliday.value == workTypeClass
					|| WorkTypeClassification.YearlyReserved.value == workTypeClass
					|| WorkTypeClassification.SubstituteHoliday.value == workTypeClass
					|| WorkTypeClassification.Absence.value == workTypeClass
					|| WorkTypeClassification.SpecialHoliday.value == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation.value == workTypeClass) {

				return this.checkRequiredOfInputType(workTypeClass);
			}

			if (WorkTypeClassification.Attendance.value == workTypeClass
					|| WorkTypeClassification.HolidayWork.value == workTypeClass
					|| WorkTypeClassification.Shooting.value == workTypeClass) {
				return SetupType.REQUIRED;
			}

			if (WorkTypeClassification.AnnualHoliday.value == workTypeClass
					|| WorkTypeClassification.YearlyReserved.value == workTypeClass
					|| WorkTypeClassification.SpecialHoliday.value == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation.value == workTypeClass
					|| WorkTypeClassification.ContinuousWork.value == workTypeClass
					|| WorkTypeClassification.Closure.value == workTypeClass
					|| WorkTypeClassification.LeaveOfAbsence.value == workTypeClass) {
				return SetupType.OPTIONAL;
			}

			if (WorkTypeClassification.Holiday.value == workTypeClass || WorkTypeClassification.Absence.value == workTypeClass
					|| WorkTypeClassification.Pause.value == workTypeClass) {
				return SetupType.NOT_REQUIRED;
			}
		}

		// Half day
		if (WorkTypeUnit.MonringAndAfternoon.value == workTypeUnit) {
			WorkStyle workStyle = this.convertToWorkStyle(workTypeCode, listWorkType);
			if (WorkStyle.ONE_DAY_REST == workStyle) {

				SetupType morningWorkStyle = this.checkRequiredOfInputType(workTypeScreenDto.getMorning());
				SetupType afternoonWorkStyle = this.checkRequiredOfInputType(workTypeScreenDto.getAfternoon());

				return this.checkRequired(morningWorkStyle, afternoonWorkStyle);
			} else {
				return SetupType.REQUIRED;
			}
		}
		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}
	
	private boolean checkLeave(int attribute) {
		return WorkTypeClassification.Holiday.value == attribute || WorkTypeClassification.Pause.value == attribute
				|| WorkTypeClassification.AnnualHoliday.value == attribute
				|| WorkTypeClassification.YearlyReserved.value == attribute
				|| WorkTypeClassification.SpecialHoliday.value == attribute
				|| WorkTypeClassification.TimeDigestVacation.value == attribute
				|| WorkTypeClassification.SubstituteHoliday.value == attribute
				|| WorkTypeClassification.Absence.value == attribute
				|| WorkTypeClassification.ContinuousWork.value == attribute
				|| WorkTypeClassification.Closure.value == attribute
				|| WorkTypeClassification.LeaveOfAbsence.value == attribute;
	}

	private boolean isLeaveForMorning(int valueMorning) {
		return this.checkLeave(valueMorning);
	}

	private boolean isLeaveForAfternoon(int valueAfternoon) {
		return this.checkLeave(valueAfternoon);
	}

	private boolean isLeaveForADay(int valueOneDay) {
		return this.checkLeave(valueOneDay);
	}
	
	private SetupType checkRequiredOfInputType(int workTypeClass) {
		// TO-DO
		// Because of this stage WORK_SETTING_FOR_VACATION is not made
		// holidayWorkSetting - 休暇時の勤務設定 is not made
		// so return SetupType.OPTIONAL
		return SetupType.OPTIONAL;
	}
	
	private SetupType checkRequired(SetupType morningWorkStyle, SetupType afternoonWorkStyle) {

		if (SetupType.REQUIRED == morningWorkStyle) {
			return SetupType.REQUIRED;
		}

		if (SetupType.NOT_REQUIRED == morningWorkStyle) {
			return afternoonWorkStyle;
		}

		if (SetupType.REQUIRED == afternoonWorkStyle) {
			return SetupType.REQUIRED;
		}

		if (SetupType.NOT_REQUIRED == afternoonWorkStyle || SetupType.OPTIONAL == afternoonWorkStyle) {
			return SetupType.OPTIONAL;
		}

		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}

}
