/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface SubHolTransferSetSetMemento.
 */
public interface SubHolTransferSetSetMemento {

	/**
	 * Sets the certain time.
	 *
	 * @param time the new certain time
	 */
	void setCertainTime(OneDayTime time);

	/**
	 * Sets the use division.
	 *
	 * @param val the new use division
	 */
	 void setUseDivision(boolean val);

	/**
	 * Sets the designated time.
	 *
	 * @param time the new designated time
	 */
	 void setDesignatedTime(DesignatedTime time);

	/**
	 * Sets the sub hol transfer set atr.
	 *
	 * @param atr the new sub hol transfer set atr
	 */
	 void setSubHolTransferSetAtr(SubHolTransferSetAtr atr);
}
