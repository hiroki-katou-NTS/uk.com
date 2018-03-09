/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimedisplay;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * The Interface WorkTimeDisplayModeGetMemento.
 */
public interface WorkTimeDisplayModeGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the worktime code.
	 *
	 * @return the worktime code
	 */
	WorkTimeCode getWorktimeCode();

	/**
	 * Gets the display mode.
	 *
	 * @return the display mode
	 */
	DisplayMode getDisplayMode();

}
