/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class FineworkDimensionalMode.
 */

/*
 * (non-Javadoc)
 * 
 * @see nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode#getElements()
 */
@Getter
public class FineworkDimensionalMode implements DemensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Finework;

	/** The elements. */
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

	public FineworkDimensionalMode(List<WageTableElement> elements) {
		super();

		// Validate
		if (ListUtil.isEmpty(elements)) {
			throw new BusinessException("???");
		}

		this.elements = elements;
	}
}
