package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の遅刻早退
 * @author shuichi_ishida
 */
@Getter
public class LateLeaveEarlyOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 早退 */
	private LeaveEarly leaveEarly;
	/** 遅刻 */
	private Late late;
	
	/**
	 * コンストラクタ
	 */
	public LateLeaveEarlyOfMonthly(){
		
		this.leaveEarly = new LeaveEarly();
		this.late = new Late();
	}
	
	/**
	 * ファクトリー
	 * @param leaveEarly 早退
	 * @param late 遅刻
	 * @return 月別実績の遅刻早退
	 */
	public static LateLeaveEarlyOfMonthly of(LeaveEarly leaveEarly, Late late){
		
		val domain = new LateLeaveEarlyOfMonthly();
		domain.leaveEarly = leaveEarly;
		domain.late = late;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){
		
		// 早退を集計
		this.leaveEarly.aggregate(attendanceTimeOfDaily);
		
		// 遅刻を集計
		this.late.aggregate(attendanceTimeOfDaily);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(LateLeaveEarlyOfMonthly target){
		
		this.leaveEarly.addTimes(target.leaveEarly.getTimes().v());
		this.leaveEarly.addMinutesToTime(
				target.leaveEarly.getTime().getTime().v(), target.leaveEarly.getTime().getCalcTime().v());
		this.late.addTimes(target.late.getTimes().v());
		this.late.addMinutesToTime(
				target.late.getTime().getTime().v(), target.late.getTime().getCalcTime().v());
	}
}
