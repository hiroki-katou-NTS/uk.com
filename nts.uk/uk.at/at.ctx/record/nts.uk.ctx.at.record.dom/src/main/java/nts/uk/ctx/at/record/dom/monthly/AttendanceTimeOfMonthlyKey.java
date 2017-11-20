package nts.uk.ctx.at.record.dom.monthly;

import lombok.Value;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * キー値：月別実績の勤怠時間
 * @author shuichu_ishida
 */
@Value
public class AttendanceTimeOfMonthlyKey {
	/** 社員ID */
	String employeeID;
	/** 期間 */
	DatePeriod datePeriod;
}
