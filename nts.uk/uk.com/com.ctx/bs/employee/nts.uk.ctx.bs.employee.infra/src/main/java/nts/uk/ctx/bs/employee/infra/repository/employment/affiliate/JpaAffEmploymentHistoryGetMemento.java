/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employment.affiliate;

import nts.uk.ctx.bs.employee.dom.employment.affiliate.AffEmploymentHistoryGetMemento;
import nts.uk.ctx.bs.employee.dom.employment.affiliate.EmploymentCode;
import nts.uk.ctx.bs.employee.infra.entity.employment.affiliate.KmnmtAffiliEmploymentHist;
import nts.uk.ctx.bs.employee.infra.entity.employment.affiliate.KmnmtAffiliEmploymentHistPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaAffEmploymentHistoryGetMemento.
 */
public class JpaAffEmploymentHistoryGetMemento implements AffEmploymentHistoryGetMemento {
	
	/** The employment history. */
	private KmnmtAffiliEmploymentHist employmentHistory;
	
	/**
	 * Instantiates a new jpa employment history get memento.
	 *
	 * @param employmentHistory the employment history
	 */
	public JpaAffEmploymentHistoryGetMemento(KmnmtAffiliEmploymentHist employmentHistory) {
		if(employmentHistory.getKmnmtEmploymentHistPK() == null){
			employmentHistory.setKmnmtEmploymentHistPK(new KmnmtAffiliEmploymentHistPK());
		}
		this.employmentHistory = employmentHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.employmentHistory.getKmnmtEmploymentHistPK().getEmptcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryGetMemento#getPeriod()
	 */
	@Override
	public DatePeriod getPeriod() {
		return new DatePeriod(this.employmentHistory.getKmnmtEmploymentHistPK().getStrD(),
				this.employmentHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.employmentHistory.getKmnmtEmploymentHistPK().getEmpId();
	}

}
