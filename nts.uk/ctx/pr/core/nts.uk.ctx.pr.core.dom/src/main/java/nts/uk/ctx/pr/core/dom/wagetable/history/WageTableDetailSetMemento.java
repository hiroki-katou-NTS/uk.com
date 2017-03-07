/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Interface WageTableHistorySetMemento.
 */
public interface WageTableDetailSetMemento {

	/**
	 * Gets the demension no.
	 *
	 * @return the demension no
	 */
	void setDemensionNo(DemensionNo demensionNo);

	/**
	 * Gets the element mode setting.
	 *
	 * @return the element mode setting
	 */
	void setElementModeSetting(ElementMode elementModeSetting);

}
