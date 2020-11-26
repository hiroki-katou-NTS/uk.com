package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@AllArgsConstructor
public class DateHistoryItemExport {

	/** The company id. */
	private String historyId;

	/** The employee id. */
	// 社員ID
	private DatePeriod period;

}
