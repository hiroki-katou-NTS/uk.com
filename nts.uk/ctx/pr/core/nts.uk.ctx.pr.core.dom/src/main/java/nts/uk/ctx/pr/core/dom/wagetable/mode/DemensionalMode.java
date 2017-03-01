/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.mode;

import java.util.List;

import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableElement;

/**
 * The Interface DimensionalMode.
 */
public interface DemensionalMode {

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	List<WageTableElement> getElements();

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	ElementCount getMode();

}
