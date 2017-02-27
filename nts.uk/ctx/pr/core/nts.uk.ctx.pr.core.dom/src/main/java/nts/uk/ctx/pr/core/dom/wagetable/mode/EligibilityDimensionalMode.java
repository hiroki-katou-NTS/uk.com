/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class EligibilityDimensionalMode.
 */
@Getter
public class EligibilityDimensionalMode implements DimensionalMode {

	/** The Constant mode. */
	public static final ElementCount mode = ElementCount.Eligibility;

	/** The demensions. */
	private List<WageTableElement> demensions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.mode.DimensionalMode#getElements()
	 */
	@Override
	public Map<DemensionOrder, WageTableElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
