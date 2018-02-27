package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.FlexTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * フレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTime {

	/** フレックス時間 */
	@Setter
	private TimeMonthWithCalculationAndMinus flexTime;
	/** 事前フレックス時間 */
	@Setter
	private AttendanceTimeMonth beforeFlexTime;
	/** 法定内フレックス時間 */
	@Setter
	private AttendanceTimeMonthWithMinus legalFlexTime;
	/** 法定外フレックス時間 */
	@Setter
	private AttendanceTimeMonthWithMinus illegalFlexTime;
	
	/** 時系列ワーク */
	private Map<GeneralDate, FlexTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public FlexTime(){
		
		this.flexTime = TimeMonthWithCalculationAndMinus.ofSameTime(0);
		this.beforeFlexTime = new AttendanceTimeMonth(0);
		this.legalFlexTime = new AttendanceTimeMonthWithMinus(0);
		this.illegalFlexTime = new AttendanceTimeMonthWithMinus(0);
		this.timeSeriesWorks = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param beforeFlexTime 事前フレックス時間
	 * @param legalFlexTime 法定内フレックス時間
	 * @param illegalFlexTime 法定外フレックス時間
	 * @return フレックス時間
	 */
	public static FlexTime of(
			TimeMonthWithCalculationAndMinus flexTime,
			AttendanceTimeMonth beforeFlexTime,
			AttendanceTimeMonthWithMinus legalFlexTime,
			AttendanceTimeMonthWithMinus illegalFlexTime){

		val domain = new FlexTime();
		domain.flexTime = flexTime;
		domain.beforeFlexTime = beforeFlexTime;
		domain.legalFlexTime = legalFlexTime;
		domain.illegalFlexTime = illegalFlexTime;
		return domain;
	}
	
	/**
	 * フレックス時間（時系列ワーク）に加算する
	 * @param ymd 年月日
	 * @param timeAsMinutes フレックス時間（分）
	 * @param calcTimeAsMinutes 計算フレックス時間（分）
	 * @param beforeApplicationTimeAsMinutes 事前申請時間（分）
	 */
	private void addFlexTimeInTimeSeriesWork(GeneralDate ymd,
			Integer timeAsMinutes, Integer calcTimeAsMinutes, Integer beforeApplicationTimeAsMinutes){
		
		this.timeSeriesWorks.putIfAbsent(ymd, new FlexTimeOfTimeSeries(ymd));
		val targetFlexTime = this.timeSeriesWorks.get(ymd);
		val flexTimeSrc = targetFlexTime.getFlexTime();
		targetFlexTime.setFlexTime(
				new nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime(
						TimeWithCalculationMinusExist.createTimeWithCalculation(
								flexTimeSrc.getFlexTime().getTime().addMinutes(timeAsMinutes),
								flexTimeSrc.getFlexTime().getCalcTime().addMinutes(calcTimeAsMinutes)),
						flexTimeSrc.getBeforeApplicationTime().addMinutes(beforeApplicationTimeAsMinutes)
				));
	}
	
	/**
	 * 集計する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		// 「フレックス時間」を取得する
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessPrescribedTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val overTimeOfDaily = excessPrescribedTimeOfDaily.getOverTimeWork();
		// 残業時間がない時、集計しない
		if (!overTimeOfDaily.isPresent()) return;
		val ymd = attendanceTimeOfDaily.getYmd();
		val flexTimeSrc = overTimeOfDaily.get().getFlexTime();
		
		// 取得したフレックス時間を「フレックス時間」に加算する
		this.addFlexTimeInTimeSeriesWork(ymd, flexTimeSrc.getFlexTime().getTime().v(),
				flexTimeSrc.getFlexTime().getCalcTime().v(),
				flexTimeSrc.getBeforeApplicationTime().v());
	}
	
	/**
	 * 残業枠時間をフレックス時間に入れる
	 * @param ymd 年月日
	 * @param overTimeFrameTime 残業枠時間
	 */
	public void addOverTimeFrameTime(GeneralDate ymd, OverTimeFrameTime overTimeFrameTime){
		
		this.addFlexTimeInTimeSeriesWork(ymd, overTimeFrameTime.getOverTimeWork().getTime().v(),
				overTimeFrameTime.getOverTimeWork().getCalcTime().v(),
				overTimeFrameTime.getBeforeApplicationTime().v());
	}
	
	/**
	 * 時系列合計フレックス時間を取得する
	 * @param datePeriod 期間
	 * @param exceptMinus マイナス値を除く
	 * @return 時系列合計フレックス時間
	 */
	public AttendanceTimeMonthWithMinus getTimeSeriesTotalFlexTime(DatePeriod datePeriod, boolean exceptMinus){
		
		AttendanceTimeMonthWithMinus returnTime = new AttendanceTimeMonthWithMinus(0);
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val flexTime = timeSeriesWork.getFlexTime().getFlexTime().getTime();
			if (exceptMinus && flexTime.lessThanOrEqualTo(0)) continue;
			returnTime = returnTime.addMinutes(flexTime.v());
		}
		return returnTime;
	}
}
