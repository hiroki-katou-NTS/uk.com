package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.HolidayWorkTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.OverTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;

/**
 * 逆時系列で週割増を割り当てた時間の明細
 * @author shuichu_ishida
 */
@Getter
public class ReverseWeeklyPremiumAssignedTime {

	/** 残業時間 */
	private List<OverTimeOfTimeSeries> overTime;
	/** 休出時間 */
	private List<HolidayWorkTimeOfTimeSeries> holidayWorkTime;
	/** 就業時間 */
	private List<WorkTimeOfTimeSeries> workTime;
	/** 年休使用時間 */
	private List<AnnualLeaveUseTimeOfTimeSeries> annualLeaveUseTime;
	/** 積立年休使用時間 */
	private List<RetentionYearlyUseTimeOfTimeSeries> retentionYearlyUseTime;
	/** 特別休暇使用時間 */
	private List<SpecialHolidayUseTimeOfTimeSeries> specialHolidayUseTime;

	/**
	 * コンストラクタ
	 */
	public ReverseWeeklyPremiumAssignedTime(){
		
		this.overTime = new ArrayList<>();
		this.holidayWorkTime = new ArrayList<>();
		this.workTime = new ArrayList<>();
		this.annualLeaveUseTime = new ArrayList<>();
		this.retentionYearlyUseTime = new ArrayList<>();
		this.specialHolidayUseTime = new ArrayList<>();
	}
}
