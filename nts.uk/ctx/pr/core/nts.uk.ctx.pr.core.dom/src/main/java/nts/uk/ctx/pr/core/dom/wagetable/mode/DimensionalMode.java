/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.Map;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Class WageTableHist.
 */
public interface DimensionalMode {

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	Map<DemensionOrder, WageTableElement> getElements();
	
	
}
