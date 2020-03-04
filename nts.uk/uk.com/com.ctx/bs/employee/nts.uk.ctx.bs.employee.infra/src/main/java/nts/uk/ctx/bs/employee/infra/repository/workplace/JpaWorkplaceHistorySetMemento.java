/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceHistorySetMemento.
 */
public class JpaWorkplaceHistorySetMemento implements WorkplaceHistorySetMemento {

	/** The entity. */
	private BsymtWorkplaceHist entity;

	/**
	 * Instantiates a new jpa workplace history set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkplaceHistorySetMemento(BsymtWorkplaceHist entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		entity.getBsymtWorkplaceHistPK().setHistoryId(historyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#setPeriod
	 * (nts.arc.time.calendar.period.DatePeriod)
	 */
	@Override
	public void setPeriod(DatePeriod period) {
		entity.setStrD(period.start());
		entity.setEndD(period.end());
	}

}
