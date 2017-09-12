/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.affiliate;

import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistorySetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.WorkplaceId;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.KmnmtAffiliWorkplaceHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.KmnmtAffiliWorkplaceHistPK;

/**
 * The Class JpaAffWorkplaceHistorySetMemento.
 */
public class JpaAffWorkplaceHistorySetMemento implements AffWorkplaceHistorySetMemento {

	/** The workplace history. */
	private KmnmtAffiliWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa aff workplace history set memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaAffWorkplaceHistorySetMemento(KmnmtAffiliWorkplaceHist workplaceHistory) {
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
		this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().setStrD(period.getStartDate());
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
	public void setEmployeeId(String employeeId) {
		this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().setEmpId(employeeId);
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
		this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().setWkpId(workplaceId.v());
	}

}
