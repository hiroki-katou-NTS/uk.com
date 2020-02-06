package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 月割増対象時間
 * @author shuichu_ishida
 */
@Getter
public class TargetPremiumTimeMonth {

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	/** 加算した休暇使用時間 */
	private AttendanceTimeMonth addedVacationUseTime;
	
	/**
	 * コンストラクタ
	 */
	public TargetPremiumTimeMonth(){
		
		this.time = new AttendanceTimeMonthWithMinus(0);
		this.addedVacationUseTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 月割増対象時間を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param statutoryWorkingTimeMonth 月間法定労働時間
	 * @param isAddVacation 休暇加算　（true=する）
	 */
	public void askTime(
			String companyId, String employeeId, DatePeriod datePeriod,
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeMonth,
			boolean isAddVacation){
		
		// 変形労働勤務の月割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfIrregular = new TargetPremiumTimeMonthOfIrregular();
		targetPremiumTimeMonthOfIrregular.askPremiumTimeMonth(
				companyId, employeeId, datePeriod, addSet, aggregateTotalWorkingTime, isAddVacation);
		this.addedVacationUseTime = targetPremiumTimeMonthOfIrregular.getAddedVacationUseTime();
		
		// （実績）所定労働時間を取得する
		val prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		prescribedWorkingTime.aggregate(datePeriod);
		val recordPresctibedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime();
		
		// 法定労働時間と所定労働時間を比較する
		val targetPremiumTimeMonthSrc = targetPremiumTimeMonthOfIrregular.getTargetPremiumTimeMonth();
		AttendanceTimeMonthWithMinus excessOrDificiency = new AttendanceTimeMonthWithMinus(0);
		if (statutoryWorkingTimeMonth.greaterThanOrEqualTo(recordPresctibedWorkingTime.v())){

			// 月割増対象時間と法定労働時間を比較する
			if (targetPremiumTimeMonthSrc.lessThanOrEqualTo(statutoryWorkingTimeMonth.v())) return;
			
			// 月割増対象時間（過不足分）を求める
			excessOrDificiency = new AttendanceTimeMonthWithMinus(targetPremiumTimeMonthSrc.v());
			excessOrDificiency = excessOrDificiency.minusMinutes(statutoryWorkingTimeMonth.v());
		}
		else {
			
			// 月割増対象時間と所定労働時間を比較する
			if (targetPremiumTimeMonthSrc.lessThanOrEqualTo(recordPresctibedWorkingTime.v())) return;
			
			// 月割増対象時間（過不足分）を求める
			excessOrDificiency = new AttendanceTimeMonthWithMinus(targetPremiumTimeMonthSrc.v());
			excessOrDificiency = excessOrDificiency.minusMinutes(recordPresctibedWorkingTime.v());
		}
		
		// 月割増対象時間を求める
		this.time = excessOrDificiency.minusMinutes(weeklyTotalPremiumTime.v());
	}
}
