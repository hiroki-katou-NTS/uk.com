/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Class OneDimensionalMode.
 */
@Getter
public class OneDimensionalMode implements DemensionalMode {

	/** The Constant ELEMENT_COUNT. */
	public static final int ELEMENT_COUNT = 1;

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.One;

	/** The element. */
	private List<WageTableElement> elements;

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
	public OneDimensionalMode(List<WageTableElement> elements) {
		super();

		// TODO
		if (elements.size() != ELEMENT_COUNT) {
			throw new BusinessException("");
		}

		this.elements = elements;
	}

}
