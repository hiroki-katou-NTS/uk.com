/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class StepMode.
 */
@Getter
public class StepMode extends BaseMode {

	/** The upper limit. */
	private BigDecimal upperLimit;

	/** The lower limit. */
	private BigDecimal lowerLimit;

	/** The interval. */
	private BigDecimal interval;

	/** The items. */
	@Setter
	private List<RangeItem> items;

	/**
	 * Instantiates a new step mode.
	 *
	 * @param type
	 *            the type
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
	}

	/**
	 * Instantiates a new step mode.
	 *
	 * @param type
	 *            the type
	 * @param upperLimit
	 *            the upper limit
	 * @param lowerLimit
	 *            the lower limit
	 * @param interval
	 *            the interval
	 * @param items
	 *            the items
	 */
	public StepMode(ElementType type, BigDecimal lowerLimit, BigDecimal upperLimit,
			BigDecimal interval, List<RangeItem> items) {
		super(type);
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		this.interval = interval;
		this.items = items;
	}

}
