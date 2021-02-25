package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.breaktime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の休憩時間
 * @author shuichi_ishida
 */
@Getter
public class BreakTimeOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 休憩回数 */
	private AttendanceTimesMonth breakTimes;
	/** 休憩時間 */
	private AttendanceTimeMonth breakTime;
	/** 所定内休憩時間 */
	private AttendanceTimeMonth withinTime;
	/** 所定内控除時間 */
	private AttendanceTimeMonth withinDeductionTime;
	/** 所定外休憩時間 */
	private AttendanceTimeMonth excessTime;
	/** 所定外控除時間 */
	private AttendanceTimeMonth excessDeductionTime;
	
	/**
	 * コンストラクタ
	 */
	public BreakTimeOfMonthly(){
		
		this.breakTimes = new AttendanceTimesMonth(0);
		this.breakTime = new AttendanceTimeMonth(0);
		this.withinTime = new AttendanceTimeMonth(0);
		this.withinDeductionTime = new AttendanceTimeMonth(0);
		this.excessTime = new AttendanceTimeMonth(0);
		this.excessDeductionTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param breakTimes 休憩回数
	 * @param breakTime 休憩時間
	 * @param withinTime 所定内休憩時間
	 * @param withinDeductionTime 所定内控除時間
	 * @param excessTime 所定外休憩時間
	 * @param excessDeductionTime 所定外控除時間
	 * @return 月別実績の休憩時間
	 */
	public static BreakTimeOfMonthly of(
			AttendanceTimesMonth breakTimes, AttendanceTimeMonth breakTime,
			AttendanceTimeMonth withinTime, AttendanceTimeMonth withinDeductionTime,
			AttendanceTimeMonth excessTime, AttendanceTimeMonth excessDeductionTime){

		val domain = new BreakTimeOfMonthly();
		domain.breakTimes = breakTimes;
		domain.breakTime = breakTime;
		domain.withinTime = withinTime;
		domain.withinDeductionTime = withinDeductionTime;
		domain.excessTime = excessTime;
		domain.excessDeductionTime = excessDeductionTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
		val breakTimeOfDaily = totalWorkingTime.getBreakTimeOfDaily();
		
		this.breakTime = this.breakTime.addMinutes(
				breakTimeOfDaily.getToRecordTotalTime().getTotalTime().getTime().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(BreakTimeOfMonthly target){
		
		this.breakTimes = this.breakTimes.addTimes(target.breakTimes.v());
		this.breakTime = this.breakTime.addMinutes(target.breakTime.v());
		this.withinTime = this.withinTime.addMinutes(target.withinTime.v());
		this.withinDeductionTime = this.withinDeductionTime.addMinutes(target.withinDeductionTime.v());
		this.excessTime = this.excessTime.addMinutes(target.excessTime.v());
		this.excessDeductionTime = this.excessDeductionTime.addMinutes(target.excessDeductionTime.v());
	}
}
