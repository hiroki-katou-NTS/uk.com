/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceConfigHistoryGetMemento.
 */
public class JpaWorkplaceConfigHistoryGetMemento implements WorkplaceConfigHistoryGetMemento {

	/** The entity. */
	private BsymtWkpConfig entity;
	
	/**
	 * Instantiates a new jpa workplace config history get memento.
	 *
	 * @param item the item
	 */
	public JpaWorkplaceConfigHistoryGetMemento(BsymtWkpConfig item) {
		this.entity = item;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getBsymtWkpConfigPK().getHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistoryGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.entity.getStrD(),this.entity.getEndD());
	}
}
