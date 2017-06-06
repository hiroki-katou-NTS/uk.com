/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

/**
 * The Interface VaAcOrderSetMemento.
 */
public interface AcquisitionOrderSetMemento {
	
	/**
	 * Sets the vacation type.
	 *
	 * @param vacationType the new vacation type
	 */
	void setVacationType(AcquisitionType vacationType);
	
	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	void setPriority(Priority priority);
}
