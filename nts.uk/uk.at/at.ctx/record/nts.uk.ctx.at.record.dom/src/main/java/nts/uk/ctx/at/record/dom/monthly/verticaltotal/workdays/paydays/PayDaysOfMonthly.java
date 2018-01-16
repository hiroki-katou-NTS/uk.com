package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 月別実績の給与用日数
 * @author shuichu_ishida
 */
@Getter
public class PayDaysOfMonthly {

	/** 給与出勤日数 */
	private AttendanceDaysMonth payAttendanceDays;
	/** 給与欠勤日数 */
	private AttendanceDaysMonth payAbsenceDays;
	
	/**
	 * コンストラクタ
	 */
	public PayDaysOfMonthly(){
		
		this.payAttendanceDays = new AttendanceDaysMonth(0.0);
		this.payAbsenceDays = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param payAttendanceDays 給与出勤日数
	 * @param payAbsenceDays　給与欠勤日数
	 * @return 月別実績の給与用日数
	 */
	public static PayDaysOfMonthly of(
			AttendanceDaysMonth payAttendanceDays,
			AttendanceDaysMonth payAbsenceDays){
		
		val domain = new PayDaysOfMonthly();
		domain.payAttendanceDays = payAttendanceDays;
		domain.payAbsenceDays = payAbsenceDays;
		return domain;
	}
}
