package nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@AllArgsConstructor
public class DateHistoryItemImport {

	/** The company id. */
	private String historyId;

	/** The employee id. */
	// 社員ID
	private DatePeriod period;

}
