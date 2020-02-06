package nts.uk.ctx.at.record.dom.adapter.workplace.affiliate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@Setter
@NoArgsConstructor
public class AffWorkPlaceSidImport {
	
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

	public AffWorkPlaceSidImport(DatePeriod dateRange, String employeeId, String workplaceId, String workplaceCode,
			String workplaceName, String wkpDisplayName) {
		super();
		this.dateRange = dateRange;
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
		this.wkpDisplayName = wkpDisplayName;
	}


}
