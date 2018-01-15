/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface SubHolTransferSetGetMemento.
 */
public interface SubHolTransferSetGetMemento {

	/**
	 * Gets the certain time.
	 *
	 * @return the certain time
	 */
	OneDayTime getCertainTime();

	/**
	 * Gets the use division.
	 *
	 * @return the use division
	 */
	 boolean getUseDivision();

	/**
	 * Gets the designated time.
	 *
	 * @return the designated time
	 */
	 DesignatedTime getDesignatedTime();

	/**
	 * Gets the sub hol transfer set atr.
	 *
	 * @return the sub hol transfer set atr
	 */
	 SubHolTransferSetAtr getSubHolTransferSetAtr();
}
