/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.PositionId;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.KmnmtAffiliJobTitleHistPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public DatePeriod getPeriod() {
		return new DatePeriod(this.jobTitleHistory.getKmnmtJobTitleHistPK().getStrD(),
				this.jobTitleHistory.getEndD());
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
