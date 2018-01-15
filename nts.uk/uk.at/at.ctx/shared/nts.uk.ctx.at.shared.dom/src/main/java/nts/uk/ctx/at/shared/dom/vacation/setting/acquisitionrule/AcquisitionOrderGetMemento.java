/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

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
