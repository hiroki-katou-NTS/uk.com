package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.PrescribedWorkingTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の所定労働時間
 * @author shuichi_ishida
 */
@Getter
public class PrescribedWorkingTimeOfMonthly {

	/** 計画所定労働時間 */
	private AttendanceTimeMonth schedulePrescribedWorkingTime;
	/** 実績所定労働時間 */
	private AttendanceTimeMonth recordPrescribedWorkingTime;
	/** 時系列ワーク */
	private List<PrescribedWorkingTimeOfTimeSeries> timeSeriesWorks;

	/**
	 * コンストラクタ
	 */
	public PrescribedWorkingTimeOfMonthly(){
		
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param schedulePrescribedWorkingTime 計画所定労働時間
	 * @param recordPrescribedWorkingTime 実績所定労働時間
	 * @return 月別実績の所定労働時間
	 */
	public static PrescribedWorkingTimeOfMonthly of(
			AttendanceTimeMonth schedulePrescribedWorkingTime,
			AttendanceTimeMonth recordPrescribedWorkingTime){
		
		PrescribedWorkingTimeOfMonthly domain = new PrescribedWorkingTimeOfMonthly();
		domain.schedulePrescribedWorkingTime = schedulePrescribedWorkingTime;
		domain.recordPrescribedWorkingTime = recordPrescribedWorkingTime;
		return domain;
	}
	
	/**
	 * 所定労働時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		for (AttendanceTimeOfDailyPerformance attendanceTimeOfDaily : attendanceTimeOfDailys){
		
			// 「日別実績の勤務予定時間」を取得する
			WorkScheduleTimeOfDaily workScheduleTimeOfDaily = attendanceTimeOfDaily.getWorkScheduleTimeOfDaily();
			
			// 取得した就業時間を「月別実績の所定労働時間」に入れる
			//*****（未）上の取得値を、同じクラスをあらたにインスタンス化したものに入れなおしてaddに渡す。下のような渡し方はダメ（なはず）
			this.timeSeriesWorks.add(PrescribedWorkingTimeOfTimeSeries.of(
					attendanceTimeOfDaily.getYmd(), workScheduleTimeOfDaily));
		}
	}
	
	/**
	 * 所定労働時間を集計する
	 */
	public void aggregate(){
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		
		for (PrescribedWorkingTimeOfTimeSeries timeSeriesWork : this.timeSeriesWorks){
			WorkScheduleTimeOfDaily prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			//*****（未）参照先クラスで、コンパイルエラーが出ているため、コメントアウト中
			//this.schedulePrescribedWorkingTime.addMinutes(prescribedWorkingTime.getSchedulePrescribedLaborTime().valueAsMinutes());
			//this.recordPrescribedWorkingTime.addMinutes(prescribedWorkingTime.getRecordPrescribedLaborTime().valueAsMinutes());
		}
	}
}
