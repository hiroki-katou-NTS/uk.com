/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.Map;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class OneDimensionalMode.
 */
@Getter
public class OneDimensionalMode implements DimensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.One;

	/** The Constant ELEMENT_SIZE. */
	public static final int ELEMENT_SIZE = 1;

	/** The elements. */
	private Map<DemensionOrder, WageTableElement> elements;

	/**
	 * Instantiates a new one dimensional mode.
	 *
	 * @param elements
	 *            the elements
	 */
	public OneDimensionalMode(Map<DemensionOrder, WageTableElement> elements) {
		super();
		if (elements.size() != ELEMENT_SIZE) {
			// TODO
			throw new BusinessException("");
		}

		this.elements = elements;
	}

}
