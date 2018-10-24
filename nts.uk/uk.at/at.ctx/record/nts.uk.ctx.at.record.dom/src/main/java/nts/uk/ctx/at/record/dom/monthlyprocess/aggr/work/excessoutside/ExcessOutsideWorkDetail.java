package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.MonthlyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WeeklyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 時間外超過明細
 * @author shuichi_ishida
 */
@Getter
public class ExcessOutsideWorkDetail {

	/** 就業時間 */
	private Map<GeneralDate, WorkTimeOfTimeSeries> workTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, AggregateOverTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> holidayWorkTime;
	/** フレックス超過時間 */
	private Map<GeneralDate, FlexTimeOfTimeSeries> flexExcessTime;
	/** 週割増時間 */
	private Map<GeneralDate, WeeklyPremiumTimeOfTimeSeries> weeklyPremiumTime;
	/** 月割増時間 */
	private Map<GeneralDate, MonthlyPremiumTimeOfTimeSeries> monthlyPremiumTime;
	/** 丸め後合計時間 */
	private TotalTime totalTimeAfterRound;
	/** 丸め差分時間 */
	private TotalTime roundDiffTime;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideWorkDetail(){

		this.workTime = new HashMap<>();
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
		this.flexExcessTime = new HashMap<>();
		this.weeklyPremiumTime = new HashMap<>();
		this.monthlyPremiumTime = new HashMap<>();
		this.totalTimeAfterRound = new TotalTime();
		this.roundDiffTime = new TotalTime();
	}
	
	/**
	 * フレックス超過時間合計を取得する
	 * @param datePeriod 期間
	 * @return フレックス超過時間合計
	 */
	public AttendanceTimeMonthWithMinus getTotalFlexExcessTime(DatePeriod datePeriod){
		
		AttendanceTimeMonthWithMinus totalTime = new AttendanceTimeMonthWithMinus(0);
		for (val timeSeriesWork : this.flexExcessTime.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val flexTime = timeSeriesWork.getFlexTime().getFlexTime().getTime();
			totalTime = totalTime.addMinutes(flexTime.v());
		}
		return totalTime;
	}
	
	/**
	 * 丸め後合計時間に移送する
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param regAndIrgTimeOfMonthly 月別実績の通常変形時間
	 * @param flexTimeOfMonthly 月別実績のフレックス時間
	 * @param flexAggrSet フレックス時間勤務の月の集計設定
	 * @param outsideOTBDItems 時間外超過設定：内訳項目一覧（積上番号順）
	 * @param roundingSet 月別実績の丸め設定
	 */
	public void setTotalTimeAfterRound(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RegularAndIrregularTimeOfMonthly regAndIrgTimeOfMonthly,
			FlexTimeOfMonthly flexTimeOfMonthly,
			FlexMonthWorkTimeAggrSet flexAggrSet,
			List<OutsideOTBRDItem> outsideOTBDItems,
			RoundingSetOfMonthly roundingSet){
		
		// 丸め前合計時間にコピーする
		TotalTimeBeforeRound totalTimeBeforeRound = new TotalTimeBeforeRound();
		totalTimeBeforeRound.copyValues(aggregateTotalWorkingTime,
				regAndIrgTimeOfMonthly, flexTimeOfMonthly, flexAggrSet, outsideOTBDItems);
		
		// 各合計時間を丸める
		this.totalTimeAfterRound.setTotalTimeAfterRound(totalTimeBeforeRound, roundingSet);
	}

	/**
	 * フレックス超過対象時間を日単位で割り当てる
	 * @param procDate 処理日
	 * @param flexExcessTargetTime フレックス超過対象時間
	 * @param flexTimeOfMonthly 月別実績のフレックス時間
	 * @return フレックス超過対象時間　（割り当て後）
	 */
	public AttendanceTimeMonthWithMinus assignFlexExcessTargetTimeByDayUnit(
			GeneralDate procDate,
			AttendanceTimeMonthWithMinus flexExcessTargetTime,
			FlexTimeOfMonthly flexTimeOfMonthly){
		
		AttendanceTimeMonthWithMinus timeAfterAssign = flexExcessTargetTime; 
		
		// フレックス時間を確認する
		val timeSeriesWorks = flexTimeOfMonthly.getFlexTime().getTimeSeriesWorks();
		if (!timeSeriesWorks.containsKey(procDate)) return timeAfterAssign;
		val flexMinutes = timeSeriesWorks.get(procDate).getFlexTime().getFlexTime().getTime().v();
		if (flexMinutes <= 0) return timeAfterAssign;
		
		// フレックス時間を上限として時間外超過明細に割り当てる
		int assignMinutes = timeAfterAssign.v();
		if (assignMinutes > flexMinutes) assignMinutes = flexMinutes;
		this.flexExcessTime.putIfAbsent(procDate, new FlexTimeOfTimeSeries(procDate));
		this.flexExcessTime.get(procDate).addMinutesToFlexTimeInFlexTime(assignMinutes);
		
		// 時間外超過明細に入れた分を「フレックス超過対象時間」から引く
		timeAfterAssign = timeAfterAssign.minusMinutes(assignMinutes);
		
		return timeAfterAssign;
	}
	
	/**
	 * 丸め時間を割り当てる
	 * @param monthlyDetail 月次明細
	 * @param datePeriod 期間
	 */
	public void assignRoundTime(MonthlyDetail monthlyDetail, DatePeriod datePeriod, RoundingSetOfMonthly roundingSet){
		
		// 月次明細から時間外超過明細へ値をコピーする
		this.workTime = monthlyDetail.getWorkTime();
		this.overTime = monthlyDetail.getOverTime();
		this.holidayWorkTime = monthlyDetail.getHolidayWorkTime();
		
		// 各時系列の時間を集計する
		TotalTimeBeforeRound totalTimeBeforeRound = new TotalTimeBeforeRound();
		totalTimeBeforeRound.aggregateValues(this, datePeriod);
		
		// 各合計時間を丸める
		this.totalTimeAfterRound.setTotalTimeAfterRound(totalTimeBeforeRound, roundingSet);
		
		// 丸め差分時間を求める
		this.askRoundDiffTime(totalTimeBeforeRound);
	}
	
	/**
	 * 丸め差分時間を求める
	 * @param totalTimeBeforeRound 丸め前合計時間
	 */
	private void askRoundDiffTime(TotalTimeBeforeRound totalTimeBeforeRound){
		
		// 就業時間
		int diffWorkMinutes = this.totalTimeAfterRound.getWorkTime().v()
				- totalTimeBeforeRound.getWorkTime().v();
		int diffWithinPrescribedMinutes = this.totalTimeAfterRound.getWithinPrescribedPremiumTime().v()
				- totalTimeBeforeRound.getWithinPrescribedPremiumTime().v();
		this.roundDiffTime.setWorkTime(new AttendanceTimeMonth(diffWorkMinutes));
		this.roundDiffTime.setWithinPrescribedPremiumTime(new AttendanceTimeMonth(diffWithinPrescribedMinutes));
		
		// 残業時間
		for (val overTimeEachFrameNo : this.totalTimeAfterRound.getOverTime().values()){
			val overTimeFrameNo = overTimeEachFrameNo.getOverTimeFrameNo();
			if (!totalTimeBeforeRound.getOverTime().containsKey(overTimeFrameNo)) continue;
			val overTimeBeforeRound = totalTimeBeforeRound.getOverTime().get(overTimeFrameNo);
			
			int diffOverMinutes = overTimeEachFrameNo.getOverTime().getTime().v()
					- overTimeBeforeRound.getOverTime().getTime().v();
			
			int diffCalcOverMinutes = overTimeEachFrameNo.getOverTime().getCalcTime().v()
					- overTimeBeforeRound.getOverTime().getCalcTime().v();
			
			int diffTransOverMinutes = overTimeEachFrameNo.getTransferOverTime().getTime().v()
					- overTimeBeforeRound.getTransferOverTime().getTime().v();
			
			int diffCalcTransOverMinutes = overTimeEachFrameNo.getTransferOverTime().getCalcTime().v()
					- overTimeBeforeRound.getTransferOverTime().getCalcTime().v();
			
			this.roundDiffTime.getOverTime().putIfAbsent(overTimeFrameNo,
					OverTimeFrameTotalTime.of(overTimeFrameNo,
							new TimeMonthWithCalculation(
									new AttendanceTimeMonth(diffOverMinutes),
									new AttendanceTimeMonth(diffCalcOverMinutes)),
							new TimeMonthWithCalculation(
									new AttendanceTimeMonth(diffTransOverMinutes),
									new AttendanceTimeMonth(diffCalcTransOverMinutes))));
		}
		
		// 休出時間
		for (val holidayWorkTimeEachFrameNo : this.totalTimeAfterRound.getHolidayWorkTime().values()){
			val holidayWorkTimeFrameNo = holidayWorkTimeEachFrameNo.getHolidayWorkFrameNo();
			if (!totalTimeBeforeRound.getHolidayWorkTime().containsKey(holidayWorkTimeFrameNo)) continue;
			val holidayWorkTimeBeforeRound = totalTimeBeforeRound.getHolidayWorkTime().get(holidayWorkTimeFrameNo);
			
			int diffHolidayWorkMinutes = holidayWorkTimeEachFrameNo.getHolidayWorkTime().getTime().v()
					- holidayWorkTimeBeforeRound.getHolidayWorkTime().getTime().v();
			
			int diffCalcHolidayWorkMinutes = holidayWorkTimeEachFrameNo.getHolidayWorkTime().getCalcTime().v()
					- holidayWorkTimeBeforeRound.getHolidayWorkTime().getCalcTime().v();
			
			int diffTransMinutes = holidayWorkTimeEachFrameNo.getTransferTime().getTime().v()
					- holidayWorkTimeBeforeRound.getTransferTime().getTime().v();
			
			int diffCalcTransMinutes = holidayWorkTimeEachFrameNo.getTransferTime().getCalcTime().v()
					- holidayWorkTimeBeforeRound.getTransferTime().getCalcTime().v();
			
			this.roundDiffTime.getHolidayWorkTime().putIfAbsent(holidayWorkTimeFrameNo,
					HolidayWorkFrameTotalTime.of(holidayWorkTimeFrameNo,
							new TimeMonthWithCalculation(
									new AttendanceTimeMonth(diffHolidayWorkMinutes),
									new AttendanceTimeMonth(diffCalcHolidayWorkMinutes)),
							new TimeMonthWithCalculation(
									new AttendanceTimeMonth(diffTransMinutes),
									new AttendanceTimeMonth(diffCalcTransMinutes))));
		}
		
		// フレックス超過時間
		int diffFlexExcessMinutes = this.totalTimeAfterRound.getFlexExcessTime().v()
				- totalTimeBeforeRound.getFlexExcessTime().v();
		this.roundDiffTime.setFlexExcessTime(new AttendanceTimeMonth(diffFlexExcessMinutes));
		
		// 週割増合計時間
		int diffWeekPremiumMinutes = this.totalTimeAfterRound.getWeeklyTotalPremiumTime().v()
				- totalTimeBeforeRound.getWeeklyTotalPremiumTime().v();
		this.roundDiffTime.setWeeklyTotalPremiumTime(new AttendanceTimeMonth(diffWeekPremiumMinutes));
		
		// 月割増合計時間
		int diffMonthPremiumMinutes = this.totalTimeAfterRound.getMonthlyTotalPremiumTime().v()
				- totalTimeBeforeRound.getMonthlyTotalPremiumTime().v();
		this.roundDiffTime.setMonthlyTotalPremiumTime(new AttendanceTimeMonth(diffMonthPremiumMinutes));
	}
	
	/**
	 * 勤怠項目IDに対応する時間を取得する
	 * @param attendanceItemId 勤怠項目ID
	 * @param procDate 処理日
	 * @return 勤怠月間時間
	 */
	public AttendanceTimeMonth getTimeOfAttendanceItemId(int attendanceItemId, GeneralDate procDate){

		AttendanceTimeMonth notExistTime = new AttendanceTimeMonth(0);
		
		// 就業時間
		if (attendanceItemId == AttendanceItemOfMonthly.WORK_TIME.value) {
			if (!this.workTime.containsKey(procDate)) return notExistTime;
			return new AttendanceTimeMonth(this.workTime.get(procDate).getLegalTime().getWorkTime().v());
		}
		
		// 残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.OVER_TIME_01.value + 1);
			if (!this.overTime.containsKey(overTimeFrameNo)) return notExistTime;
			val overTimeDetails = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!overTimeDetails.containsKey(procDate)) return notExistTime;
			val overTimeDetail = overTimeDetails.get(procDate).getOverTime();
			return new AttendanceTimeMonth(overTimeDetail.getOverTimeWork().getTime().v());
		}
		
		// 計算残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + 1);
			if (!this.overTime.containsKey(overTimeFrameNo)) return notExistTime;
			val overTimeDetails = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!overTimeDetails.containsKey(procDate)) return notExistTime;
			val overTimeDetail = overTimeDetails.get(procDate).getOverTime();
			return new AttendanceTimeMonth(overTimeDetail.getOverTimeWork().getCalcTime().v());
		}
		
		// 振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + 1);
			if (!this.overTime.containsKey(overTimeFrameNo)) return notExistTime;
			val overTimeDetails = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!overTimeDetails.containsKey(procDate)) return notExistTime;
			val overTimeDetail = overTimeDetails.get(procDate).getOverTime();
			return new AttendanceTimeMonth(overTimeDetail.getTransferTime().getTime().v());
		}
		
		// 計算振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + 1);
			if (!this.overTime.containsKey(overTimeFrameNo)) return notExistTime;
			val overTimeDetails = this.overTime.get(overTimeFrameNo).getTimeSeriesWorks();
			if (!overTimeDetails.containsKey(procDate)) return notExistTime;
			val overTimeDetail = overTimeDetails.get(procDate).getOverTime();
			return new AttendanceTimeMonth(overTimeDetail.getTransferTime().getCalcTime().v());
		}
		
		// 休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + 1);
			if (!this.holidayWorkTime.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			val holidayWorkTimeDetails = this.holidayWorkTime.get(holidayWorkTimeFrameNo).getTimeSeriesWorks();
			if (!holidayWorkTimeDetails.containsKey(procDate)) return notExistTime;
			val holidayWorkTimeDetail = holidayWorkTimeDetails.get(procDate).getHolidayWorkTime();
			return new AttendanceTimeMonth(holidayWorkTimeDetail.getHolidayWorkTime().get().getTime().v());
		}
		
		// 計算休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + 1);
			if (!this.holidayWorkTime.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			val holidayWorkTimeDetails = this.holidayWorkTime.get(holidayWorkTimeFrameNo).getTimeSeriesWorks();
			if (!holidayWorkTimeDetails.containsKey(procDate)) return notExistTime;
			val holidayWorkTimeDetail = holidayWorkTimeDetails.get(procDate).getHolidayWorkTime();
			return new AttendanceTimeMonth(holidayWorkTimeDetail.getHolidayWorkTime().get().getCalcTime().v());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_TIME_01.value + 1);
			if (!this.holidayWorkTime.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			val holidayWorkTimeDetails = this.holidayWorkTime.get(holidayWorkTimeFrameNo).getTimeSeriesWorks();
			if (!holidayWorkTimeDetails.containsKey(procDate)) return notExistTime;
			val holidayWorkTimeDetail = holidayWorkTimeDetails.get(procDate).getHolidayWorkTime();
			return new AttendanceTimeMonth(holidayWorkTimeDetail.getTransferTime().get().getTime().v());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + 1);
			if (!this.holidayWorkTime.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			val holidayWorkTimeDetails = this.holidayWorkTime.get(holidayWorkTimeFrameNo).getTimeSeriesWorks();
			if (!holidayWorkTimeDetails.containsKey(procDate)) return notExistTime;
			val holidayWorkTimeDetail = holidayWorkTimeDetails.get(procDate).getHolidayWorkTime();
			return new AttendanceTimeMonth(holidayWorkTimeDetail.getTransferTime().get().getCalcTime().v());
		}
		
		// フレックス超過時間　（フレックス時間のプラス分）
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			if (!this.flexExcessTime.containsKey(procDate)) return notExistTime;
			Integer flexExcessMinutes = this.flexExcessTime.get(procDate).getFlexTime().getFlexTime().getTime().v();
			if (flexExcessMinutes <= 0) flexExcessMinutes = 0;
			return new AttendanceTimeMonth(flexExcessMinutes);
		}
		
		// 所定内割増時間
		if (attendanceItemId == AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value){
			if (!this.workTime.containsKey(procDate)) return notExistTime;
			return new AttendanceTimeMonth(this.workTime.get(procDate).getLegalTime().getWithinPrescribedPremiumTime().v());
		}
		
		// 週割増合計時間
		if (attendanceItemId == AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value){
			if (!this.weeklyPremiumTime.containsKey(procDate)) return notExistTime;
			return this.weeklyPremiumTime.get(procDate).getWeeklyPremiumTime();
		}
		
		// 月割増合計時間
		if (attendanceItemId == AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value){
			if (!this.monthlyPremiumTime.containsKey(procDate)) return notExistTime;
			return this.monthlyPremiumTime.get(procDate).getMonthlyTotalPremiumTime();
		}
		
		return notExistTime;
	}
}
