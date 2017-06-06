/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

/**
 * The Class ClosureHistoryGetMemento.
 */
public interface ClosureHistoryGetMemento {
	
	/**
	 * Gets the close name.
	 *
	 * @return the close name
	 */
	CloseName getCloseName();

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	ClosureId getClosureId();
	
	
	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	ClosureYearMonth getEndDate();
	
	/**
	 * Gets the closure date.
	 *
	 * @return the closure date
	 */
	ClosureDate getClosureDate();
	
	
	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	ClosureYearMonth getStartDate();
}
