/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrderSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Priority;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KarstAcquisitionRule;

/**
 * The Class JpaAcquisitionOrderSetMemento.
 */
public class JpaAcquisitionOrderSetMemento implements AcquisitionOrderSetMemento {

	/** The type value. */
	private KarstAcquisitionRule typeValue;

	/** The va type. */
	private AcquisitionType vaType;

	/** The priority. */
	private Priority priority;

	/**
	 * Instantiates a new jpa acquisition order set memento.
	 *
	 * @param vaType
	 *            the va type
	 * @param priority
	 *            the priority
	 */
	public JpaAcquisitionOrderSetMemento(AcquisitionType vaType, Priority priority) {
		super();
		this.vaType = vaType;
		this.priority = priority;
	}

	/**
	 * Sets the vacation type.
	 *
	 * @param vacationType
	 *            the new vacation type
	 */
	@Override
	public void setVacationType(AcquisitionType vacationType) {
		this.vaType = vacationType;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	@Override
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
