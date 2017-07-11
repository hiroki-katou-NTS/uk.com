/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistoryGetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHistPK;

/**
 * The Class JpaAffJobTitleHistoryGetMemento.
 */
public class JpaAffJobTitleHistoryGetMemento implements AffJobTitleHistoryGetMemento {

	/** The job title history. */
	private KmnmtAffiliJobTitleHist jobTitleHistory;
	
	/**
	 * Instantiates a new jpa job title history get memento.
	 *
	 * @param jobTitleHistory the job title history
	 */
	public JpaAffJobTitleHistoryGetMemento(KmnmtAffiliJobTitleHist jobTitleHistory) {
		if (jobTitleHistory.getKmnmtJobTitleHistPK() == null) {
			jobTitleHistory.setKmnmtJobTitleHistPK(new KmnmtAffiliJobTitleHistPK());
		}
		this.jobTitleHistory = jobTitleHistory;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryGetMemento#getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.jobTitleHistory.getStrD(), this.jobTitleHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.jobTitleHistory.getKmnmtJobTitleHistPK().getEmpId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryGetMemento#getPositionId()
	 */
	@Override
	public PositionId getJobTitleId() {
		return new PositionId(this.jobTitleHistory.getKmnmtJobTitleHistPK().getJobId());
	}

}
