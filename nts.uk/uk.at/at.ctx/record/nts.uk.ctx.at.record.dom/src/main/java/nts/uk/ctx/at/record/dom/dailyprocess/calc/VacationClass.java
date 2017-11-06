package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
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
	public static AttendanceTime vacationTimeOfcalcDaily(WorkType workType,VacationCategory vacationCategory) {
		BreakdownTimeDay breakDownTimeDay = getVacationAddSet(predetermineTimeSet, siftCode, personalCondition);
		switch(vacationCategory.) {
		case /*1日*/:
			return breakDownTimeDay.getOneDay();
		case /*午前*/
			return breakDownTimeDay.getMorning();
		case /*午後*/
			return breakDownTimeDay.getAfternoon();
		default:
			throw new RuntimeException("unknown WorkType");
		}
	}
	
	/**
	 * 休暇加算設定の取得
	 * @return
	 */
	public BreakdownTimeDay getVacationAddSet(PredetermineTimeSetForCalc predetermineTimeSet,Optional<SiftCode> siftCode
											 ,Optional<PersonalLaborCondition> personalCondition) {
		if(siftCode.isPresent()) {
			return predetermineTimeSet.getAdditionSet();
		}
		else {
			if(personalCondition.isPresent()) {
				return personalCondition.get().getHolidayAddTimeSet();
			}
			else {
				return 
			}
		}
		return /*1日の時間内訳*/;
	}
}
