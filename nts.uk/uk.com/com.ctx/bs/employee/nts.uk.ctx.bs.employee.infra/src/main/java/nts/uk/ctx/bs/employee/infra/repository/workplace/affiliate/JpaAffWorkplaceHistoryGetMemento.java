/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.affiliate;

import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.WorkplaceId;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.KmnmtAffiliWorkplaceHist;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaAffWorkplaceHistoryGetMemento.
 */
public class JpaAffWorkplaceHistoryGetMemento implements AffWorkplaceHistoryGetMemento {

	/** The workplace history. */
	private KmnmtAffiliWorkplaceHist workplaceHistory;

	/**
	 * Instantiates a new jpa workplace history get memento.
	 *
	 * @param workplaceHistory the workplace history
	 */
	public JpaAffWorkplaceHistoryGetMemento(KmnmtAffiliWorkplaceHist workplaceHistory) {
		this.workplaceHistory = workplaceHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().getStrD(),
				this.workplaceHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().getEmpId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.workplaceHistory.getKmnmtAffiliWorkplaceHistPK().getWkpId());
	}

}
