/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

/**
 * The Interface CalcResultRangeGetMemento.
 */
public interface CalcResultRangeGetMemento {

	/**
	 * Gets the upper limit.
	 *
	 * @return the upper limit
	 */
	CalcRangeCheck getUpperLimit();

	/**
	 * Gets the lower limit.
	 *
	 * @return the lower limit
	 */
	CalcRangeCheck getLowerLimit();

	/**
	 * Gets the number range.
	 *
	 * @return the number range
	 */
	Optional<NumberRange> getNumberRange();

	/**
	 * Gets the time range.
	 *
	 * @return the time range
	 */
	Optional<TimeRange> getTimeRange();

	/**
	 * Gets the amount range.
	 *
	 * @return the amount range
	 */
	Optional<AmountRange> getAmountRange();
}
