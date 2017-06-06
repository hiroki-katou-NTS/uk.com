/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Interface EmploymentSetMemento.
 */
public interface EmploymentSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the work closure id.
	 *
	 * @param workClosureId the new work closure id
	 */
	void setWorkClosureId(Integer workClosureId);
	
	/**
	 * Sets the salary closure id.
	 *
	 * @param salaryClosureId the new salary closure id
	 */
	void setSalaryClosureId(Integer salaryClosureId);
	
	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	void setEmploymentCode(EmploymentCode employmentCode);
	
	/**
	 * Sets the employment name.
	 *
	 * @param employmentName the new employment name
	 */
	void setEmploymentName(EmploymentName employmentName);
}
