package nts.uk.ctx.at.record.dom.monthly;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * キー値：月別実績の勤怠時間
 * @author shuichu_ishida
 */
@Value
public class AttendanceTimeOfMonthlyKey {
	/** 社員ID */
	String employeeId;
	/** 年月 */
	YearMonth yearMonth;
	/** 締めID */
	ClosureId closureId;
	/** 締め日付 */
	ClosureDate closureDate;
}
