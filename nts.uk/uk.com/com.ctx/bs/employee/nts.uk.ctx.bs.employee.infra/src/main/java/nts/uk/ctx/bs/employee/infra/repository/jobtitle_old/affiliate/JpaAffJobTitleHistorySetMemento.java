/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle_old.affiliate;

import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistorySetMemento;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.PositionId;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.affiliate.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.affiliate.KmnmtAffiliJobTitleHistPK;

/**
 * The Class JpaAffJobTitleHistorySetMemento.
 */
public class JpaAffJobTitleHistorySetMemento implements AffJobTitleHistorySetMemento {
	
	/** The job title history. */
	private KmnmtAffiliJobTitleHist jobTitleHistory;
	
	/**
	 * Instantiates a new jpa job title history set memento.
	 *
	 * @param jobTitleHistory the job title history
	 */
	public JpaAffJobTitleHistorySetMemento(KmnmtAffiliJobTitleHist jobTitleHistory) {
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
		this.jobTitleHistory.getKmnmtJobTitleHistPK().setStrD(period.getStartDate());
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
