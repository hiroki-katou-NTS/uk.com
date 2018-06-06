package nts.uk.ctx.at.record.dom.weekly;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * キー値：週別実績の勤怠時間
 * @author shuichu_ishida
 */
@Value
public class AttendanceTimeOfWeeklyKey {
	/** 社員ID */
	String employeeId;
	/** 年月 */
	YearMonth yearMonth;
	/** 締めID */
	ClosureId closureId;
	/** 締め日付 */
	ClosureDate closureDate;
	/** 週NO */
	int weekNo;
}
