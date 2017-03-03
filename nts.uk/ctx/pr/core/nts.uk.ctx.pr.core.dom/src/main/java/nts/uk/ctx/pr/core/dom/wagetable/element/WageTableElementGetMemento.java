/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;

/**
 * The Interface WageTableElementGetMemento.
 */
public interface WageTableElementGetMemento {

	/**
	 * Gets the demension no.
	 *
	 * @return the demension no
	 */
	DemensionOrder getDemensionNo();

	/**
	 * Gets the element mode setting.
	 *
	 * @return the element mode setting
	 */
	ElementMode getElementModeSetting();

}
