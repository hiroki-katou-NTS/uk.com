package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget;

import java.util.Optional;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeAddtionTimeGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/** 割増時間集計対象時間の取得 */
public class TargetPremiumTimeGetter {

	/** 割増時間集計対象時間の取得 */
	public static TargetPremiumTime askPremiumTime(Require require, String companyId, String employeeId,
			DatePeriod period, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime, boolean isAddVacation,
			MonthlyCalculatingDailys monthlyCalcDailys, MonthlyAggregateAtr aggregateAtr, WorkingSystem workingSystem) {

		// 集計対象時間を取得する
		AttendanceTimeMonth premiumTime = aggregateTotalWorkingTime.getWorkTime().getAggregateTargetTime(period, addSet);
		
		// 法定内残業時間を取得する
		val legalOverTime = aggregateTotalWorkingTime.getOverTime().calcOverTimeForPremium(
				period, aggregateTotalWorkingTime);
		
		// 週割増時間に残業時間を加算する
		premiumTime = premiumTime.addMinutes(legalOverTime.v());

		// 法定内休出時間を取得する
		val legalHolidayWorkTime = aggregateTotalWorkingTime.getHolidayWorkTime().getLegalHolidayWorkTime(period);
		
		// 週割増時間に休出時間を加算する
		premiumTime = premiumTime.addMinutes(legalHolidayWorkTime.v());
		
		/** 変形法定内残業時間を求める*/
		val irregularLegalOverTime = aggregateTotalWorkingTime.getOverTime().getIrregularLegalOverTime(period);
		/** 変形法定内残業時間を集計対象時間に加算する*/
		premiumTime = premiumTime.addMinutes(irregularLegalOverTime.valueAsMinutes());
		
		// 休暇加算を確認する
		if (isAddVacation){
			
			// 休暇加算時間を取得する
			val dailies = monthlyCalcDailys.getDailyWorks(employeeId, period);
			val addVacationTime = WorkTimeAddtionTimeGetter.getForPremiumTime(require, dailies, 
					workingSystem, companyId, aggregateAtr, Optional.of(addSet));
			
			// 週割増時間に休暇加算時間を加算する
			return new TargetPremiumTime(premiumTime.addMinutes(addVacationTime.v()), addVacationTime);
		}

		return new TargetPremiumTime(premiumTime, new AttendanceTimeMonth(0));
	}
	
	public static interface Require extends WorkTimeAddtionTimeGetter.Require {}
}
