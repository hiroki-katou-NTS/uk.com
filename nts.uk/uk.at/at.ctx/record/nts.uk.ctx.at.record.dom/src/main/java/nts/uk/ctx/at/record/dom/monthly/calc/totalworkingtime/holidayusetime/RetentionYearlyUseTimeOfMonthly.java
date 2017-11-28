package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.RetentionYearlyUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の積立年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class RetentionYearlyUseTimeOfMonthly {
	
	/** 使用時間 */
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	private List<RetentionYearlyUseTimeOfTimeSeries> timeSeriesWorks;

	/**
	 * コンストラクタ
	 */
	public RetentionYearlyUseTimeOfMonthly(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の積立年休使用時間
	 */
	public static RetentionYearlyUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		RetentionYearlyUseTimeOfMonthly domain = new RetentionYearlyUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	/**
	 * 積立年休使用時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){

		for (AttendanceTimeOfDailyPerformance attendanceTimeOfDaily : attendanceTimeOfDailys) {
			
			// 日別実績の勤務実績時間　取得
			ActualWorkingTimeOfDaily actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
	
			// 日別実績の総労働時間　取得
			TotalWorkingTime totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
	
			// 日別実績の休暇　取得
			//*****（未）　ここから先のドメインがまだない
			//VacationOfDaily vacationOfDaily = totalWorkingTime.getVacation();
			
			// 「日別実績の積立年休」を取得する
			//RetentionYearlyOfDaily retentionYearlyOfDaily = vacationOfDaily.getRetentionYearly();
			
			// 取得した使用時間を「月別実績の積立年休使用時間」に入れる
			//*****（未）　「日別実績の積立年休」クラスをnewして、値を入れて、それをset？
			this.timeSeriesWorks.add(RetentionYearlyUseTimeOfTimeSeries.of(attendanceTimeOfDaily.getYmd()));
		}
	}
	
	/**
	 * 積立年休使用時間を集計する
	 */
	public void aggregate(){
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (RetentionYearlyUseTimeOfTimeSeries timeSeriesWork : this.timeSeriesWorks){
			//RetentionYearlyOfDaily retentionYearly = timeSeriesWork.getRetentionYearly();
			//this.useTime.addMinutes(retentionYearly.getUseTime().valueAsMinutes());
		}
	}
}
