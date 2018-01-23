/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface WorkTimezoneOtherSubHolTimeSetSetMemento.
 */
public interface WorkTimezoneOtherSubHolTimeSetSetMemento {

	/**
	 * Sets the sub hol time set.
	 *
	 * @param set the new sub hol time set
	 */
	 void  setSubHolTimeSet(SubHolTransferSet set);

	/**
	 * Sets the work time code.
	 *
	 * @param cd the new work time code
	 */
	 void setWorkTimeCode(WorkTimeCode cd);

	/**
	 * Sets the origin atr.
	 *
	 * @param atr the new origin atr
	 */
	 void setOriginAtr(CompensatoryOccurrenceDivision atr);
}
