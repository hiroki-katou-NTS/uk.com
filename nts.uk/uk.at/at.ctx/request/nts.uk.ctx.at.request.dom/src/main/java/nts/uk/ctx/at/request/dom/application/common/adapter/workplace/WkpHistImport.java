package nts.uk.ctx.at.request.dom.application.common.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author hoatt
 *
 */
@Value
@AllArgsConstructor
public class WkpHistImport {

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
