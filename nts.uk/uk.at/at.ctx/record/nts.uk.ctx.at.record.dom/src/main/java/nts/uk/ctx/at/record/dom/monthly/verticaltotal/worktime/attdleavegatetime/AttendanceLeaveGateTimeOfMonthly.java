package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.attdleavegatetime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の入退門時間
 * @author shuichu_ishida
 */
@Getter
public class AttendanceLeaveGateTimeOfMonthly {

	/** 出勤前時間 */
	private AttendanceTimeMonth timeBeforeAttendance;
	/** 退勤後時間 */
	private AttendanceTimeMonth timeAfterLeaveWork;
	/** 滞在時間 */
	private AttendanceTimeMonth stayingTime;
	/** 不就労時間 */
	private AttendanceTimeMonth unemployedTime;
	
	/**
	 * コンストラクタ
	 */
	public AttendanceLeaveGateTimeOfMonthly(){
		
		this.timeBeforeAttendance = new AttendanceTimeMonth(0);
		this.timeAfterLeaveWork = new AttendanceTimeMonth(0);
		this.stayingTime = new AttendanceTimeMonth(0);
		this.unemployedTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param timeBeforeAttendance 出勤前時間
	 * @param timeAfterLeaveWork 退勤後時間
	 * @param stayingTime 滞在時間
	 * @param unemployedTime 不就労時間
	 * @return 月別実績の入退門時間
	 */
	public static AttendanceLeaveGateTimeOfMonthly of(
			AttendanceTimeMonth timeBeforeAttendance,
			AttendanceTimeMonth timeAfterLeaveWork,
			AttendanceTimeMonth stayingTime,
			AttendanceTimeMonth unemployedTime){
		
		val domain = new AttendanceLeaveGateTimeOfMonthly();
		domain.timeBeforeAttendance = timeBeforeAttendance;
		domain.timeAfterLeaveWork = timeAfterLeaveWork;
		domain.stayingTime = stayingTime;
		domain.unemployedTime = unemployedTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		// 不就労時間を累積
		this.unemployedTime = this.unemployedTime.addMinutes(attendanceTimeOfDaily.getUnEmployedTime().v());

		// 滞在時間、出勤前時間、退勤後時間を累積
		val stayingTime = attendanceTimeOfDaily.getStayingTime();
		this.stayingTime = this.stayingTime.addMinutes(stayingTime.getStayingTime().v());
		this.timeBeforeAttendance = this.timeBeforeAttendance.addMinutes(stayingTime.getBeforeWoringTime().v());
		this.timeAfterLeaveWork = this.timeAfterLeaveWork.addMinutes(stayingTime.getAfterLeaveTime().v());
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AttendanceLeaveGateTimeOfMonthly target){
		
		this.timeBeforeAttendance = this.timeBeforeAttendance.addMinutes(target.timeBeforeAttendance.v());
		this.timeAfterLeaveWork = this.timeAfterLeaveWork.addMinutes(target.timeAfterLeaveWork.v());
		this.stayingTime = this.stayingTime.addMinutes(target.stayingTime.v());
		this.unemployedTime = this.unemployedTime.addMinutes(target.unemployedTime.v());
	}
}
