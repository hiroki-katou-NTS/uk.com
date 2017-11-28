package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.AnnualLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の年休使用時間
 * @author shuichi_ishida
 */
@Getter
public class AnnualLeaveUseTimeOfMonthly {
	
	/** 使用時間 */
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	private List<AnnualLeaveUseTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUseTimeOfMonthly(){
	
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の年休使用時間
	 */
	public static AnnualLeaveUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		AnnualLeaveUseTimeOfMonthly domain = new AnnualLeaveUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	/**
	 * 年休使用時間を確認する
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
			
			// 「日別実績の年休」を取得する
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
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (AnnualLeaveUseTimeOfTimeSeries timeSeriesWork : this.timeSeriesWorks){
			//AnnualLeaveOfDaily annualLeaveUseTime = timeSeriesWork.getAnnualLeaveUseTime();
			//this.useTime.addMinutes(annualLeaveUseTime.getUseTime().valueAsMinutes());
		}
	}
}
