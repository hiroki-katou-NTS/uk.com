/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimedisplay;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * The Interface WorkTimeDisplayModeSetMemento.
 */
public interface WorkTimeDisplayModeSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
	 * Sets the worktime code.
	 *
	 * @param workTimeCode the new worktime code
	 */
	void setWorktimeCode(WorkTimeCode workTimeCode);

	/**
	 * Sets the work time division.
	 *
	 * @param displayMode the new work time division
	 */
	void setWorkTimeDivision(DisplayMode displayMode);

}
