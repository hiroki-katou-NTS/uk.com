package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime.GetVacationAddTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 変形労働勤務の月割増対象時間
 * @author shuichu_ishida
 */
@Getter
public class TargetPremiumTimeMonthOfIrregular {

	/** 月割増対象時間 */
	private AttendanceTimeMonth targetPremiumTimeMonth;
	
	/**
	 * コンストラクタ
	 */
	public TargetPremiumTimeMonthOfIrregular(){
		
		this.targetPremiumTimeMonth = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 変形労働勤務の月割増時間の対象となる時間を求める　（休暇加算前）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @return 加算した休暇使用時間　（休暇加算前）
	 */
	public void askPremiumTimeMonth(String companyId, String employeeId, DatePeriod datePeriod,
			AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime){

		// 法定内時間を取得する
		val legalTime = aggregateTotalWorkingTime.getWorkTime().getTimeSeriesTotalLegalTime();

		// 変形労働勤務の月割増対象時間に法定内時間（就業時間）を加算する
		this.targetPremiumTimeMonth = this.targetPremiumTimeMonth.addMinutes(legalTime.v());
		
		// 法定内残業時間を取得する
		val legalOverTime = aggregateTotalWorkingTime.getOverTime().getLegalOverTime();
		
		// 変形労働勤務の月割増対象時間に残業時間を加算する
		this.targetPremiumTimeMonth = this.targetPremiumTimeMonth.addMinutes(legalOverTime.v());

		// 法定内休出時間を取得する
		val legalHolidayWorkTime = aggregateTotalWorkingTime.getHolidayWorkTime().getLegalHolidayWorkTime();
		
		// 変形労働勤務の月割増対象時間に休出時間を加算する
		this.targetPremiumTimeMonth = this.targetPremiumTimeMonth.addMinutes(legalHolidayWorkTime.v());
	}
}
