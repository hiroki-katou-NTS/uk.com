package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の遅刻早退
 * @author shuichu_ishida
 */
@Getter
public class LateLeaveEarlyOfMonthly {

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
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(
			DatePeriod datePeriod,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		// 早退を集計
		this.leaveEarly.aggregate(datePeriod, attendanceTimeOfDailys);
		
		// 遅刻を集計
		this.late.aggregate(datePeriod, attendanceTimeOfDailys);
	}
}
