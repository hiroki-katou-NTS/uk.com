package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.GetPeriodExcluseEntryRetireTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/** 月別実績集計の変形労働集計方法 */
@AllArgsConstructor
@Getter
public class DefoAggregateMethodOfMonthly {
	
	public DefoAggregateMethodOfMonthly () {
		this.premiumTimeCalcMethod = MonPremiumTimeCalcMethodInEntryOfDefo.CALC_BY_STATUTORY_TIME;
	}
	
	public static DefoAggregateMethodOfMonthly of(int calcMethod) {
		
		return new DefoAggregateMethodOfMonthly(EnumAdaptor.valueOf(calcMethod, MonPremiumTimeCalcMethodInEntryOfDefo.class));
	}

	/** 途中入社、途中退職時の割増計算方法 */
	private MonPremiumTimeCalcMethodInEntryOfDefo premiumTimeCalcMethod;
	
	/** 総枠を計算する */
	public AttendanceTimeMonth calc(Require require, CacheCarrier cacheCarrier, String sid, YearMonth targetYm, 
			GeneralDate baseDate, String cid, String employmentCode, DatePeriod period, ClosureId closureId) {
		
		/** 週、月の法定労働時間を取得(通常、変形用) */
		val statutory = MonthlyStatutoryWorkingHours.monAndWeekStatutoryTime(require, cacheCarrier, cid, employmentCode, 
				sid, baseDate, targetYm, WorkingSystem.VARIABLE_WORKING_TIME_WORK);
		
		/** 月の時間を返す */
		val monthlyTime = statutory.map(c -> new AttendanceTimeMonth(c.getMonthlyEstimateTime().v())).orElseGet(() -> new AttendanceTimeMonth(0));
		
		/** 補正するかを確認する */
		if(this.premiumTimeCalcMethod == MonPremiumTimeCalcMethodInEntryOfDefo.CALC_BY_STATUTORY_TIME) return monthlyTime;
		
		/** 入社前、退職後を期間から除く */
		val exclusedPeriod = GetPeriodExcluseEntryRetireTime.getPeriodExcluseEntryRetireTime(require, cacheCarrier , sid, period);

		/** 在職日数を計算する */
		val workingDays = exclusedPeriod.datesBetween().size();
		
		/** 当月の期間を算出する */
		val closurePeriod = ClosureService.getClosurePeriod(require, closureId.value, targetYm);
		
		/** 在職しない日数を確認する */
		if(closurePeriod.datesBetween().size() == workingDays) return  monthlyTime;
		
		/** 総枠時間を計算して返す */
		val totalTime = (workingDays * statutory.map(c -> c.getWeeklyEstimateTime().v()).orElse(0)) / 7;
		return new AttendanceTimeMonth(totalTime);
	}
	
	/** 総枠を計算する */
	public AttendanceTimeMonth getStatutoryWorkingTime(Require require, CacheCarrier cacheCarrier, String sid, YearMonth targetYm, 
			AttendanceTimeMonth monthlyTime, AttendanceTimeMonth weeklyEstimateTime, DatePeriod period, ClosureId closureId) {
		
		/** 補正するかを確認する */
		if(this.premiumTimeCalcMethod == MonPremiumTimeCalcMethodInEntryOfDefo.CALC_BY_STATUTORY_TIME) return monthlyTime;
		
		/** 入社前、退職後を期間から除く */
		val exclusedPeriod = GetPeriodExcluseEntryRetireTime.getPeriodExcluseEntryRetireTime(require, cacheCarrier , sid, period);

		/** 在職日数を計算する */
		val workingDays = exclusedPeriod.datesBetween().size();
		
		/** 当月の期間を算出する */
		val closurePeriod = ClosureService.getClosurePeriod(require, closureId.value, targetYm);
		
		/** 在職しない日数を確認する */
		if(closurePeriod.datesBetween().size() == workingDays) return  monthlyTime;
		
		/** 総枠時間を計算して返す */
		val totalTime = (workingDays * weeklyEstimateTime.valueAsMinutes()) / 7;
		return new AttendanceTimeMonth(totalTime);
	}
	
	public static interface Require extends MonthlyStatutoryWorkingHours.RequireM4, GetPeriodExcluseEntryRetireTime.Require, ClosureService.RequireM1 {
		
	}
}
