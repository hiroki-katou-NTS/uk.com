/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employment.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistoryGetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHistPK;

/**
 * The Class JpaEmploymentHistoryGetMemento.
 */
public class JpaEmploymentHistoryGetMemento implements EmploymentHistoryGetMemento{
	
	/** The employment history. */
	private KmnmtEmploymentHist employmentHistory;
	
	/**
	 * Instantiates a new jpa employment history get memento.
	 *
	 * @param employmentHistory the employment history
	 */
	public JpaEmploymentHistoryGetMemento(KmnmtEmploymentHist employmentHistory) {
		if(employmentHistory.getKmnmtEmploymentHistPK() == null){
			employmentHistory.setKmnmtEmploymentHistPK(new KmnmtEmploymentHistPK());
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
