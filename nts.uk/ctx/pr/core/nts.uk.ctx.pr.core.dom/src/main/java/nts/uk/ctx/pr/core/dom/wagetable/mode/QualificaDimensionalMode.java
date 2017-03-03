/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Class QualificaDimensionalMode.
 */
@Getter
public class QualificaDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Qualification;

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
	 * Instantiates a new qualifica dimensional mode.
	 *
	 * @param element
	 *            the element
	 */
	public QualificaDimensionalMode(WageTableElement element) {
		super();
		this.element = element;
	}

}
