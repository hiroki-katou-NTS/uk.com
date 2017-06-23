/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.employment;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistoryGetMemento;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHistPK;

/**
 * The Class JpaAffiliationEmploymentHistoryGetMemento.
 */
public class JpaAffiliationEmploymentHistoryGetMemento
		implements AffiliationEmploymentHistoryGetMemento {
	
	/** The employment history. */
	private KmnmtAffiliEmploymentHist employmentHistory;
	
	/**
	 * Instantiates a new jpa employment history get memento.
	 *
	 * @param employmentHistory the employment history
	 */
	public JpaAffiliationEmploymentHistoryGetMemento(KmnmtAffiliEmploymentHist employmentHistory) {
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
	public Period getPeriod() {
		return new Period(this.employmentHistory.getStrD(), this.employmentHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.employmentHistory.getKmnmtEmploymentHistPK().getSid());
	}

}
