/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface GoOutTimezoneRoundingSetGetMemento.
 */
public interface GoOutTimezoneRoundingSetGetMemento {
	
	/**
	 * Gets the pub hol work timezone.
	 *
	 * @return the pub hol work timezone
	 */
	GoOutTypeRoundingSet getPubHolWorkTimezone();
	
	
	/**
	 * Gets the work timezone.
	 *
	 * @return the work timezone
	 */
	GoOutTypeRoundingSet getWorkTimezone();
	
	
	/**
	 * Gets the ottimezone.
	 *
	 * @return the ottimezone
	 */
	GoOutTypeRoundingSet getOttimezone();

}
