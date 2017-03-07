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
 * The Class FineworkDimensionalMode.
 */
@Getter
public class FineworkDimensionalMode implements DemensionalMode {
	/** The Constant ELEMENT_COUNT. */
	public static final int ELEMENT_COUNT = 3;

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Finework;

	/** The element 1 st. */
	private List<WageTableElement> elements;

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
	 * @param elements
	 *            the elements
	 */
	public FineworkDimensionalMode(List<WageTableElement> elements) {
		super();

		// TODO: Valid element
		// Demension 1 = ElementType.WORKING_DAY
		// Demension 2 = ElementType.COME_LATE
		// Demension 3 = ElementType.LEVEL
		if (elements.size() != ELEMENT_COUNT) {
			throw new BusinessException("");
		}

		this.elements = elements;
	}

}
