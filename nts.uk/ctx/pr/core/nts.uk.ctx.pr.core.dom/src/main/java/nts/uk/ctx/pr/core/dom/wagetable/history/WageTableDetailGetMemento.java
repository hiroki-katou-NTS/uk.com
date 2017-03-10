/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;

/**
 * The Interface WageTableDetailGetMemento.
 */
public interface WageTableDetailGetMemento {

	/**
	 * Gets the demension no.
	 *
	 * @return the demension no
	 */
	DemensionNo getDemensionNo();

	/**
	 * Gets the element mode setting.
	 *
	 * @return the element mode setting
	 */
	ElementMode getElementModeSetting();

}
