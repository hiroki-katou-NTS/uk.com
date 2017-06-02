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
	 * Sets the work days per month.
	 *
	 * @param workDaysPerMonth the new work days per month
	 */
	void setWorkDaysPerMonth(Integer workDaysPerMonth);
	
	/**
	 * Sets the payment day.
	 *
	 * @param paymentDay the new payment day
	 */
	void setPaymentDay(Integer paymentDay);
	
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
