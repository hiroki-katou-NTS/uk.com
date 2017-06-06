/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 社員労働時間設定.
 */
public class EmployeeSetting extends AggregateRoot {

	/** 会社ID. */
	private CompanyId companyId;

	/** 労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/** 年月. */
	private YearMonth yearMonth;

	/** 社員ID. */
	private String employeeId;

}
