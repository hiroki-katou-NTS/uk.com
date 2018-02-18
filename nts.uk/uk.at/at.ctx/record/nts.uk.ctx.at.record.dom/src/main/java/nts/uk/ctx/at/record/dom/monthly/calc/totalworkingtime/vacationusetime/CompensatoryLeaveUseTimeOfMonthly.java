package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.CompensatoryLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の代休使用時間
 * @author shuichi_ishida
 */
public class CompensatoryLeaveUseTimeOfMonthly {
	
	/** 使用時間 */
	@Getter
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	@Getter
	private List<CompensatoryLeaveUseTimeOfTimeSeries> timeSeriesWorks;

	/** 集計済 */
	private boolean isAggregated;
	
	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfMonthly(){
		
		this.useTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new ArrayList<>();
		this.isAggregated = false;
	}
	
	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		val domain = new CompensatoryLeaveUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	/**
	 * 代休使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void confirm(DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap){

		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.values()) {
			
			// 期間外はスキップする
			if (!datePeriod.contains(attendanceTimeOfDaily.getYmd())) continue;
			
			// 「日別実績の代休」を取得する
			val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			//*****（未）　ここから先のドメインがまだない
			//VacationOfDaily vacationOfDaily = totalWorkingTime.getVacation();
			//CompensatoryLeaveOfDaily compensatoryLeaveOfDaily = vacationOfDaily.getCompensatoryLeave();
			
			// 取得した使用時間を「月別実績の代休使用時間」に入れる
			//*****（未）　「日別実績の代休」クラスをnewして、値を入れて、それをset？
			this.timeSeriesWorks.add(CompensatoryLeaveUseTimeOfTimeSeries.of(attendanceTimeOfDaily.getYmd()));
		}
	}
	
	/**
	 * 代休使用時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		if (this.isAggregated) return;
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			//CompensatoryLeaveOfDaily compensatoryLeaveUseTime = timeSeriesWork.getCompensatoryLeaveUseTime();
			//this.useTime.addMinutes(compensatoryLeaveUseTime.getUseTime().valueAsMinutes());
		}
		this.isAggregated = true;
	}
}
