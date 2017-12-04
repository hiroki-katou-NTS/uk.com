package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.WorkTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の就業時間
 * @author shuichi_ishida
 */
@Getter
public class WorkTimeOfMonthly {

	/** 就業時間 */
	private AttendanceTimeMonth workTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth withinPrescribedPremiumTime;
	/** リスト：時系列ワーク */
	private List<WorkTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public WorkTimeOfMonthly(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param workTime 就業時間
	 * @param withinPrescribedPremiumTime 所定内割増時間
	 * @return 月別実績の就業時間
	 */
	public static WorkTimeOfMonthly of(
			AttendanceTimeMonth workTime,
			AttendanceTimeMonth withinPrescribedPremiumTime){
		
		WorkTimeOfMonthly domain = new WorkTimeOfMonthly();
		domain.workTime = workTime;
		domain.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		return domain;
	}
	
	/**
	 * 就業時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		for (AttendanceTimeOfDailyPerformance attendanceTimeOfDaily : attendanceTimeOfDailys) {
			
			// 日別実績の勤務実績時間　取得
			ActualWorkingTimeOfDaily actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
	
			// 日別実績の総労働時間　取得
			TotalWorkingTime totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			
			// ドメインモデル「日別実績の法定内時間」を取得する
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = totalWorkingTime.getWithinStatutoryTimeOfDaily();
	
			// 取得した就業時間・所定内割増時間を確認する
			AttendanceTime workTime = new AttendanceTime(withinStatutoryTimeOfDaily.getWorkTime().v());
			AttendanceTime withinPrescribedPremiumTime =
					new AttendanceTime(withinStatutoryTimeOfDaily.getWithinPrescribedPremiumTime().v());
			
			// ドメインモデル「日別実績の残業時間」を取得する
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
			if (excessOfStatutoryTimeOfDaily.getOverTimeWork().isPresent()){
				OverTimeOfDaily overTimeWorkOfDaily = excessOfStatutoryTimeOfDaily.getOverTimeWork().get();
				
				// 変形法定内残業を就業時間に加算
				workTime.addMinutes(overTimeWorkOfDaily.getIrregularWithinPrescribedOverTimeWork().valueAsMinutes());
			}
	
			// 時系列ワークに追加
			//*****（未）WithinStatutoryTimeOfDailyがインスタンス化できない（or 値を入れる）
			//this.timeSeriesWorks.add(WorkTimeOfTimeSeries.of(
			//		attendanceTime.getYmd(),
			//		new WithinStatutoryTimeOfDaily()));
		}
	}
	
	/**
	 * 就業時間を集計する
	 */
	public void aggregate(){
		
		this.workTime = new AttendanceTimeMonth(0);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(0);
		
		for (WorkTimeOfTimeSeries timeSeriesWork : this.timeSeriesWorks){
			WithinStatutoryTimeOfDaily withinStatutoryTime = timeSeriesWork.getWithinStatutoryTimeOfDaily();
			this.workTime.addMinutes(withinStatutoryTime.getWorkTime().valueAsMinutes());
			this.withinPrescribedPremiumTime.addMinutes(withinStatutoryTime.getWithinPrescribedPremiumTime().valueAsMinutes());
		}
	}
}
