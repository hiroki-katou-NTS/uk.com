/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class ThreeDimensionalMode.
 */
@Getter
public class ThreeDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Three;

	/** The element 1 st. */
	private WageTableElement element1st;

	/** The element 2 nd. */
	private WageTableElement element2nd;

	/** The element 3 rd. */
	private WageTableElement element3rd;

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
	 * Instantiates a new three dimensional mode.
	 *
	 * @param element1st
	 *            the element 1 st
	 * @param element2nd
	 *            the element 2 nd
	 * @param element3rd
	 *            the element 3 rd
	 */
	public ThreeDimensionalMode(WageTableElement element1st, WageTableElement element2nd, WageTableElement element3rd) {
		super();
		this.element1st = element1st;
		this.element2nd = element2nd;
		this.element3rd = element3rd;
	}
}
