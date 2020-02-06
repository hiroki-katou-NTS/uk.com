package nts.uk.ctx.at.shared.dom.adapter.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BsEmploymentHistoryImport {

	/** The company id. */
	private String employeeId;

	/** The employment code. */
	// 雇用コード.
	private String employmentCode;

	/** The employment name. */
	// 雇用名称.
	private String employmentName;

	// 期間 .
	private DatePeriod period;
}
