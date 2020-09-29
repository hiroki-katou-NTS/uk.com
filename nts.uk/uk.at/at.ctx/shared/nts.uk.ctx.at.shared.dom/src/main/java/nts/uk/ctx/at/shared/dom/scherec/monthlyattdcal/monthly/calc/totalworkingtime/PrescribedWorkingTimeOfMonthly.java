package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.PrescribedWorkingTimeOfTimeSeries;

/**
 * 月別実績の所定労働時間
 * @author shuichi_ishida
 */
@Getter
public class PrescribedWorkingTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

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
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
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
		
		val domain = new PrescribedWorkingTimeOfMonthly();
		domain.schedulePrescribedWorkingTime = schedulePrescribedWorkingTime;
		domain.recordPrescribedWorkingTime = recordPrescribedWorkingTime;
		return domain;
	}
	
	@Override
	public PrescribedWorkingTimeOfMonthly clone() {
		PrescribedWorkingTimeOfMonthly cloned = new PrescribedWorkingTimeOfMonthly();
		try {
			cloned.schedulePrescribedWorkingTime = new AttendanceTimeMonth(this.schedulePrescribedWorkingTime.v());
			cloned.recordPrescribedWorkingTime = new AttendanceTimeMonth(this.recordPrescribedWorkingTime.v());
			// ※　Shallow Copy.
			cloned.timeSeriesWorks = timeSeriesWorks;
		}
		catch (Exception e){
			throw new RuntimeException("PrescribedWorkingTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 所定労働時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void confirm(DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap){
		
		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.entrySet()){

			// 期間外はスキップする
			if (!datePeriod.contains(attendanceTimeOfDaily.getKey())) continue;
			
			// 「日別実績の勤務予定時間」を取得する
			val workScheduleTimeOfDaily = attendanceTimeOfDaily.getValue().getWorkScheduleTimeOfDaily();
			
			// 取得した就業時間を「月別実績の所定労働時間」に入れる
			this.timeSeriesWorks.add(PrescribedWorkingTimeOfTimeSeries.of(
					attendanceTimeOfDaily.getKey(), workScheduleTimeOfDaily));
		}
	}
	
	/**
	 * 所定労働時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			this.schedulePrescribedWorkingTime = this.schedulePrescribedWorkingTime.addMinutes(
					prescribedWorkingTime.getSchedulePrescribedLaborTime().v());
			this.recordPrescribedWorkingTime = this.recordPrescribedWorkingTime.addMinutes(
					prescribedWorkingTime.getRecordPrescribedLaborTime().v());
		}
	}

	/**
	 * 計画所定労働合計時間を取得する
	 * @param datePeriod 期間
	 * @return 計画所定労働合計時間
	 */
	public AttendanceTimeMonth getTotalSchedulePrescribedWorkingTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			returnTime = returnTime.addMinutes(prescribedWorkingTime.getSchedulePrescribedLaborTime().v());
		}
		return returnTime;
	}

	/**
	 * 実績所定労働合計時間を取得する
	 * @param datePeriod 期間
	 * @return 実績所定労働合計時間
	 */
	public AttendanceTimeMonth getTotalRecordPrescribedWorkingTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			returnTime = returnTime.addMinutes(prescribedWorkingTime.getRecordPrescribedLaborTime().v());
		}
		return returnTime;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PrescribedWorkingTimeOfMonthly target){
		
		this.schedulePrescribedWorkingTime = this.schedulePrescribedWorkingTime.addMinutes(
				target.schedulePrescribedWorkingTime.v());
		this.recordPrescribedWorkingTime = this.recordPrescribedWorkingTime.addMinutes(
				target.recordPrescribedWorkingTime.v());
	}
}
