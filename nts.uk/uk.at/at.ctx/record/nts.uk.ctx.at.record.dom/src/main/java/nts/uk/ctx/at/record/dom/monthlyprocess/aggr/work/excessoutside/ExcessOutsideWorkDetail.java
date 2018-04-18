package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.MonthlyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WeeklyPremiumTimeOfTimeSeries;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 時間外超過明細
 * @author shuichu_ishida
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
	 * @param aggrSetOfFlex フレックス時間勤務の月の集計設定
	 * @param roundingSet 月別実績の丸め設定
	 */
	public void setTotalTimeAfterRound(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			RegularAndIrregularTimeOfMonthly regAndIrgTimeOfMonthly,
			FlexTimeOfMonthly flexTimeOfMonthly,
			AggrSettingMonthlyOfFlx aggrSetOfFlex,
			RoundingSetOfMonthly roundingSet){
		
		// 丸め前合計時間にコピーする
		TotalTimeBeforeRound totalTimeBeforeRound = new TotalTimeBeforeRound();
		totalTimeBeforeRound.copyValues(aggregateTotalWorkingTime,
				regAndIrgTimeOfMonthly, flexTimeOfMonthly, aggrSetOfFlex);
		
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
		
		// 丸め差分時間を求める　→　丸め差分時間を時系列の時間に割り当てる
		this.assignRoundDiffTime(totalTimeBeforeRound, datePeriod.end());
	}
	
	/**
	 * 丸め差分時間を割り当てる
	 * @param totalTimeBeforeRound 丸め前合計時間
	 * @param assignDate 割り当て日
	 */
	private void assignRoundDiffTime(TotalTimeBeforeRound totalTimeBeforeRound, GeneralDate assignDate){
		
		// 就業時間
		int diffWorkMinutes = this.totalTimeAfterRound.getWorkTime().v()
				- totalTimeBeforeRound.getWorkTime().v();
		if (diffWorkMinutes < 0) diffWorkMinutes = 0; 
		int diffWithinPrescribedMinutes = this.totalTimeAfterRound.getWithinPrescribedPremiumTime().v()
				- totalTimeBeforeRound.getWithinPrescribedPremiumTime().v();
		if (diffWithinPrescribedMinutes < 0) diffWithinPrescribedMinutes = 0;
		this.workTime.putIfAbsent(assignDate, new WorkTimeOfTimeSeries(assignDate));
		val workTimeDetail = this.workTime.get(assignDate);
		workTimeDetail.addLegalTime(WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
				new AttendanceTime(diffWorkMinutes),
				new AttendanceTime(0),
				new AttendanceTime(diffWithinPrescribedMinutes),
				new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				new AttendanceTime(0)));
		
		// 残業時間
		for (val overTimeEachFrameNo : this.totalTimeAfterRound.getOverTime().values()){
			val overTimeFrameNo = overTimeEachFrameNo.getOverTimeFrameNo();
			if (!totalTimeBeforeRound.getOverTime().containsKey(overTimeFrameNo)) continue;
			val overTimeBeforeRound = totalTimeBeforeRound.getOverTime().get(overTimeFrameNo);
			this.overTime.putIfAbsent(overTimeFrameNo, new AggregateOverTime(overTimeFrameNo));
			val overTimeDetail = this.overTime.get(overTimeFrameNo);
			val targetWork = overTimeDetail.getAndPutTimeSeriesWork(assignDate);
			
			int diffOverMinutes = overTimeEachFrameNo.getOverTime().getTime().v()
					- overTimeBeforeRound.getOverTime().getTime().v();
			if (diffOverMinutes < 0) diffOverMinutes = 0;
			
			int diffCalcOverMinutes = overTimeEachFrameNo.getOverTime().getCalcTime().v()
					- overTimeBeforeRound.getOverTime().getCalcTime().v();
			if (diffCalcOverMinutes < 0) diffCalcOverMinutes = 0;
			
			int diffTransOverMinutes = overTimeEachFrameNo.getTransferOverTime().getTime().v()
					- overTimeBeforeRound.getTransferOverTime().getTime().v();
			if (diffTransOverMinutes < 0) diffTransOverMinutes = 0;
			
			int diffCalcTransOverMinutes = overTimeEachFrameNo.getTransferOverTime().getCalcTime().v()
					- overTimeBeforeRound.getTransferOverTime().getCalcTime().v();
			if (diffCalcTransOverMinutes < 0) diffCalcTransOverMinutes = 0;
			
			targetWork.addOverTime(new OverTimeFrameTime(
					overTimeFrameNo,
					TimeDivergenceWithCalculation.createTimeWithCalculation(
							new AttendanceTime(diffOverMinutes),
							new AttendanceTime(diffCalcOverMinutes)),
					TimeDivergenceWithCalculation.createTimeWithCalculation(
							new AttendanceTime(diffTransOverMinutes),
							new AttendanceTime(diffCalcTransOverMinutes)),
					new AttendanceTime(0),
					new AttendanceTime(0)));
		}
		
		// 休出時間
		for (val holidayWorkTimeEachFrameNo : this.totalTimeAfterRound.getHolidayWorkTime().values()){
			val holidayWorkTimeFrameNo = holidayWorkTimeEachFrameNo.getHolidayWorkFrameNo();
			if (!totalTimeBeforeRound.getHolidayWorkTime().containsKey(holidayWorkTimeFrameNo)) continue;
			val holidayWorkTimeBeforeRound = totalTimeBeforeRound.getHolidayWorkTime().get(holidayWorkTimeFrameNo);
			this.holidayWorkTime.putIfAbsent(holidayWorkTimeFrameNo, new AggregateHolidayWorkTime(holidayWorkTimeFrameNo));
			val holidayWorkTimeDetail = this.holidayWorkTime.get(holidayWorkTimeFrameNo);
			val targetWork = holidayWorkTimeDetail.getAndPutTimeSeriesWork(assignDate);
			
			int diffHolidayWorkMinutes = holidayWorkTimeEachFrameNo.getHolidayWorkTime().getTime().v()
					- holidayWorkTimeBeforeRound.getHolidayWorkTime().getTime().v();
			if (diffHolidayWorkMinutes < 0) diffHolidayWorkMinutes = 0;
			
			int diffCalcHolidayWorkMinutes = holidayWorkTimeEachFrameNo.getHolidayWorkTime().getCalcTime().v()
					- holidayWorkTimeBeforeRound.getHolidayWorkTime().getCalcTime().v();
			if (diffCalcHolidayWorkMinutes < 0) diffCalcHolidayWorkMinutes = 0;
			
			int diffTransMinutes = holidayWorkTimeEachFrameNo.getTransferTime().getTime().v()
					- holidayWorkTimeBeforeRound.getTransferTime().getTime().v();
			if (diffTransMinutes < 0) diffTransMinutes = 0;
			
			int diffCalcTransMinutes = holidayWorkTimeEachFrameNo.getTransferTime().getCalcTime().v()
					- holidayWorkTimeBeforeRound.getTransferTime().getCalcTime().v();
			if (diffCalcTransMinutes < 0) diffCalcTransMinutes = 0;
			
			targetWork.addHolidayWorkTime(new HolidayWorkFrameTime(
					holidayWorkTimeFrameNo,
					Finally.of(TimeDivergenceWithCalculation.createTimeWithCalculation(
							new AttendanceTime(diffHolidayWorkMinutes),
							new AttendanceTime(diffCalcHolidayWorkMinutes))),
					Finally.of(TimeDivergenceWithCalculation.createTimeWithCalculation(
							new AttendanceTime(diffTransMinutes),
							new AttendanceTime(diffCalcTransMinutes))),
					Finally.of(new AttendanceTime(0))));
		}
		
		// フレックス超過時間
		int diffFlexExcessMinutes = this.totalTimeAfterRound.getFlexExcessTime().v()
				- totalTimeBeforeRound.getFlexExcessTime().v();
		if (diffFlexExcessMinutes < 0) diffFlexExcessMinutes = 0;
		this.flexExcessTime.putIfAbsent(assignDate, new FlexTimeOfTimeSeries(assignDate));
		val flexExcessTimeDetail = this.flexExcessTime.get(assignDate);
		flexExcessTimeDetail.addMinutesToFlexTimeInFlexTime(diffFlexExcessMinutes);
		
		// 週割増合計時間
		int diffWeekPremiumMinutes = this.totalTimeAfterRound.getWeeklyTotalPremiumTime().v()
				- totalTimeBeforeRound.getWeeklyTotalPremiumTime().v();
		if (diffWeekPremiumMinutes < 0) diffWeekPremiumMinutes = 0;
		this.weeklyPremiumTime.putIfAbsent(assignDate, new WeeklyPremiumTimeOfTimeSeries(assignDate));
		val weekPremiumTimeDetail = this.weeklyPremiumTime.get(assignDate);
		weekPremiumTimeDetail.addMinutesToWeeklyPremiumTime(diffWeekPremiumMinutes);
		
		// 月割増合計時間
		int diffMonthPremiumMinutes = this.totalTimeAfterRound.getMonthlyTotalPremiumTime().v()
				- totalTimeBeforeRound.getMonthlyTotalPremiumTime().v();
		if (diffMonthPremiumMinutes < 0) diffMonthPremiumMinutes = 0;
		this.monthlyPremiumTime.putIfAbsent(assignDate, new MonthlyPremiumTimeOfTimeSeries(assignDate));
		val monthPremiumTimeDetail = this.monthlyPremiumTime.get(assignDate);
		monthPremiumTimeDetail.addMinutesToMonthlyPremiumTime(diffMonthPremiumMinutes);
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
		
		// フレックス超過時間
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			if (!this.flexExcessTime.containsKey(procDate)) return notExistTime;
			return new AttendanceTimeMonth(this.flexExcessTime.get(procDate).getFlexTime().getFlexTime().getTime().v());
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
