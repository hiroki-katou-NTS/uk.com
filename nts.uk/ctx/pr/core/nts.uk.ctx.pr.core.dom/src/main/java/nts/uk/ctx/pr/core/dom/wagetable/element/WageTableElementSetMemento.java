/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionOrder;

/**
 * The Interface WageTableElementSetMemento.
 */
public interface WageTableElementSetMemento {

	/**
	 * Sets the demension no.
	 *
	 * @param demensionNo the new demension no
	 */
	void setDemensionNo(DemensionOrder demensionNo);

	/**
	 * Sets the element mode setting.
	 *
	 * @param elementModeSetting the new element mode setting
	 */
	void setElementModeSetting(ElementMode elementModeSetting);

}
