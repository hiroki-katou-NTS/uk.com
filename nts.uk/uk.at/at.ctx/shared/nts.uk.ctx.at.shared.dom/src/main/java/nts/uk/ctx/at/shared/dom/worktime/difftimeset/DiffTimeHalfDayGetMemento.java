/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Interface DiffTimeHalfDayGetMemento.
 */
public interface DiffTimeHalfDayGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	public DiffTimeRestTimezone getRestTimezone();

	/**
	 * Gets the work timezone.
	 *
	 * @return the work timezone
	 */
	public DiffTimezoneSetting getWorkTimezone();

	/**
	 * Gets the am pm cls.
	 *
	 * @return the am pm cls
	 */
	public AmPmClassification getAmPmCls();
}
