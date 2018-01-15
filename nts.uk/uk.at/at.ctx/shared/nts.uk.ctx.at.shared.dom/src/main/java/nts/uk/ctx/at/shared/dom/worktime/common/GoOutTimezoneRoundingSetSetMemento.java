/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface GoOutTimezoneRoundingSetSetMemento.
 */
public interface GoOutTimezoneRoundingSetSetMemento {
	
	/**
	 * Sets the pub hol work timezone.
	 *
	 * @param pubHolWorkTimezone the new pub hol work timezone
	 */
	void setPubHolWorkTimezone(GoOutTypeRoundingSet pubHolWorkTimezone);
	
	
	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	void setWorkTimezone(GoOutTypeRoundingSet workTimezone);
	
	
	/**
	 * Sets the ottimezone.
	 *
	 * @param ottimezone the new ottimezone
	 */
	void setOttimezone(GoOutTypeRoundingSet ottimezone);

}
