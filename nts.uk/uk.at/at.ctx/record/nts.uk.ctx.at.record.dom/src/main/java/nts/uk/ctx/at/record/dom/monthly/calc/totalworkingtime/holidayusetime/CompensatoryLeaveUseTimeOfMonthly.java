package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.CompensatoryLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の代休使用時間
 * @author shuichi_ishida
 */
@Getter
public class CompensatoryLeaveUseTimeOfMonthly {
	
	/** 使用時間 */
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	private List<CompensatoryLeaveUseTimeOfTimeSeries> timeSeriesWorks;

	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfMonthly(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		CompensatoryLeaveUseTimeOfMonthly domain = new CompensatoryLeaveUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	/**
	 * 代休使用時間を確認する
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
			
			// 「日別実績の代休」を取得する
			//CompensatoryLeaveOfDaily compensatoryLeaveOfDaily = vacationOfDaily.getCompensatoryLeave();
			
			// 取得した使用時間を「月別実績の代休使用時間」に入れる
			//*****（未）　「日別実績の代休」クラスをnewして、値を入れて、それをset？
			this.timeSeriesWorks.add(CompensatoryLeaveUseTimeOfTimeSeries.of(attendanceTimeOfDaily.getYmd()));
		}
	}
	
	/**
	 * 代休使用時間を集計する
	 */
	public void aggregate(){
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (CompensatoryLeaveUseTimeOfTimeSeries timeSeriesWork : this.timeSeriesWorks){
			//CompensatoryLeaveOfDaily compensatoryLeaveUseTime = timeSeriesWork.getCompensatoryLeaveUseTime();
			//this.useTime.addMinutes(compensatoryLeaveUseTime.getUseTime().valueAsMinutes());
		}
	}
}
