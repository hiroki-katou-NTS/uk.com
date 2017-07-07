/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.employment;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistorySetMemento;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHistPK;

/**
 * The Class JpaAffiliationEmploymentHistorySetMemento.
 */
public class JpaAffiliationEmploymentHistorySetMemento
		implements AffiliationEmploymentHistorySetMemento {
	
	/** The employment history. */
	private KmnmtAffiliEmploymentHist employmentHistory;
	
	/**
	 * Instantiates a new jpa employment history set memento.
	 *
	 * @param employmentHistory the employment history
	 */
	public JpaAffiliationEmploymentHistorySetMemento(KmnmtAffiliEmploymentHist employmentHistory) {
		if(employmentHistory.getKmnmtEmploymentHistPK() == null){
			employmentHistory.setKmnmtEmploymentHistPK(new KmnmtAffiliEmploymentHistPK());
		}
		this.employmentHistory = employmentHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistorySetMemento#setEmploymentCode(nts.uk.ctx.basic.dom.
	 * company.organization.employment.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentHistory.getKmnmtEmploymentHistPK().setEmptcd(employmentCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistorySetMemento#setPeriod(nts.uk.ctx.basic.dom.common.history
	 * .Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.employmentHistory.setStrD(period.getStartDate());
		this.employmentHistory.setEndD(period.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistorySetMemento#setEmployeeId(nts.uk.ctx.basic.dom.company.
	 * organization.employee.EmployeeId)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.employmentHistory.getKmnmtEmploymentHistPK().setEmpId(employeeId);
	}

}
