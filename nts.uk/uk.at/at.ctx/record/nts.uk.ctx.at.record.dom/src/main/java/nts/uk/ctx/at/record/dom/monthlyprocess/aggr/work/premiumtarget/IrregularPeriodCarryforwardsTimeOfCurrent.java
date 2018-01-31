package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetVacationAddTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 当月の変形期間繰越時間
 * @author shuichu_ishida
 */
@Getter
public class IrregularPeriodCarryforwardsTimeOfCurrent {

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	/** 加算した休暇使用時間 */
	private AttendanceTimeMonth addedVacationUseTime;
	
	/**
	 * コンストラクタ
	 */
	public IrregularPeriodCarryforwardsTimeOfCurrent(){
		
		this.time = new AttendanceTimeMonthWithMinus(0);
		this.addedVacationUseTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 変形期間繰越時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param statutoryWorkingTimeMonth 月間法定労働時間
	 */
	public void aggregate(String companyId, String employeeId, DatePeriod datePeriod,
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth statutoryWorkingTimeMonth){
		
		// 「変形労働勤務の加算設定」を取得する
		//*****（未）　日別側の休暇系処理の実装待ち。仮に、分岐ケースを例示。
		boolean isDummy = true;
		if (isDummy){
		
			// 月割増対象時間（休暇加算前）を求める
			val targetPremiumTimeMonth = new TargetPremiumTimeMonth();
			targetPremiumTimeMonth.askTime(companyId, employeeId, datePeriod, weeklyTotalPremiumTime,
					addSet, aggregateTotalWorkingTime, statutoryWorkingTimeMonth, false);
			val beforeAddVacation = targetPremiumTimeMonth.getTime();
			
			// 月割増対象時間（休暇加算前）を〃（休暇加算後）に入れる
			AttendanceTimeMonthWithMinus afterAddVacation = new AttendanceTimeMonthWithMinus(
					beforeAddVacation.v());
			
			// 月割増対象時間（休暇加算後）＜0 の時、不足分を加算する
			if (afterAddVacation.lessThan(0)) {
				
				// 加算する休暇時間を取得する
				val vacationAddTime = GetVacationAddTime.getTime(
						datePeriod, aggregateTotalWorkingTime.getVacationUseTime(), addSet);
				
				// 月割増対象時間（休暇加算後）に、休暇加算時間を加算する
				afterAddVacation = afterAddVacation.addMinutes(vacationAddTime.v());
				
				// 加算した時間を、加算した休暇使用時間に退避しておく
				this.addedVacationUseTime = new AttendanceTimeMonth(vacationAddTime.v());
			}
	
			// 月割増対象時間（休暇加算後）を当月の変形期間繰越時間に入れる
			this.time = new AttendanceTimeMonthWithMinus(afterAddVacation.v());
		}
		if (!isDummy){
			
			// 月割増対象時間（休暇加算後）を求める
			val targetPremiumTimeMonth = new TargetPremiumTimeMonth();
			targetPremiumTimeMonth.askTime(companyId, employeeId, datePeriod, weeklyTotalPremiumTime,
					addSet, aggregateTotalWorkingTime, statutoryWorkingTimeMonth, true);
			this.time = new AttendanceTimeMonthWithMinus(
					targetPremiumTimeMonth.getTime().v());
			this.addedVacationUseTime = new AttendanceTimeMonth(
					targetPremiumTimeMonth.getAddedVacationUseTime().v());
		}
	}
}
