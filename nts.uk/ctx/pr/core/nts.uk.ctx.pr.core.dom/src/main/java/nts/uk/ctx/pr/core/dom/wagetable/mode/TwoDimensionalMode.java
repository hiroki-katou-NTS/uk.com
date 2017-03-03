/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Class TwoDimensionalMode.
 */
@Getter
public class TwoDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Two;

	/** The element 1 st. */
	private WageTableElement element1st;

	/** The element 2 nd. */
	private WageTableElement element2nd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode#getMode()
	 */
	@Override
	public ElementCount getMode() {
		return mode;
	}

	/**
	 * Instantiates a new two dimensional mode.
	 *
	 * @param element1st
	 *            the element 1 st
	 * @param element2nd
	 *            the element 2 nd
	 */
	public TwoDimensionalMode(WageTableElement element1st, WageTableElement element2nd) {
		super();
		this.element1st = element1st;
		this.element2nd = element2nd;
	}
}
