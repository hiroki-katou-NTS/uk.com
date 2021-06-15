package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/** 週、月の法定労働時間を按分を取得する */
public class MonthlyStatutoryLaborDivisionService {

	/** 按分した週、月の法定労働時間を取得(フレックス用) */
	public static MonthlyFlexStatutoryLaborTime getDivisiedStatutoryLabor(Require require, CacheCarrier cacheCarrier, String cid,
			String employmentCd, String sid, GeneralDate baseDate, Optional<DatePeriod> period, YearMonth ym, ClosureId closureId,
			FlexAggregateMethodOfMonthly flexAggregateMethod) {

		/** 週、月の法定労働時間を取得(フレックス用) */
		val monStatTime = MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(require, cacheCarrier,
				cid, employmentCd, sid, baseDate, ym);
		
		return period.map(p -> {
			
			/** 所定労働時間を按分する */
			val newSpecifiedSetting = flexAggregateMethod.getTimeDivisionByWorkingDays(require, ym, monStatTime.getSpecifiedSetting(), p, closureId);
			
			/** 法定労働時間を按分する */
			val newStatutorySetting = flexAggregateMethod.getTimeDivisionByWorkingDays(require, ym, monStatTime.getStatutorySetting(), p, closureId);
			
			/** 週平均時間を按分する */
			val newWeekAveSetting = flexAggregateMethod.getTimeDivisionByWorkingDays(require, ym, monStatTime.getWeekAveSetting(), p, closureId);
			
			return new MonthlyFlexStatutoryLaborTime(newStatutorySetting, newSpecifiedSetting, newWeekAveSetting);
		}).orElse(monStatTime);
	}
	
	public static interface Require extends MonthlyStatutoryWorkingHours.RequireM1, FlexAggregateMethodOfMonthly.Require {
		
		
	}
}
