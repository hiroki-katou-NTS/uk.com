package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.DefoAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

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
	public void askTime(Require require, CacheCarrier cacheCarrier, String companyId, String employeeId, DatePeriod datePeriod,
			YearMonth targetYm, GeneralDate baseDate, String employmentCode, ClosureId closureId, 
			AttendanceTimeMonth weeklyTotalPremiumTime, AddSet addSet, AggregateTotalWorkingTime aggregateTotalWorkingTime,
			boolean isAddVacation, DefoAggregateMethodOfMonthly defoAggregateMethod,
			MonthlyCalculatingDailys monthlyCalcDailys, MonthlyAggregateAtr aggregateAtr, WorkingSystem workingSystem) {
		
		// 変形労働勤務の月割増時間の対象となる時間を求める
		val targetPremiumTimeMonthOfIrregular = TargetPremiumTimeGetter.askPremiumTime(require, companyId, employeeId, datePeriod, 
				addSet, aggregateTotalWorkingTime, isAddVacation, monthlyCalcDailys, aggregateAtr, workingSystem);
		
		this.addedVacationUseTime = targetPremiumTimeMonthOfIrregular.getAddedVacationUseTime();
		
		val targetPremiumTimeMonthSrc = targetPremiumTimeMonthOfIrregular.getTargetPremiumTime();

		/** 総枠時間を取得する */
		val totalStatutory = defoAggregateMethod.calc(require, cacheCarrier, employeeId, targetYm,
														baseDate, companyId, employmentCode, datePeriod, closureId);
		
//		// 月割増対象時間と法定労働時間を比較する
//		if (targetPremiumTimeMonthSrc.lessThanOrEqualTo(totalStatutory.v())) 
//			return;
		
		// 月割増対象時間（過不足分）を求める
		int premiumTime = targetPremiumTimeMonthSrc.v() - totalStatutory.v();
		val excessOrDificiency = new AttendanceTimeMonthWithMinus(premiumTime);
		
		// 月割増対象時間を求める
		this.time = excessOrDificiency.minusMinutes(weeklyTotalPremiumTime.v());
	}
	
	public static interface Require extends DefoAggregateMethodOfMonthly.Require, TargetPremiumTimeGetter.Require {}
}
