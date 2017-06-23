/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.dom.company.organization.workplace.history.WorkplaceHistoryGetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHist;

/**
 * The Class JpaWorkplaceHistoryGetMemento.
 */
public class JpaWorkplaceHistoryGetMemento implements WorkplaceHistoryGetMemento {

	/** The workplace history. */
	private KmnmtWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaWorkplaceHistoryGetMemento(KmnmtWorkplaceHist workplaceHistory) {
		this.workplaceHistory = workplaceHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.workplaceHistory.getStrD(), this.workplaceHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.workplaceHistory.getKmnmtWorkplaceHistPK().getSid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.workplaceHistory.getKmnmtWorkplaceHistPK().getWplId());
	}

}
