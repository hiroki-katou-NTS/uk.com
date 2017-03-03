/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class AgeFixMode.
 */
@Getter
public class StepMode extends BaseMode {
	/** The upper limit. */
	// Demension
	private BigDecimal upperLimit;

	/** The lower limit. */
	private BigDecimal lowerLimit;

	/** The interval. */
	private BigDecimal interval;

	/** The items. */
	// private List<RangeItem> items;

	/**
	 * Instantiates a new age fix mode.
	 *
	 * @param lowerLimit
	 *            the lower limit
	 * @param upperLimit
	 *            the upper limit
	 * @param interval
	 *            the interval
	 */
	public StepMode(ElementType type, BigDecimal lowerLimit, BigDecimal upperLimit,
			BigDecimal interval) {
		super(type);
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.interval = interval;

		// List<RangeItem> items = new ArrayList<>();
		//
		// int index = 0;
		// int start = lowerLimit;
		// while (start <= upperLimit) {
		// index++;
		// int end = start + interval - 1;
		//
		// items.add(new RangeItem(index, start, ((end <= upperLimit) ? end :
		// upperLimit),
		// IdentifierUtil.randomUniqueId()));
		//
		// start = start + interval;
		// }
		//
		// this.items = items;
	}
}
