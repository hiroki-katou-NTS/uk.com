/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class FineworkDimensionalMode.
 */
@Getter
public class FineworkDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Finework;

	/** The element 1 st. */
	private WageTableElement element1st;

	/** The element 2 nd. */
	private WageTableElement element2nd;

	/** The element 3 rd. */
	private WageTableElement element3rd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.mode.DimensionalMode#getMode()
	 */
	@Override
	public ElementCount getMode() {
		return mode;
	}

	/**
	 * Instantiates a new finework dimensional mode.
	 *
	 * @param element1st
	 *            the element 1 st
	 * @param element2nd
	 *            the element 2 nd
	 * @param element3nd
	 *            the element 3 nd
	 */
	public FineworkDimensionalMode(WageTableElement element1st, WageTableElement element2nd,
			WageTableElement element3rd) {
		super();
		this.element1st = element1st;
		this.element2nd = element2nd;
		this.element3rd = element3rd;
	}

}
