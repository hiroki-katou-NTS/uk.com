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
 * The Class QualificaDimensionalMode.
 */
@Getter
public class QualificaDimensionalMode implements DemensionalMode {

	/** The Constant ELEMENT_COUNT. */
	public static final int ELEMENT_COUNT = 1;

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Qualification;

	/** The elements. */
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
	 * Instantiates a new qualifica dimensional mode.
	 */
	public QualificaDimensionalMode(List<WageTableElement> elements) {
		super();
		// TODO: Valid element
		if (elements.size() != ELEMENT_COUNT) {
			throw new BusinessException("");
		}

		this.elements = elements;
	}

}
