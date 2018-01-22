package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetVacationAddTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 当月の変形期間繰越時間
 * @author shuichu_ishida
 */
@Getter
public class IrregularPeriodCarryforwardsTimeOfCurrent {

	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * 変形期間繰越時間を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 */
	public AddedVacationUseTime aggregate(String companyId, String employeeId, DatePeriod datePeriod,
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		AddedVacationUseTime addedVacationUseTime = new AddedVacationUseTime();
		
		// 月割増対象時間（休暇加算前）を求める
		val beforeAddVacation = TargetPremiumTimeMonth.askTime(
				companyId, employeeId, datePeriod, weeklyTotalPremiumTime, addSet, aggregateTotalWorkingTime);
		
		// 月割増対象時間（休暇加算前）を〃（休暇加算後）に入れる
		AttendanceTimeMonth afterAddVacation = new AttendanceTimeMonth(beforeAddVacation.v());
		
		// 月割増対象時間（休暇加算後）＜0 の時、不足分を加算する
		if (afterAddVacation.lessThan(0)) {
			
			// 加算する休暇時間を取得する
			val vacationAddTime = GetVacationAddTime.getTime(
					datePeriod, aggregateTotalWorkingTime.getVacationUseTime(), addSet);
			
			// 月割増対象時間（休暇加算後）に、休暇加算時間を加算する
			afterAddVacation = afterAddVacation.addMinutes(vacationAddTime.v());
			
			// 加算した時間を、加算した休暇使用時間に退避しておく
			addedVacationUseTime = AddedVacationUseTime.of(vacationAddTime);
		}

		// 月割増対象時間（休暇加算後）を当月の変形期間繰越時間に入れる
		this.time = new AttendanceTimeMonth(afterAddVacation.v());
		
		return addedVacationUseTime;
	}
}
