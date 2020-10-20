/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

/**
 * The Interface CalcResultRangeSetMemento.
 */
public interface CalcResultRangeSetMemento {

	/**
	 * Sets the upper limit.
	 *
	 * @param upper the new upper limit
	 */
	void setUpperLimit(CalcRangeCheck upper);

	/**
	 * Sets the lower limit.
	 *
	 * @param lower the new lower limit
	 */
	void setLowerLimit(CalcRangeCheck lower);

	/**
	 * Sets the number range.
	 *
	 * @param range the new number range
	 */
	void setNumberRange(Optional<NumberRange> range);

	/**
	 * Sets the time range.
	 *
	 * @param range the new time range
	 */
	void setTimeRange(Optional<TimeRange> range);

	/**
	 * Sets the amount range.
	 *
	 * @param range the new amount range
	 */
	void setAmountRange(Optional<AmountRange> range);
}
