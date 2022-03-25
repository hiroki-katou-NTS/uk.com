package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@AllArgsConstructor
public class EmploymentAppHistoryItemExport {

	/** 履歴ID */
	private String historyId;

	/** 期間 */
	private DatePeriod datePeriod;
}
