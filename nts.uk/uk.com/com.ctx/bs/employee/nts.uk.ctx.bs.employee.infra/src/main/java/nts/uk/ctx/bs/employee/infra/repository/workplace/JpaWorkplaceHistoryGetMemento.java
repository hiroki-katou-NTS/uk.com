/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceHistoryGetMemento.
 */
public class JpaWorkplaceHistoryGetMemento implements WorkplaceHistoryGetMemento {

	/** The history id. */
	private String historyId;

	/** The period. */
	private DatePeriod period;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param item
	 *            the item
	 */
	public JpaWorkplaceHistoryGetMemento(BsymtWorkplaceHist item) {
		this.historyId = item.getBsymtWorkplaceHistPK().getHistoryId();
		this.period = new DatePeriod(item.getStrD(), item.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#
	 * getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.historyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#getPeriod
	 * ()
	 */
	@Override
	public DatePeriod getPeriod() {
		return this.period;
	}

}
