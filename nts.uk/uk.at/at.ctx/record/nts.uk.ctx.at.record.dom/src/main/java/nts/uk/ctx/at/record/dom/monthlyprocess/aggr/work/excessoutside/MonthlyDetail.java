package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 月次明細
 * @author shuichu_ishida
 */
@Getter
public class MonthlyDetail {

	/** 残業時間 */
	private Map<OverTimeFrameNo, AggregateOverTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> holidayWorkTime;
	/** フレックス時間 */
	private Map<GeneralDate, FlexTimeOfTimeSeries> flexTime;
	/** 就業時間 */
	private List<WorkTimeOfTimeSeries> workTime;
	/** 年休使用時間 */
	private List<AnnualLeaveUseTimeOfTimeSeries> annualLeaveUseTime;
	/** 積立年休使用時間 */
	private List<RetentionYearlyUseTimeOfTimeSeries> retentionYearlyUseTime;
	/** 特別休暇使用時間 */
	private List<SpecialHolidayUseTimeOfTimeSeries> specialHolidayUseTime;
	/** 週割増割り当て時間 */
	private ReverseWeeklyPremiumAssignedTime weeklyPremiumAssignedTime;
	
	/**
	 * コンストラクタ
	 * @param attendanceTimeOfMonthly 月別実績の勤怠時間
	 */
	public MonthlyDetail(AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		
		val monthlyCalculation = attendanceTimeOfMonthly.getMonthlyCalculation();
		val totalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		val vacationUseTime = totalWorkingTime.getVacationUseTime();
		
		this.overTime = totalWorkingTime.getOverTime().getAggregateOverTimeMap();
		this.holidayWorkTime = totalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		this.flexTime = monthlyCalculation.getFlexTime().getFlexTime().getTimeSeriesWorks();
		this.workTime = totalWorkingTime.getWorkTime().getTimeSeriesWorks();
		this.annualLeaveUseTime = vacationUseTime.getAnnualLeave().getTimeSeriesWorks();
		this.retentionYearlyUseTime = vacationUseTime.getRetentionYearly().getTimeSeriesWorks();
		this.specialHolidayUseTime = vacationUseTime.getSpecialHoliday().getTimeSeriesWorks();
		this.weeklyPremiumAssignedTime = new ReverseWeeklyPremiumAssignedTime();
	}
}
