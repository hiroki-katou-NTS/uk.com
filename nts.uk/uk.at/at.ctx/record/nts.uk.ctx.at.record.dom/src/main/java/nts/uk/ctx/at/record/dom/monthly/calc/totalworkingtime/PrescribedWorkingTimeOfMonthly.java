package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries.PrescribedWorkingTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の所定労働時間
 * @author shuichi_ishida
 */
public class PrescribedWorkingTimeOfMonthly {

	/** 計画所定労働時間 */
	@Getter
	private AttendanceTimeMonth schedulePrescribedWorkingTime;
	/** 実績所定労働時間 */
	@Getter
	private AttendanceTimeMonth recordPrescribedWorkingTime;
	
	/** 時系列ワーク */
	@Getter
	private List<PrescribedWorkingTimeOfTimeSeries> timeSeriesWorks;

	/** 集計済 */
	private boolean isAggregated;
	
	/**
	 * コンストラクタ
	 */
	public PrescribedWorkingTimeOfMonthly(){
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new ArrayList<>();
		this.isAggregated = false;
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
		
		val domain = new PrescribedWorkingTimeOfMonthly();
		domain.schedulePrescribedWorkingTime = schedulePrescribedWorkingTime;
		domain.recordPrescribedWorkingTime = recordPrescribedWorkingTime;
		return domain;
	}
	
	/**
	 * 所定労働時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
		
			// 「日別実績の勤務予定時間」を取得する
			val workScheduleTimeOfDaily = attendanceTimeOfDaily.getWorkScheduleTimeOfDaily();
			
			// 取得した就業時間を「月別実績の所定労働時間」に入れる
			this.timeSeriesWorks.add(PrescribedWorkingTimeOfTimeSeries.of(
					attendanceTimeOfDaily.getYmd(),
					new WorkScheduleTimeOfDaily(
							new WorkScheduleTime(
									new AttendanceTime(workScheduleTimeOfDaily.getWorkScheduleTime().getTotal().v()),
									new AttendanceTime(workScheduleTimeOfDaily.getWorkScheduleTime().getExcessOfStatutoryTime().v()),
									new AttendanceTime(workScheduleTimeOfDaily.getWorkScheduleTime().getWithinStatutoryTime().v())),
							new AttendanceTime(workScheduleTimeOfDaily.getSchedulePrescribedLaborTime().v()),
							new AttendanceTime(workScheduleTimeOfDaily.getRecordPrescribedLaborTime().v()))
					));
		}
	}
	
	/**
	 * 所定労働時間を集計する
	 */
	public void aggregate(){
		
		if (this.isAggregated) return;
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			this.schedulePrescribedWorkingTime.addMinutes(prescribedWorkingTime.getSchedulePrescribedLaborTime().valueAsMinutes());
			this.recordPrescribedWorkingTime.addMinutes(prescribedWorkingTime.getRecordPrescribedLaborTime().valueAsMinutes());
		}
		this.isAggregated = true;
	}
}
