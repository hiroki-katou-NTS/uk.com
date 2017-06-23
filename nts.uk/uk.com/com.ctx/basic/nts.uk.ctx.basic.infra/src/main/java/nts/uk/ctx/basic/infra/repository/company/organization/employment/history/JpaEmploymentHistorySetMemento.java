/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employment.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistorySetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHistPK;

public class JpaEmploymentHistorySetMemento implements EmploymentHistorySetMemento{
	
	/** The employment history. */
	private KmnmtEmploymentHist employmentHistory;
	
	/**
	 * Instantiates a new jpa employment history set memento.
	 *
	 * @param employmentHistory the employment history
	 */
	public JpaEmploymentHistorySetMemento(KmnmtEmploymentHist employmentHistory) {
		if(employmentHistory.getKmnmtEmploymentHistPK() == null){
			employmentHistory.setKmnmtEmploymentHistPK(new KmnmtEmploymentHistPK());
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
	public void setEmployeeId(EmployeeId employeeId) {
		this.employmentHistory.getKmnmtEmploymentHistPK().setEmptcd(employeeId.v());
	}

}
