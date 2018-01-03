/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface WorkTimezoneOtherSubHolTimeSetGetMemento.
 */
public interface WorkTimezoneOtherSubHolTimeSetGetMemento {

	/**
	 * Gets the sub hol time set.
	 *
	 * @return the sub hol time set
	 */
	 SubHolTransferSet getSubHolTimeSet();

	/**
	 * Gets the work time code.
	 *
	 * @return the work time code
	 */
	 WorkTimeCode getWorkTimeCode();

	/**
	 * Gets the origin atr.
	 *
	 * @return the origin atr
	 */
	 CompensatoryOccurrenceDivision getOriginAtr();
}
