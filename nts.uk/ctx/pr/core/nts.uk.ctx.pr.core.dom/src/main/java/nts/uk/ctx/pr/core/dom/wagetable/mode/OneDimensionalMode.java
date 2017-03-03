/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Class OneDimensionalMode.
 */
@Getter
public class OneDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.One;

	/** The element. */
	private WageTableElement element;

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
	 * Instantiates a new one dimensional mode.
	 *
	 * @param element
	 *            the element
	 */
	public OneDimensionalMode(WageTableElement element) {
		super();
		this.element = element;
	}

}
