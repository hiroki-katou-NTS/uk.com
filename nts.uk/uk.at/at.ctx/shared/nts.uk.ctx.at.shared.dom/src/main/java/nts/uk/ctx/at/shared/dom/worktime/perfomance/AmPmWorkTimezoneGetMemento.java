/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.perfomance;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface AmPmWorkTimezoneGetMemento.
 */
public interface AmPmWorkTimezoneGetMemento {

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public TimeWithDayAttr getStart();

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	// 終了
	public TimeWithDayAttr getEnd();

	/**
	 * Gets the am pm atr.
	 *
	 * @return the am pm atr
	 */
	public AmPmAtr getAmPmAtr();
}
