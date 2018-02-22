package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 月次明細
 * @author shuichu_ishida
 */
@Getter
public class MonthlyDetail {

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
	/** フレックス時間 */
	private Map<GeneralDate, FlexTimeOfTimeSeries> flexTime;
	/** 週割増割り当て時間 */
	private ReverseWeeklyPremiumAssignedTime weeklyPremiumAssignedTime;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyDetail(){
		
		this.workTime = new HashMap<>();
		this.annualLeaveUseTime = new HashMap<>();
		this.retentionYearlyUseTime = new HashMap<>();
		this.specialHolidayUseTime = new HashMap<>();
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
		this.flexTime = new HashMap<>();
		this.weeklyPremiumAssignedTime = new ReverseWeeklyPremiumAssignedTime();
	}
	
	/**
	 * 週割増時間を日単位で割り当てる
	 * @param procDate 処理日
	 * @param weeklyPTForAssignReverseTimeSeries 逆時系列割り当て用の週割増時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void assignWeeklyPremiumTimeByDayUnit(
			GeneralDate procDate,
			AttendanceTimeMonth weeklyPTForAssignReverseTimeSeries,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap,
			ExcessOutsideWorkMng excessOutsideWorkMng,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 各時系列ワーク　確認
		this.workTime = aggregateTotalWorkingTime.getWorkTime().getTimeSeriesWorks();
		val vacationUseTime = aggregateTotalWorkingTime.getVacationUseTime();
		this.annualLeaveUseTime = vacationUseTime.getAnnualLeave().getTimeSeriesWorks();
		this.retentionYearlyUseTime = vacationUseTime.getRetentionYearly().getTimeSeriesWorks();
		this.specialHolidayUseTime = vacationUseTime.getSpecialHoliday().getTimeSeriesWorks();
		this.overTime = aggregateTotalWorkingTime.getOverTime().getAggregateOverTimeMap();
		this.holidayWorkTime = aggregateTotalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		// 休出時間を限度として割り当てる（週割）
		this.assignWithHolidayWorkTimeAsLimit(procDate, weeklyPTForAssignReverseTimeSeries,
				workInformationOfDailyMap, excessOutsideWorkMng, repositories);
		
		// 残業時間を限度として割り当てる（週割）
		
		// 就業時間を限度として割り当てる（週割）
	}
	
	/**
	 * 休出時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssignReverseTimeSeries 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void assignWithHolidayWorkTimeAsLimit(
			GeneralDate procDate,
			AttendanceTimeMonth weeklyPTForAssignReverseTimeSeries,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap,
			ExcessOutsideWorkMng excessOutsideWorkMng,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		if (!workInformationOfDailyMap.containsKey(procDate)) return;
		val workInfo = workInformationOfDailyMap.get(procDate);

		// 休出・振替の処理順序を取得する（逆時系列用）
		val procAtrHolidayWorkAndTransferList = excessOutsideWorkMng.getHolidayWorkAndTransferOrder(workInfo, repositories);
		
		// 休出・振替のループ
		for (val procAtrHolidayWorkAndTransfer : procAtrHolidayWorkAndTransferList){
		
			// 休出枠時間のループ処理
			
		}
	}
}
