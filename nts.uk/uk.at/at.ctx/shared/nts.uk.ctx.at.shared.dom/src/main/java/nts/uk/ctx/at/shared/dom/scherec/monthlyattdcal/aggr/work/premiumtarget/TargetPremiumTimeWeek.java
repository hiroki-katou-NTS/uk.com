package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget;

import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 通常勤務の週割増対象時間
 * @author shuichi_ishida
 */
@Getter
public class TargetPremiumTimeWeek {

	/** 週割増時間 */
	private AttendanceTimeMonth premiumTimeWeek;
	/** 当月の週割増対象時間 */
	private AttendanceTimeMonth premiumTimeOfCurrentMonth;
	/** 前月の最終週の週割増時間 */
	private AttendanceTimeMonth premiumTimeOfPrevMonth;
	
	/**
	 * 通常勤務の週割増時間の対象となる時間を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param weekPeriod 週割増処理期間
	 * @param addSet 加算設定
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param premiumTimeOfPrevMonLast 前月の最終週の週割増対象時間
	 * @param isAddVacationTime 休暇加算区分（boolean - nullの場合trueとする)
	 * @return 通常勤務の週割増対象時間
	 */
	public static TargetPremiumTimeWeek askPremiumTimeWeek(Require require, String companyId, String employeeId,
			DatePeriod weekPeriod, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			AttendanceTimeMonth premiumTimeOfPrevMonLast, boolean isAddVacationTime, WorkingSystem workingSystem,
			MonthlyCalculatingDailys monthlyCalcDailys, MonthlyAggregateAtr aggregateAtr) {

		TargetPremiumTimeWeek domain = new TargetPremiumTimeWeek();
		domain.premiumTimeWeek = new AttendanceTimeMonth(0);
		domain.premiumTimeOfCurrentMonth = new AttendanceTimeMonth(0);
		domain.premiumTimeOfPrevMonth = new AttendanceTimeMonth(premiumTimeOfPrevMonLast.v());
		

		/** ○変形労働勤務の週割増時間の対象となる時間を求める */
		domain.premiumTimeWeek = TargetPremiumTimeGetter.askPremiumTime(require, companyId, employeeId, weekPeriod, addSet, 
					aggregateTotalWorkingTime, isAddVacationTime, monthlyCalcDailys, aggregateAtr, workingSystem)
				.getTargetPremiumTime();

		// 「当月の週割増対象時間」を求める
		domain.premiumTimeOfCurrentMonth = new AttendanceTimeMonth(domain.premiumTimeWeek.v());

		// 「前月の最終週の週割増対象時間」を加算する
		domain.premiumTimeWeek = domain.premiumTimeWeek.addMinutes(domain.premiumTimeOfPrevMonth.v());
		
		return domain;
	}
	
	public static interface Require extends TargetPremiumTimeGetter.Require {
		
	}
}
