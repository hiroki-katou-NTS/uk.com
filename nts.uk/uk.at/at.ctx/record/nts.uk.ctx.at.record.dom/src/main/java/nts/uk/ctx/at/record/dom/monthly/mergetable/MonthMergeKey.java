package nts.uk.ctx.at.record.dom.monthly.mergetable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
@Getter
@Setter
public class MonthMergeKey {
	/** 社員ID */
	String employeeId;
	/** 年月 */
	YearMonth yearMonth;
	/** 締めID */
	ClosureId closureId;
	/** 締め日付 */
	ClosureDate closureDate;
	
	public MonthMergeKey(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
	}
	
	public MonthMergeKey() {
		super();
	}
}
