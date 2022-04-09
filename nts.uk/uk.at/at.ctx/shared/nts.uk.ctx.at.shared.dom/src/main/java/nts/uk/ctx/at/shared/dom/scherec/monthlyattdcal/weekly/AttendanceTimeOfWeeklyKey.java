package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.closure.ClosureMonth;

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

	public ClosureMonth closureMonth(){
		return new ClosureMonth(
				this.yearMonth,
				this.closureId.value,
				this.closureDate);
	}
}
