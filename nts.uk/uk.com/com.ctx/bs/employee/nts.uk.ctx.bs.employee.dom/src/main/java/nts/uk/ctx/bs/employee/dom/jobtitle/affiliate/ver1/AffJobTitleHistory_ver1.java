package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1;

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
 * The Class AffJobHistory.
 */
// 所属職位履歴
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AffJobTitleHistory_ver1 extends AggregateRoot
		implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
	/** 会社ID */
	private String companyId;
	
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
