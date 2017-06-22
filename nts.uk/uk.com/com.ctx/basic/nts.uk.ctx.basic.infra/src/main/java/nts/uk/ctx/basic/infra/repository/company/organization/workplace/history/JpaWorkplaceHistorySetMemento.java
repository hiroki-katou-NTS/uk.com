/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;
import nts.uk.ctx.basic.dom.company.organization.workplace.history.WorkplaceHistorySetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHist;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHistPK;

/**
 * The Class JpaWorkplaceHistorySetMemento.
 */
public class JpaWorkplaceHistorySetMemento implements WorkplaceHistorySetMemento {

	/** The workplace history. */
	private KmnmtWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa workplace history set memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaWorkplaceHistorySetMemento(KmnmtWorkplaceHist workplaceHistory) {
		if (workplaceHistory.getKmnmtWorkplaceHistPK() == null) {
			workplaceHistory.setKmnmtWorkplaceHistPK(new KmnmtWorkplaceHistPK());
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
		this.workplaceHistory.getKmnmtWorkplaceHistPK().setSid(employeeId.v());
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
		this.workplaceHistory.getKmnmtWorkplaceHistPK().setWplId(workplaceId.v());
	}

}
