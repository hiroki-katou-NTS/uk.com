/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.Getter;

/**
 * The Class AcquisitionOrder.
 */
@Getter
public class AcquisitionOrder {

	/** The vacation type. */
	private AcquisitionType vacationType;

	/** The priority. */
	private Priority priority;

	/**
	 * Instantiates a new acquisition order.
	 *
	 * @param memento
	 *            the memento
	 */
	public AcquisitionOrder(AcquisitionOrderGetMemento memento) {
		super();
		this.vacationType = memento.getVacationType();
		this.priority = memento.getPriority();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AcquisitionOrderSetMemento memento) {
		memento.setVacationType(this.vacationType);
		memento.setPriority(this.priority);
	}
}
