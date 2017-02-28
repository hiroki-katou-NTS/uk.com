/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class ThreeDimensionalMode.
 */
@Getter
public class ThreeDimensionalMode implements DimensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Three;

	/** The Constant ELEMENT_SIZE. */
	public static final int ELEMENT_SIZE = 3;

	/** The elements. */
	private List<WageTableElement> elements;

	/**
	 * Instantiates a new one dimensional mode.
	 *
	 * @param elements
	 *            the elements
	 */
	public ThreeDimensionalMode(List<WageTableElement> elements) {
		super();
		if (elements.size() != ELEMENT_SIZE) {
			// TODO
			throw new BusinessException("");
		}

		this.elements = elements;
	}
}
