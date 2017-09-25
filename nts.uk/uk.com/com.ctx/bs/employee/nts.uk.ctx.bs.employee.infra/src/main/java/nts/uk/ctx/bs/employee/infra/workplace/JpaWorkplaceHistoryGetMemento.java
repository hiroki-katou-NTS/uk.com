/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;

/**
 * The Class JpaWorkplaceHistoryGetMemento.
 */
public class JpaWorkplaceHistoryGetMemento implements WorkplaceHistoryGetMemento {

	/** The history id. */
	private String historyId;

	/** The period. */
	private Period period;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param item
	 *            the item
	 */
	public JpaWorkplaceHistoryGetMemento(BsymtWorkplaceHist item) {
		this.historyId = item.getBsymtWorkplaceHistPK().getHistoryId();
		this.period = new Period(item.getStrD(), item.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#
	 * getHistoryId()
	 */
	@Override
	public HistoryId getHistoryId() {
		return new HistoryId(this.historyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistoryGetMemento#getPeriod
	 * ()
	 */
	@Override
	public Period getPeriod() {
		return this.period;
	}

}
