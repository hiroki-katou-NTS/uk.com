package nts.uk.ctx.at.function.dom.adapter.workplace;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@Builder
public class WorkplaceImport {
	/** The date range. */
	// 配属期間
	private DatePeriod dateRange;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;

	/** The workplace code. */
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	/** The wkp display name. */
	// 職場表示名
	private String wkpDisplayName;
}
