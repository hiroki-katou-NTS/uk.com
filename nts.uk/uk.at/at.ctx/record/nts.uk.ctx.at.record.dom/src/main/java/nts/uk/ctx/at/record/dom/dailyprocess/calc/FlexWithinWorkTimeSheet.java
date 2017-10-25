package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * フレックス就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class FlexWithinWorkTimeSheet {
	private TimeSpanForCalc coreTimeSheet;
	
	/**
	 * 控除する時間の計算
	 */
	public void calcdeductTime(){
		AttendanceTime forHolidayTime = calcHolidayDeductionTime(workType);
		AttendanceTime forCompensatoryLeaveTime = calcSubstituteHoliday(workType);
	}
	
	
	
	/**
	 * 休日控除時間の計算
	 */
	public AttendanceTime calcHolidayDeductionTime(WorkType workType) {
		int useTime = VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.Holiday).valueAsMinutes();
		useTime += VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.SubstituteHoliday).valueAsMinutes();
		return new AttendanceTime(useTime);
	}
	
	/**
	 * 代休使用時間の計算
	 * @return
	 */
	public AttendanceTime calcSubstituteHoliday(WorkType workType) {
		return VacationClass.vacationTimeOfcalcDaily(workType, VacationCategory.SubstituteHoliday);
	}
	
	/**
	 * フレックス時間を計算するか判定し就業時間内時間帯を作成する
	 * @return
	 */
	public FlexWithinWorkTimeSheet createWithinWorkTimeSheetAsFlex(CalcMethodOfNoWorkingDay calcMethod) {
		if(calcMethod.isCalclateFlexTime()) {
			if(/*勤務種類を基に非勤務日であるか判定　非勤務日である*/) {
				return new FlexWithinWorkTimeSheet(new TimeSpanForCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)));
			}
		}
		/*フレックス時間の計算*/
		int flexTime = calcFlexTime();
		/*事前申請を上限とする制御*/
	}
	
	/**
	 * フレックス時間の計算
	 */
	public int calcFlexTime() {
		/*法定労働時間の算出*/
		int houtei = calcStatutoryTime();
		/*実働時間の算出*/
		int zitudou = calcWorkTime(PremiumAtr);
		/*実働時間の算出(割増時間含む)*/
		int zitudouIncludePremium = calcWorkTime(PremiumAtr);
		
		int flexTime = 0;
		if(/*不足時に加算する*/) {
			/*フレックス時間算出*/
			flexTime = houtei - zitudou;
			if(flexTime < 0) {
				flexTime = zitudouIncludePremium - houtei;
				/*不足しているフレックス時間*/
				int husokuZiKasanZikan = zitudouIncludePremium - zitudou;
			}
		}
		else if(加算しない) {
			flexTime = houtei - zitudou;
		}
		else if(加算する) {
			/*不足しているフレックス時間*/
			int husokuZiKasanZikan = zitudouIncludePremium - zitudou;
		}
		else {
			throw new RuntimeException("unknown calcAtr" + ??);
		}
		
		if(/*計算区分が計算しない*/ && flexTime > 0) {
			flexTime = 0;
		}
		return flexTime = 0;
	}
	
	/**
	 * 法定労働時間から控除
	 * @return
	 */
	public int calcStatutoryTime() {
		
	}
}
