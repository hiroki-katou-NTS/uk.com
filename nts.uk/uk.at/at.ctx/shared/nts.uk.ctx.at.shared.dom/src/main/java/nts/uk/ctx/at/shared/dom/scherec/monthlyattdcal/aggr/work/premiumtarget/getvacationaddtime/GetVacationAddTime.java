package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;

/**
 * 休暇加算時間を取得する
 * @author shuichi_ishida
 */
public class GetVacationAddTime {

	/**
	 * 休暇加算時間を取得する
	 * @param datePeriod 期間
	 * @param vacationUseTime 休暇使用時間
	 * @param addSet 加算設定
	 * @return 休暇加算時間
	 */
	public static AttendanceTimeMonth getTime(DatePeriod datePeriod,
			VacationUseTimeOfMonthly vacationUseTime, AddSet addSet){
		
		AttendanceTimeMonth vacationAddTime = new AttendanceTimeMonth(0);
		
		if (addSet.isAnnualLeave()){
			
			// 休暇加算時間に年休使用時間を加算する
			vacationAddTime = vacationAddTime.addMinutes(
					vacationUseTime.getAnnualLeave().getTotalUseTime(datePeriod).v());
		}
		
		if (addSet.isRetentionYearly()){
			
			// 休暇加算時間に積立年休使用時間を加算する
			vacationAddTime = vacationAddTime.addMinutes(
					vacationUseTime.getRetentionYearly().getTotalUseTime(datePeriod).v());
		}
		
		// 大塚モードの確認
		if (false) {
			
			if (addSet.isSpecialHoliday()){
				
				// 休暇加算時間に特別休暇使用時間を加算する
				vacationAddTime = vacationAddTime.addMinutes(
						vacationUseTime.getSpecialHoliday().getTotalUseTime(datePeriod).v());
			}
		}

		// 休暇加算時間を返す
		return vacationAddTime;
	}

	/**
	 * 休暇加算時間を取得する（過去分）
	 * @param vacationUseTime 休暇使用時間
	 * @param addSet 加算設定
	 * @return 休暇加算時間
	 */
	public static AttendanceTimeMonth getTimeForPast(VacationUseTimeOfMonthly vacationUseTime, AddSet addSet){
		
		AttendanceTimeMonth vacationAddTime = new AttendanceTimeMonth(0);
		
		if (addSet.isAnnualLeave()){
			
			// 休暇加算時間に年休使用時間を加算する
			vacationAddTime = vacationAddTime.addMinutes(vacationUseTime.getAnnualLeave().getUseTime().v());
		}
		
		if (addSet.isRetentionYearly()){
			
			// 休暇加算時間に積立年休使用時間を加算する
			vacationAddTime = vacationAddTime.addMinutes(vacationUseTime.getRetentionYearly().getUseTime().v());
		}
		
		// 大塚モードの確認
		if (false) {
			
			if (addSet.isSpecialHoliday()){
				
				// 休暇加算時間に特別休暇使用時間を加算する
				vacationAddTime = vacationAddTime.addMinutes(vacationUseTime.getSpecialHoliday().getUseTime().v());
			}
		}

		// 休暇加算時間を返す
		return vacationAddTime;
	}
}
