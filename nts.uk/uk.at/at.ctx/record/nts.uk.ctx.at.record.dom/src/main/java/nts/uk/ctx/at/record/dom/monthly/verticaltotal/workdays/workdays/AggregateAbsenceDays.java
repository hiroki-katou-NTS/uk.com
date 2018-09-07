package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計欠勤日数
 * 
 * @author shuichu_ishida
 */
@Getter
public class AggregateAbsenceDays {

	/** 欠勤枠NO */
	private int absenceFrameNo;
	/** 日数 */
	private AttendanceDaysMonth days;
	/** 時間 */
	private AttendanceTimeMonth time;

	/**
	 * コンストラクタ
	 * 
	 * @param absenceFrameNo
	 *            欠勤枠NO
	 */
	public AggregateAbsenceDays(int absenceFrameNo) {

		this.absenceFrameNo = absenceFrameNo;
		this.days = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}

	/**
	 * @author lanlt
	 */
	public AggregateAbsenceDays() {
		this.days = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * 
	 * @param absenceFrameNo
	 *            欠勤枠NO
	 * @param days
	 *            日数
	 * @param time
	 *            時間
	 * @return 集計欠勤日数
	 */
	public static AggregateAbsenceDays of(
			int absenceFrameNo,
			AttendanceDaysMonth days,
			AttendanceTimeMonth time){
		
		val domain = new AggregateAbsenceDays(absenceFrameNo);
		domain.days = days;
		domain.time = time;
		return domain;
	}

	/**
	 * @author lanlt
	 * @param days
	 * @param time
	 * @return
	 */
	public static AggregateAbsenceDays of(AttendanceDaysMonth days, AttendanceTimeMonth time) {

		val domain = new AggregateAbsenceDays();
		domain.days = days;
		domain.time = time;
		return domain;
	}

	/**
	 * 日数を加算する
	 * 
	 * @param days
	 *            日数
	 */
	public void addDays(Double days) {
		this.days = this.days.addDays(days);
	}

	/**
	 * 時間に分を加算する
	 * 
	 * @param minutes
	 *            分
	 */
	public void addTime(int minutes) {
		this.time = this.time.addMinutes(minutes);
	}
}
