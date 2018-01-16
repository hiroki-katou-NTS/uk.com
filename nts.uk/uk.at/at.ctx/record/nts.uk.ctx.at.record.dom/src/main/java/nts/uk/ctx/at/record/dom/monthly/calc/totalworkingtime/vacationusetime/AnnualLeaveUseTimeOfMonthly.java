package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の年休使用時間
 * @author shuichi_ishida
 */
public class AnnualLeaveUseTimeOfMonthly {
	
	/** 使用時間 */
	@Getter
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	@Getter
	private List<AnnualLeaveUseTimeOfTimeSeries> timeSeriesWorks;

	/** 集計済 */
	private boolean isAggregated;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUseTimeOfMonthly(){
	
		this.useTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new ArrayList<>();
		this.isAggregated = false;
	}

	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の年休使用時間
	 */
	public static AnnualLeaveUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		val domain = new AnnualLeaveUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	/**
	 * 年休使用時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){

		for (val attendanceTimeOfDaily : attendanceTimeOfDailys) {
			
			// 「日別実績の年休」を取得する
			val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			//*****（未）　ここから先のドメインがまだない
			//VacationOfDaily vacationOfDaily = totalWorkingTime.getVacation();
			//annualLeaveOfDaily annualLeaveOfDaily = vacationOfDaily.getAnnualLeave();
			
			// 取得した使用時間を「月別実績の年休使用時間」に入れる
			//*****（未）　「日別実績の年休」クラスをnewして、値を入れて、それをset？
			this.timeSeriesWorks.add(AnnualLeaveUseTimeOfTimeSeries.of(attendanceTimeOfDaily.getYmd()));
		}
	}
	
	/**
	 * 年休使用時間を集計する
	 */
	public void aggregate(){
		
		if (this.isAggregated) return;
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks){
			//AnnualLeaveOfDaily annualLeaveUseTime = timeSeriesWork.getAnnualLeaveUseTime();
			//this.useTime.addMinutes(annualLeaveUseTime.getUseTime().valueAsMinutes());
		}
		this.isAggregated = true;
	}
}
