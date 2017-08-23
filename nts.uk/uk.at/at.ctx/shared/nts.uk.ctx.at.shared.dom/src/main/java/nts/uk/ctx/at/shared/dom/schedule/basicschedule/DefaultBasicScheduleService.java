package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
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
		// All day
		if (workType.isPresent() && WorkTypeUnit.OneDay == workType.get().getDailyWork().getWorkTypeUnit()) {

			WorkTypeClassification workTypeClass = workType.get().getDailyWork().getOneDay();

			if (WorkTypeClassification.AnnualHoliday == workTypeClass
					|| WorkTypeClassification.YearlyReserved == workTypeClass
					|| WorkTypeClassification.SubstituteHoliday == workTypeClass
					|| WorkTypeClassification.Absence == workTypeClass
					|| WorkTypeClassification.SpecialHoliday == workTypeClass
					|| WorkTypeClassification.TimeDigestVacation == workTypeClass) {
				
				this.checkRequiredOfInputType(workTypeClass);
			}

			// Check QA3
			if (WorkTypeClassification.Attendance == workTypeClass
					|| WorkTypeClassification.Shooting == workTypeClass) {
				return SetupType.REQUIRED;
			}

			// Check QA3
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
		DailyWork dailyWork = workType.get().getDailyWork();
		//ERROR
		if (workType.isPresent()
				&& WorkTypeUnit.MonringAndAfternoon == dailyWork.getWorkTypeUnit()) {
//			WorkTypeUnit workTypeUnit = this.checkWorkDay(workTypeCode);
//			if (WorkTypeUnit.OneDay == workTypeUnit) {
//
//				SetupType morningWorkStyle = this.checkRequiredOfInputType(dailyWork.getMorning());
//				SetupType afternoonWorkStyle = this.checkRequiredOfInputType(dailyWork.getAfternoon());
//
//				this.checkRequired(morningWorkStyle, afternoonWorkStyle);
//
//			} else {
//				return SetupType.REQUIRED;
//			}
		}

		throw new RuntimeException("ERROR");
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
		// TODO Auto-generated method stub
		return WorkStyle.ONE_DAY_REST;
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

		throw new RuntimeException("ERROR");
	}

	@Override
	public void checkPairWorkTypeWorkTime(String workTypeCode, String workTimeCode) {
		SetupType setupType = this.checkNeededOfWorkTimeSetting(workTypeCode);
		if (setupType == SetupType.REQUIRED && StringUtil.isNullOrEmpty(workTimeCode, true)) {
			throw new BusinessException("Msg_435");
		}

		if (setupType == SetupType.NOT_REQUIRED && !StringUtil.isNullOrEmpty(workTimeCode, true)) {
			throw new BusinessException("Msg_434");
		}
	}

}
