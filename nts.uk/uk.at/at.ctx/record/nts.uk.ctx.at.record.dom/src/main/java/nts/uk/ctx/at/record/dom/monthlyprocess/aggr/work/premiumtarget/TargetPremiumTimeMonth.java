package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月割増対象時間
 * @author shuichu_ishida
 */
public class TargetPremiumTimeMonth {

	/**
	 * 月割増対象時間を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param statutoryWorkingTimeMonth 月間法定労働時間
	 * @return 月割増対象時間
	 */
	public static AttendanceTimeMonth askTime(
			String companyId, String employeeId, DatePeriod datePeriod,
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeMonth){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		
		// 変形労働勤務の月割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfIrregular = new TargetPremiumTimeMonthOfIrregular();
		targetPremiumTimeMonthOfIrregular.askPremiumTimeMonth(
				companyId, employeeId, datePeriod, addSet, aggregateTotalWorkingTime);
		
		// （実績）所定労働時間を取得する
		val prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		prescribedWorkingTime.aggregate();
		val recordPresctibedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime();
		
		// 法定労働時間と所定労働時間を比較する
		val targetPremiumTimeMonthSrc = targetPremiumTimeMonthOfIrregular.getTargetPremiumTimeMonth();
		AttendanceTimeMonth excessOrDificiency = new AttendanceTimeMonth(0);
		if (statutoryWorkingTimeMonth.greaterThanOrEqualTo(recordPresctibedWorkingTime.v())){

			// 月割増対象時間と法定労働時間を比較する
			if (targetPremiumTimeMonthSrc.lessThanOrEqualTo(statutoryWorkingTimeMonth.v())) return returnTime;
			
			// 月割増対象時間（過不足分）を求める
			excessOrDificiency = targetPremiumTimeMonthSrc.minusMinutes(statutoryWorkingTimeMonth.v());
		}
		else {
			
			// 月割増対象時間と所定労働時間を比較する
			if (targetPremiumTimeMonthSrc.lessThanOrEqualTo(recordPresctibedWorkingTime.v())) return returnTime;
			
			// 月割増対象時間（過不足分）を求める
			excessOrDificiency = targetPremiumTimeMonthSrc.minusMinutes(recordPresctibedWorkingTime.v());
		}
		
		// 月割増対象時間を求める
		returnTime = excessOrDificiency.minusMinutes(weeklyTotalPremiumTime.v());
		
		return returnTime;
	}
}
