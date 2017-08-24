package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DefaultBasicScheduleService implements BasicScheduleService {

	@Inject
	public WorkTypeRepository workTypeRepo;

	@Override
	public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> workType = workTypeRepo.findByPK(companyId, workTypeCode);
		WorkTypeUnit workTypeUnit = workType.get().getDailyWork().getWorkTypeUnit();

		if (!workType.isPresent()) {
			throw new RuntimeException("NOT FOUND WORK TYPE");
		}

		// All day
		if (WorkTypeUnit.OneDay == workTypeUnit) {

			WorkTypeClassification workTypeClass = workType.get().getDailyWork().getOneDay();

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

				SetupType morningWorkStyle = this.checkRequiredOfInputType(workType.get().getDailyWork().getMorning());
				SetupType afternoonWorkStyle = this
						.checkRequiredOfInputType(workType.get().getDailyWork().getAfternoon());

				return this.checkRequired(morningWorkStyle, afternoonWorkStyle);
			} else {
				return SetupType.REQUIRED;
			}
		}
		throw new RuntimeException("NOT FOUND SETUP TYPE");
	}

	@Override
	public SetupType checkRequiredOfInputType(WorkTypeClassification workTypeClass) {
		// To-do
		// Because of this stage WORK_SETTING_FOR_VACATION is not made
		// so return SetupType.OPTIONAL
		return SetupType.OPTIONAL;
	}

	@Override
	public WorkStyle checkWorkDay(String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> workType = workTypeRepo.findByPK(companyId, workTypeCode);
		WorkTypeUnit workTypeUnit = workType.get().getDailyWork().getWorkTypeUnit();
		if (!workType.isPresent()) {
			throw new RuntimeException("NOT FOUND WORK TYPE");
		}
		// All day
		if (WorkTypeUnit.OneDay == workTypeUnit) {
			WorkTypeClassification workTypeClass = workType.get().getDailyWork().getOneDay();
			if (this.checkType(workTypeClass)) {
				return WorkStyle.ONE_DAY_REST;
			} else {
				return WorkStyle.ONE_DAY_WORK;
			}
		}

		// Half day
		if (WorkTypeUnit.MonringAndAfternoon == workTypeUnit) {

			WorkTypeClassification morningType = workType.get().getDailyWork().getMorning();
			WorkTypeClassification afternoonType = workType.get().getDailyWork().getAfternoon();

			if (this.checkType(morningType)) {
				if (this.checkType(afternoonType)) {
					return WorkStyle.ONE_DAY_REST;
				} else {
					return WorkStyle.AFTERNOON_WORK;
				}
			} else {
				if (this.checkType(afternoonType)) {
					return WorkStyle.MORNING_WORK;
				} else {
					return WorkStyle.ONE_DAY_WORK;
				}
			}
		}

		throw new RuntimeException("NOT FOUND WORK STYLE");
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

	@Override
	public void checkPairWorkTypeWorkTime(String workTypeCode, String workTimeCode) {
		SetupType setupType = this.checkNeededOfWorkTimeSetting(workTypeCode);
		if (setupType == SetupType.REQUIRED && StringUtil.isNullOrEmpty(workTimeCode, true) || workTimeCode == "000") {
			throw new BusinessException("Msg_435");
		}

		if (setupType == SetupType.NOT_REQUIRED && !StringUtil.isNullOrEmpty(workTimeCode, true)
				|| workTimeCode == "000") {
			throw new BusinessException("Msg_434");
		}
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

}
