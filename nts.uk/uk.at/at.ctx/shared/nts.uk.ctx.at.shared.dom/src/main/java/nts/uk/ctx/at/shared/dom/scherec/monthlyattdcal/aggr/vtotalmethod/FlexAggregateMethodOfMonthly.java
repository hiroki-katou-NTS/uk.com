package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/** 月別実績集計のフレックス集計方法 */
@AllArgsConstructor
@Getter
public class FlexAggregateMethodOfMonthly extends DomainObject {

	/** 途中入社、途中退職時に按分するか */
	private boolean divisionOnEntryRetire;
	
	public FlexAggregateMethodOfMonthly() {
		this.divisionOnEntryRetire = false;
	}
	
	/** 在籍日数で按分した時間を取得する */
	public MonthlyEstimateTime getTimeDivisionByWorkingDays(Require require, YearMonth ym, 
			MonthlyEstimateTime targetTime, DatePeriod period, ClosureId closureId) {
	
		/** 途中入社、途中退職時に按分するか */
		if(!divisionOnEntryRetire) return targetTime;
		
		/** 指定した年月の期間を算出する */
		val closurePeriod = ClosureService.getClosurePeriod(require, closureId.value, ym);
		
		/** 按分すべきかを確認する */
		val closureDays = closurePeriod.datesBetween().size();
		val recordDays = period.datesBetween().size();
		if(recordDays == closureDays) return targetTime; 
		
		/** 補正した時間　＝　（パラメータ。対象時間　/　暦日日数）　*　在籍日数 */
		int correctTime = (targetTime.valueAsMinutes() / closureDays) * recordDays;
		
		/** 時間を補正して返す */
		return new MonthlyEstimateTime(correctTime);
	}
	
	public static interface Require extends ClosureService.RequireM1{
		
	}
}
