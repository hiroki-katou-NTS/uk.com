/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.workplace;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistoryGetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHist;

/**
 * The Class JpaAffiliationWorkplaceHistoryGetMemento.
 */
public class JpaAffiliationWorkplaceHistoryGetMemento
		implements AffiliationWorkplaceHistoryGetMemento {

	/** The workplace history. */
	private KmnmtAffiliWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaAffiliationWorkplaceHistoryGetMemento(KmnmtAffiliWorkplaceHist workplaceHistory) {
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
		return new EmployeeId(this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().getEmpId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().getWplId());
	}

}
