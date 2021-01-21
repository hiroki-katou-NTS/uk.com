package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 逆時系列で週割増を割り当てた時間の明細
 * @author shuichu_ishida
 */
@Getter
public class ReverseWeeklyPremiumAssignedTime {

	/** 就業時間 */
	private Map<GeneralDate, WorkTimeOfTimeSeries> workTime;
	/** 年休使用時間 */
	private Map<GeneralDate, AnnualLeaveUseTimeOfTimeSeries> annualLeaveUseTime;
	/** 積立年休使用時間 */
	private Map<GeneralDate, RetentionYearlyUseTimeOfTimeSeries> retentionYearlyUseTime;
	/** 特別休暇使用時間 */
	private Map<GeneralDate, SpecialHolidayUseTimeOfTimeSeries> specialHolidayUseTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, AggregateOverTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> holidayWorkTime;

	/**
	 * コンストラクタ
	 */
	public ReverseWeeklyPremiumAssignedTime(){
		
		this.workTime = new HashMap<>();
		this.annualLeaveUseTime = new HashMap<>();
		this.retentionYearlyUseTime = new HashMap<>();
		this.specialHolidayUseTime = new HashMap<>();
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
	}
}
