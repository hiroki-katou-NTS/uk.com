/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

/**
 * The Interface CalculationResultRangeGetMemento.
 */
public interface CalculationResultRangeGetMemento {

	/**
	 * Gets the upper limit.
	 *
	 * @return the upper limit
	 */
	CalculationRangeCheck getUpperLimit();

	/**
	 * Gets the lower limit.
	 *
	 * @return the lower limit
	 */
	CalculationRangeCheck getLowerLimit();

	/**
	 * Gets the number range.
	 *
	 * @return the number range
	 */
	NumberRange getNumberRange();

	/**
	 * Gets the time range.
	 *
	 * @return the time range
	 */
	TimeRange getTimeRange();

	/**
	 * Gets the amount range.
	 *
	 * @return the amount range
	 */
	AmountRange getAmountRange();
}
