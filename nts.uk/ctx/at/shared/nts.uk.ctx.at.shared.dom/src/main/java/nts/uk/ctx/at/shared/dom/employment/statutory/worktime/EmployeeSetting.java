/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class EmployeeSetting.
 */
public class EmployeeSetting extends AggregateRoot {

	/** The working time setting. */
	private WorkingTimeSetting workingTimeSetting;

	/** The year. */
	private Year year;

	/** The company id. */
	private String companyId;

	/** The employee id. */
	private String employeeId;

	/** The year month. */
	private YearMonth yearMonth;
}
