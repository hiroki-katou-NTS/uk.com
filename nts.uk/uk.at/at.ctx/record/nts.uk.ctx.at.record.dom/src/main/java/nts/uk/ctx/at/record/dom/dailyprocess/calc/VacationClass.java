package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休暇クラス
 * @author keisuke_hoshina
 *
 */
@Value
public class VacationClass {
	private HolidayOfDaily holidayOfDaily;
	
	/**
	 * 日数単位の休暇時間計算
	 * @return　1日の時間内訳時間
	 */
	public static AttendanceTime vacationTimeOfcalcDaily(WorkType workType,
														 VacationCategory vacationCategory,
														 PredetermineTimeSetForCalc predetermineTimeSet,
														 Optional<WorkTimeCode> siftCode,
			 											 Optional<PersonalLaborCondition> personalCondition,
			 											 VacationAddTimeSet vacationAddTimeSet) {
		BreakDownTimeDay breakDownTimeDay = getVacationAddSet(predetermineTimeSet, siftCode, personalCondition, vacationAddTimeSet);
		switch(workType.getDailyWork().decisionMatchWorkType(vacationCategory.convertWorkTypeClassification())) {
			case FULL_TIME:
				return breakDownTimeDay.getOneDay();
			case MORNING:
				return breakDownTimeDay.getMorning();
			case AFTERNOON:
				return breakDownTimeDay.getAfternoon();
			default:
				throw new RuntimeException("unknown WorkType");
		}
	}
	
	/**
	 * 休暇加算設定の取得
	 * @return
	 */
	private static BreakDownTimeDay getVacationAddSet(PredetermineTimeSetForCalc predetermineTimeSet,Optional<WorkTimeCode> siftCode
											 ,Optional<PersonalLaborCondition> personalCondition
											 ,VacationAddTimeSet vacationAddTimeSet) {
		if(siftCode.isPresent()) {
			return predetermineTimeSet.getAdditionSet().getAddTime();
		}
		else {
			if(personalCondition.isPresent()) {
				return personalCondition.get().getHolidayAddTimeSet().getAddTime();
			}
			else {
				return vacationAddTimeSet.getAdditionTime();
			}
		}
	}
}
