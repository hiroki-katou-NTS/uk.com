/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Interface DiffTimeHalfDaySetMemento.
 */
public interface DiffTimeHalfDaySetMemento {

	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	public void setRestTimezone(DiffTimeRestTimezone restTimezone);

	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	public void setWorkTimezone(DiffTimezoneSetting workTimezone);

	/**
	 * Sets the am pm cls.
	 *
	 * @param AmPmCls the new am pm cls
	 */
	public void setAmPmCls(AmPmClassification AmPmCls);
}
