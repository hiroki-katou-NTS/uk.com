package nts.uk.ctx.bs.employee.dom.employment.employmenthistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class EmploymentHistory.
 */
// 雇用履歴
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentHistory extends AggregateRoot
		implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The Date History Item. */
	// 履歴項目
	private List<DateHistoryItem> historyItems;

	@Override
	public List<DateHistoryItem> items() {
		return historyItems;
	}

}
