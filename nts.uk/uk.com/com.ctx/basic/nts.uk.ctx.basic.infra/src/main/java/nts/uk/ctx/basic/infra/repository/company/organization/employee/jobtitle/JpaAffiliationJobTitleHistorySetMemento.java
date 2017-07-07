/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffiliationJobTitleHistorySetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHistPK;

/**
 * The Class JpaAffiliationJobTitleHistorySetMemento.
 */
public class JpaAffiliationJobTitleHistorySetMemento
		implements AffiliationJobTitleHistorySetMemento {
	
	/** The job title history. */
	private KmnmtAffiliJobTitleHist jobTitleHistory;
	
	/**
	 * Instantiates a new jpa job title history set memento.
	 *
	 * @param jobTitleHistory the job title history
	 */
	public JpaAffiliationJobTitleHistorySetMemento(KmnmtAffiliJobTitleHist jobTitleHistory) {
		if (jobTitleHistory.getKmnmtJobTitleHistPK() == null) {
			jobTitleHistory.setKmnmtJobTitleHistPK(new KmnmtAffiliJobTitleHistPK());
		}
		this.jobTitleHistory = jobTitleHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistorySetMemento#setPeriod(nts.uk.ctx.basic.dom.common.history.
	 * Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.jobTitleHistory.setStrD(period.getStartDate());
		this.jobTitleHistory.setEndD(period.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistorySetMemento#setEmployeeId(nts.uk.ctx.basic.dom.company.
	 * organization.employee.EmployeeId)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.jobTitleHistory.getKmnmtJobTitleHistPK().setEmpId(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistorySetMemento#setPositionId(nts.uk.ctx.basic.dom.company.
	 * organization.jobtitle.PositionId)
	 */
	@Override
	public void setJobTitleId(PositionId positionId) {
		this.jobTitleHistory.getKmnmtJobTitleHistPK().setJobId(positionId.v());
	}

}
