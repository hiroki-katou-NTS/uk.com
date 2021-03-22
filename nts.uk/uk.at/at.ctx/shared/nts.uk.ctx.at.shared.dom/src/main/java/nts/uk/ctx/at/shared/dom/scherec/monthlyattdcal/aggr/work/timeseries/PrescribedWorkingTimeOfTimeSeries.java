package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;

/**
 * 時系列の所定労働時間
 * @author shuichi_ishida
 */
@Getter
public class PrescribedWorkingTimeOfTimeSeries implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 所定労働時間 */
	private WorkScheduleTimeOfDaily prescribedWorkingTime;
	
	/** 計画所定労働時間 */
	private AttendanceTime schedulePrescribedLaborTime;
	
	/**
	 * コンストラクタ
	 */
	public PrescribedWorkingTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.prescribedWorkingTime = new WorkScheduleTimeOfDaily(
				new WorkScheduleTime(
						new AttendanceTime(0),
						new AttendanceTime(0),
						new AttendanceTime(0)),
				new AttendanceTime(0));
		this.schedulePrescribedLaborTime = new AttendanceTime(0);
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param prescribedWorkingTime 日別実績の勤務予定時間
	 * @param schedulePrescribedLaborTime 計画所定労働時間
	 * @return 時系列の所定労働時間
	 */
	public static PrescribedWorkingTimeOfTimeSeries of(
			GeneralDate ymd, WorkScheduleTimeOfDaily prescribedWorkingTime,
			AttendanceTime schedulePrescribedLaborTime){
		
		val domain = new PrescribedWorkingTimeOfTimeSeries(ymd);
		if (prescribedWorkingTime != null){
			
			WorkScheduleTime workScheduleTime = new WorkScheduleTime(
							new AttendanceTime(0),
							new AttendanceTime(0),
							new AttendanceTime(0));
			val srcWorkScheduleTime = prescribedWorkingTime.getWorkScheduleTime();
			if (srcWorkScheduleTime != null){
				workScheduleTime = new WorkScheduleTime(
						(srcWorkScheduleTime.getTotal() != null ? srcWorkScheduleTime.getTotal() : new AttendanceTime(0)),
						(srcWorkScheduleTime.getExcessOfStatutoryTime() != null ? srcWorkScheduleTime.getExcessOfStatutoryTime() : new AttendanceTime(0)),
						(srcWorkScheduleTime.getWithinStatutoryTime() != null ? srcWorkScheduleTime.getWithinStatutoryTime() : new AttendanceTime(0)));
			}
			
			domain.prescribedWorkingTime = new WorkScheduleTimeOfDaily(
					workScheduleTime,
					(prescribedWorkingTime.getRecordPrescribedLaborTime() != null ? prescribedWorkingTime.getRecordPrescribedLaborTime() : new AttendanceTime(0)));
		}
		domain.schedulePrescribedLaborTime = schedulePrescribedLaborTime;
		return domain;
	}
}
