/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.workplace;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class SWkpHistExport.
 */
// 社員所属職場履歴を取得
@Data
@Builder
public class SWkpHistExport {

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
	
	// 階層コード
	private String hierarchyCode;

	public SWkpHistExport(DatePeriod dateRange, String employeeId, String workplaceId, String workplaceCode,
							  String workplaceName, String wkpDisplayName, String hierarchyCode) {
		super();
		this.dateRange = dateRange;
		this.employeeId = employeeId;
		this.workplaceId = workplaceId;
		this.workplaceCode = workplaceCode;
		this.workplaceName = workplaceName;
		this.wkpDisplayName = wkpDisplayName;
		this.hierarchyCode = hierarchyCode;
	}

}
