package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
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
	/** 日別実績の法定内時間 */
	private WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily;
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param withinStatutoryTimeOfDaily 日別実績の法定内時間
	 * @return 時系列の就業時間
	 */
	public static WorkTimeOfTimeSeries of(
			GeneralDate ymd,
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily){
		
		WorkTimeOfTimeSeries domain = new WorkTimeOfTimeSeries();
		domain.ymd = ymd;
		domain.withinStatutoryTimeOfDaily = withinStatutoryTimeOfDaily;
		return domain;
	}
}
