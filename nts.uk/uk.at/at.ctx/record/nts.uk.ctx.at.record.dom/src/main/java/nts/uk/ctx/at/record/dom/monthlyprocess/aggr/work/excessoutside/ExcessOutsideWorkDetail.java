package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.HolidayWorkTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.MonthlyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.OverTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WeeklyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;

/**
 * 時間外超過明細
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWorkDetail {

	/** 合計時間 */
	private TotalTime totalTimeAfterRound;
	/** フレックス超過時間 */
	private List<FlexTimeOfTimeSeries> flexExcessTime;
	/** 残業時間 */
	private List<OverTimeOfTimeSeries> overTime;
	/** 休出時間 */
	private List<HolidayWorkTimeOfTimeSeries> holidayWorkTime;
	/** 就業時間 */
	private List<WorkTimeOfTimeSeries> workTime;
	/** 週割増時間 */
	private List<WeeklyPremiumTimeOfTimeSeries> weeklyPremiumTime;
	/** 月割増時間 */
	private List<MonthlyPremiumTimeOfTimeSeries> monthlyPremiumTime;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideWorkDetail(){

		this.totalTimeAfterRound = new TotalTime();
		this.flexExcessTime = new ArrayList<>();
		this.overTime = new ArrayList<>();
		this.holidayWorkTime = new ArrayList<>();
		this.workTime = new ArrayList<>();
		this.weeklyPremiumTime = new ArrayList<>();
		this.monthlyPremiumTime = new ArrayList<>();
	}
}
