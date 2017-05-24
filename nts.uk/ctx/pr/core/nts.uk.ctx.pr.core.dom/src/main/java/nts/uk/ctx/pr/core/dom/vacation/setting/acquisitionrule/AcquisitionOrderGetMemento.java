/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;

import java.util.List;

/**
 * The Interface VaAcOrderGetMemento.
 */
public interface AcquisitionOrderGetMemento {
	
	/**
	 * Gets the vacation type.
	 *
	 * @return the vacation type
	 */
	AcquisitionType getVacationType();
	
	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	Priority getPriority();
}
