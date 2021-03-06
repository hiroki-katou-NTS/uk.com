package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の勤務種別の履歴
 * 
 * @author Trung Tran
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTypeOfEmployeeHistory extends AggregateRoot
		implements PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
	/** 会社ID */
	String companyId;

	/** 履歴 */
	List<DateHistoryItem> history;

	/** 社員ID */
	String employeeId;

	@Override
	public List<DateHistoryItem> items() {
		return this.history;
	}

}
