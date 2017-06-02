/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import lombok.Getter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * Gets the employment name.
 *
 * @return the employment name
 */
@Getter
public class Employment {
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The work days per month. */
	private Integer workDaysPerMonth;
	
	/** The payment date. */
	private Integer paymentDate;
	
	/** The employment code. */
	private EmploymentCode employmentCode;
	
	/** The employment name. */
	private EmploymentName employmentName;
	
	/**
	 * Instantiates a new employment.
	 *
	 * @param companyId the company id
	 * @param workDaysPerMonth the work days per month
	 * @param paymentDate the payment date
	 * @param employmentCode the employment code
	 * @param employmentName the employment name
	 */
	public Employment(CompanyId companyId, Integer workDaysPerMonth, Integer paymentDate, EmploymentCode employmentCode,
			EmploymentName employmentName) {
		super();
		this.companyId = companyId;
		this.workDaysPerMonth = workDaysPerMonth;
		this.paymentDate = paymentDate;
		this.employmentCode = employmentCode;
		this.employmentName = employmentName;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmploymentSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setEmploymentName(this.employmentName);
		memento.setWorkDaysPerMonth(this.workDaysPerMonth);
		memento.setPaymentDay(this.paymentDate);
	}
	
}
