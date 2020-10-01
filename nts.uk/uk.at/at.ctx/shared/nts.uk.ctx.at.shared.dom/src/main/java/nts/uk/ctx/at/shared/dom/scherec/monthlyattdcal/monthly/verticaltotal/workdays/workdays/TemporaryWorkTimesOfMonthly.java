package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の臨時勤務回数
 * @author shuichi_ishida
 */
@Getter
public class TemporaryWorkTimesOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 回数 */
	private AttendanceTimesMonth times;

	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 */
	public TemporaryWorkTimesOfMonthly() {
		
		this.times = new AttendanceTimesMonth(0);
		this.time = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param times 回数
	 * @return 月別実績の臨時勤務回数
	 */
	public static TemporaryWorkTimesOfMonthly of(
			AttendanceTimesMonth times, AttendanceTimeMonth time){

		val domain = new TemporaryWorkTimesOfMonthly();
		domain.times = times;
		domain.time = time;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime) {

		if (attendanceTime == null) return;

		val temporatyDaily = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTemporaryTime();		
		// 勤務回数を計算
		this.times = this.times.addTimes(temporatyDaily.getTemporaryTime().size());
		//　勤務時間を計算する
		int tempTime = temporatyDaily.getTemporaryTime().stream().mapToInt(t -> t.getTemporaryTime().valueAsMinutes()).sum();
		this.time = this.time.addMinutes(tempTime);
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(TemporaryWorkTimesOfMonthly target){
		
		this.times = this.times.addTimes(target.times.v());
		this.time = this.time.addMinutes(target.time.valueAsMinutes());
	}
}
