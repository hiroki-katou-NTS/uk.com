package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;

/**
 * 時系列の就業時間
 * @author shuichi_ishida
 */
@Getter
public class WorkTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	/** 法定内時間 */
	private WithinStatutoryTimeOfDaily legalTime;
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param legalTime 法定内時間
	 * @return 時系列の就業時間
	 */
	public static WorkTimeOfTimeSeries of(
			GeneralDate ymd,
			WithinStatutoryTimeOfDaily legalTime){
		
		val domain = new WorkTimeOfTimeSeries();
		domain.ymd = ymd;
		domain.legalTime = legalTime;
		return domain;
	}
}
