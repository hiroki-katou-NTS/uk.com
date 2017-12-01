/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface PrioritySettingSetMemento.
 */
public interface PrioritySettingSetMemento {

	/**
	 * Sets the priority atr.
	 *
	 * @param atr the new priority atr
	 */
	 void setPriorityAtr(MultiStampTimePiorityAtr atr);

	/**
	 * Sets the stamp atr.
	 *
	 * @param atr the new stamp atr
	 */
	 void setStampAtr(StampPiorityAtr  atr);
}
