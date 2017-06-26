/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.workplace;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistorySetMemento;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHistPK;

/**
 * The Class JpaAffiliationWorkplaceHistorySetMemento.
 */
public class JpaAffiliationWorkplaceHistorySetMemento
		implements AffiliationWorkplaceHistorySetMemento {

	/** The workplace history. */
	private KmnmtAffiliWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa workplace history set memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaAffiliationWorkplaceHistorySetMemento(KmnmtAffiliWorkplaceHist workplaceHistory) {
		if (workplaceHistory.getKmnmtAffiliWorkplaceHistPK() == null) {
			workplaceHistory.setKmnmtAffiliWorkplaceHistPK(new KmnmtAffiliWorkplaceHistPK());
		}
		this.workplaceHistory = workplaceHistory;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistorySetMemento#setPeriod(nts.uk.ctx.basic.dom.common.history.
	 * Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.workplaceHistory.setStrD(period.getStartDate());
		this.workplaceHistory.setEndD(period.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistorySetMemento#setEmployeeId(nts.uk.ctx.basic.dom.company.
	 * organization.employee.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().setSid(employeeId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistorySetMemento#setWorkplaceId(nts.uk.ctx.basic.dom.company.
	 * organization.workplace.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().setWplId(workplaceId.v());
	}

}
