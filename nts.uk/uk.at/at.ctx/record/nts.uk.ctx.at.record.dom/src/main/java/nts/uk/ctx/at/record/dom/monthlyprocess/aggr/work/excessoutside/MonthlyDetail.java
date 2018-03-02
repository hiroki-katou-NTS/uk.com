package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WeeklyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.HolidayWorkAndTransferAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.OverTimeAndTransferAtr;

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

	/** 時間外超過管理 */
	private ExcessOutsideWorkMng excessOutsideWorkMng;
	
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
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	public AttendanceTimeMonthWithMinus assignWeeklyPremiumTimeByDayUnit(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign,
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap,
			ExcessOutsideWorkMng excessOutsideWorkMng,
			RepositoriesRequiredByMonthlyAggr repositories){

		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		// 各時系列ワーク　確認
		this.workTime = aggregateTotalWorkingTime.getWorkTime().getTimeSeriesWorks();
		val vacationUseTime = aggregateTotalWorkingTime.getVacationUseTime();
		this.annualLeaveUseTime = vacationUseTime.getAnnualLeave().getTimeSeriesWorks();
		this.retentionYearlyUseTime = vacationUseTime.getRetentionYearly().getTimeSeriesWorks();
		this.specialHolidayUseTime = vacationUseTime.getSpecialHoliday().getTimeSeriesWorks();
		this.overTime = aggregateTotalWorkingTime.getOverTime().getAggregateOverTimeMap();
		this.holidayWorkTime = aggregateTotalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		this.excessOutsideWorkMng = excessOutsideWorkMng;
		
		// 休出時間を限度として割り当てる（週割）
		weeklyPTAfterAssign = this.assignWithHolidayWorkTimeAsLimit(
				procDate, weeklyPTAfterAssign, workInformationOfDailyMap, repositories);
		
		// 残業時間を限度として割り当てる（週割）
		if (weeklyPTAfterAssign.greaterThan(0)){
			weeklyPTAfterAssign = this.assignWithOverTimeAsLimit(
					procDate, weeklyPTAfterAssign, workInformationOfDailyMap, repositories);
		}
		
		// 就業時間を限度として割り当てる（週割）
		if (weeklyPTAfterAssign.greaterThan(0)){
			weeklyPTAfterAssign = this.assignWithWorkTimeAsLimit(procDate, weeklyPTAfterAssign);
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 休出時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithHolidayWorkTimeAsLimit(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		if (!workInformationOfDailyMap.containsKey(procDate)) return weeklyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 休出・振替の処理順序を取得する（逆時系列用）
		val holidayWorkAndTransferAtrs = repositories.getHolidayWorkAndTransferOrder().get(
				this.excessOutsideWorkMng.getCompanyId(), workTimeCode, true);
		
		// 休出・振替のループ
		for (val holidayWorkAndTransferAtr : holidayWorkAndTransferAtrs){
		
			// 休出枠時間のループ処理
			weeklyPTAfterAssign = this.holidayWorkFrameTimeProcess(
					procDate, holidayWorkAndTransferAtr, weeklyPTAfterAssign);
			
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 休出枠時間のループ処理（逆時系列用）
	 * @param procDate 処理日
	 * @param holidayWorkAndTransferAtr 休出振替区分
	 * @param weeklyPTForAssignReverseTimeSeries 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus holidayWorkFrameTimeProcess(
			GeneralDate procDate,
			HolidayWorkAndTransferAtr holidayWorkAndTransferAtr,
			AttendanceTimeMonthWithMinus weeklyPTForAssign){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getHolidayWorkTime();
		
		// 振替順を取得する
		val holidayWorkTransferOrder =
				this.excessOutsideWorkMng.getLegalTransferOrderSet().getLegalHolidayWorkTransferOrder();
		
		// 休出枠時間分ループ
		for (val legalHolidayWorkTransferOrder : holidayWorkTransferOrder.getLegalHolidayWorkTransferOrders()){
			
			// 該当枠・年月日の「時系列の休出時間」を取得
			val holidayWorkFrameNo = legalHolidayWorkTransferOrder.getHolidayWorkFrameNo();
			if (!this.holidayWorkTime.containsKey(holidayWorkFrameNo)) continue;
			val timeSeriesWorks = this.holidayWorkTime.get(holidayWorkFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val legalHolidayWorkTime = timeSeriesWorks.get(procDate).getLegalHolidayWorkTime();

			Integer assignMinutes = weeklyPTAfterAssign.v();
			
			switch (holidayWorkAndTransferAtr){
			case HOLIDAY_WORK:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val holidayWorkTimeMinutes = legalHolidayWorkTime.getHolidayWorkTime().get().getTime().v(); 
				if (assignMinutes > holidayWorkTimeMinutes) assignMinutes = holidayWorkTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
				val targetHWTimeSeries = assignedDetail.get(holidayWorkFrameNo).getAndPutTimeSeriesWork(procDate);
				targetHWTimeSeries.addHolidayWorkTimeInLegalHolidayWorkTime(
						TimeWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
				
			case TRANSFER:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val transferTimeMinutes = legalHolidayWorkTime.getTransferTime().get().getTime().v(); 
				if (assignMinutes > transferTimeMinutes) assignMinutes = transferTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
				val targetTransTimeSeries = assignedDetail.get(holidayWorkFrameNo).getAndPutTimeSeriesWork(procDate);
				targetTransTimeSeries.addTransferTimeInLegalHolidayWorkTime(
						TimeWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
			}
			
			// 「逆時系列割り当て用の週割増時間」が残っているか確認する
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 残業時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @param workInformationOfDailyMap 日別実績の勤務情報リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithOverTimeAsLimit(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign,
			Map<GeneralDate, WorkInformation> workInformationOfDailyMap,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		
		if (!workInformationOfDailyMap.containsKey(procDate)) return weeklyPTAfterAssign;
		val workInfo = workInformationOfDailyMap.get(procDate);
		val workTimeCode = workInfo.getWorkTimeCode().v();

		// 残業・振替の処理順序を取得する（逆時系列用）
		val overTimeAndTransferAtrs = repositories.getOverTimeAndTransferOrder().get(
				this.excessOutsideWorkMng.getCompanyId(), workTimeCode, true);
		
		// 残業・振替のループ
		for (val overTimeAndTransferAtr : overTimeAndTransferAtrs){
		
			// 残業枠時間のループ処理
			weeklyPTAfterAssign = this.overTimeFrameTimeProcess(
					procDate, overTimeAndTransferAtr, weeklyPTAfterAssign);
			
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 残業枠時間のループ処理（逆時系列用）
	 * @param procDate 処理日
	 * @param overTimeAndTransferAtr 残業振替区分
	 * @param weeklyPTForAssignReverseTimeSeries 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus overTimeFrameTimeProcess(
			GeneralDate procDate,
			OverTimeAndTransferAtr overTimeAndTransferAtr,
			AttendanceTimeMonthWithMinus weeklyPTForAssign){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getOverTime();
		
		// 振替順を取得する
		val overTimeTransferOrder =
				this.excessOutsideWorkMng.getLegalTransferOrderSet().getLegalOverTimeTransferOrder();
		
		// 残業枠時間分ループ
		for (val legalOverTimeTransferOrder : overTimeTransferOrder.getLegalOverTimeTransferOrders()){
			
			// 該当枠・年月日の「時系列の残業時間」を取得
			val overTimeFrameNo = legalOverTimeTransferOrder.getOverTimeFrameNo();
			if (!this.overTime.containsKey(overTimeFrameNo)) continue;
			val timeSeriesWorks = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!timeSeriesWorks.containsKey(procDate)) continue;
			val legalOverTime = timeSeriesWorks.get(procDate).getLegalOverTime();

			Integer assignMinutes = weeklyPTAfterAssign.v();
			
			switch (overTimeAndTransferAtr){
			case OVER_TIME:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val overTimeMinutes = legalOverTime.getOverTimeWork().getTime().v(); 
				if (assignMinutes > overTimeMinutes) assignMinutes = overTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(overTimeFrameNo, new AggregateOverTime(overTimeFrameNo));
				val targetOTTimeSeries = assignedDetail.get(overTimeFrameNo).getAndPutTimeSeriesWork(procDate);
				targetOTTimeSeries.addOverTimeInLegalOverTime(
						TimeWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
				
			case TRANSFER:
				
				// 月次明細を限度として「時系列の週割増時間」に割り当てる
				val transferTimeMinutes = legalOverTime.getTransferTime().getTime().v(); 
				if (assignMinutes > transferTimeMinutes) assignMinutes = transferTimeMinutes;
				weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
				weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
				
				// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
				weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

				// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
				assignedDetail.putIfAbsent(overTimeFrameNo, new AggregateOverTime(overTimeFrameNo));
				val targetTransTimeSeries = assignedDetail.get(overTimeFrameNo).getAndPutTimeSeriesWork(procDate);
				targetTransTimeSeries.addTransferTimeInLegalOverTime(
						TimeWithCalculation.createTimeWithCalculation(
								new AttendanceTime(assignMinutes), new AttendanceTime(0)));
				
				break;
			}
			
			// 「逆時系列割り当て用の週割増時間」が残っているか確認する
			if (weeklyPTAfterAssign.lessThanOrEqualTo(0)) break;
		}
		
		return weeklyPTAfterAssign;
	}
	
	/**
	 * 就業時間を限度として割り当てる（週割）
	 * @param procDate 処理日
	 * @param weeklyPTForAssign 逆時系列割り当て用の週割増時間
	 * @return 逆時系列割り当て用の週割増時間　（割り当て後）
	 */
	private AttendanceTimeMonthWithMinus assignWithWorkTimeAsLimit(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus weeklyPTForAssign){
		
		AttendanceTimeMonthWithMinus weeklyPTAfterAssign = new AttendanceTimeMonthWithMinus(weeklyPTForAssign.v());
		val weeklyPremiumTime = this.excessOutsideWorkMng.getExcessOutsideWorkDetail().getWeeklyPremiumTime();
		val assignedDetail = this.weeklyPremiumAssignedTime.getWorkTime();
		
		// 該当枠・年月日の「時系列の就業時間」を取得
		val timeSeriesWorks = this.workTime;
		if (!timeSeriesWorks.containsKey(procDate)) return weeklyPTAfterAssign;
		val legalWorkTime = timeSeriesWorks.get(procDate).getLegalTime();

		Integer assignMinutes = weeklyPTAfterAssign.v();
		
		// 月次明細を限度として「時系列の週割増時間」に割り当てる
		val workTimeMinutes = legalWorkTime.getWorkTime().v(); 
		if (assignMinutes > workTimeMinutes) assignMinutes = workTimeMinutes;
		weeklyPremiumTime.putIfAbsent(procDate, new WeeklyPremiumTimeOfTimeSeries(procDate));
		weeklyPremiumTime.get(procDate).addMinutesToWeeklyPremiumTime(assignMinutes);
		
		// 「時系列の週割増時間」に入れた分を「逆時系列割り当て用の週割増時間」から引く
		weeklyPTAfterAssign = weeklyPTAfterAssign.minusMinutes(assignMinutes);

		// 「時系列の週割増時間」に入れた分を「逆時系列で週割増を割り当てた時間の明細」に入れる
		assignedDetail.putIfAbsent(procDate, WorkTimeOfTimeSeries.of(
				procDate,
				WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
						new AttendanceTime(assignMinutes),
						new AttendanceTime(0),
						new AttendanceTime(0),
						new WithinStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0))),
						new AttendanceTime(0))
				));
		
		return weeklyPTAfterAssign;
	}
}
