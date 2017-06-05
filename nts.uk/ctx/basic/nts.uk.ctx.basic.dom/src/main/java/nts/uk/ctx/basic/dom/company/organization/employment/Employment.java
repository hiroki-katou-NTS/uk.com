/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class Employment.
 */

/**
 * Gets the employment name.
 *
 * @return the employment name
 */

/**
 * Gets the employment name.
 *
 * @return the employment name
 */

/**
 * Gets the employment name.
 *
 * @return the employment name
 */
@Getter
public class Employment extends AggregateRoot{
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The work closure id. */
	private Integer workClosureId;
	
	/** The salary closure id. */
	private Integer salaryClosureId;
	
	/** The employment code. */
	private EmploymentCode employmentCode;
	
	/** The employment name. */
	private EmploymentName employmentName;
	
	/**
	 * Instantiates a new employment.
	 *
	 * @param memento the memento
	 */
	public Employment(EmploymentGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.employmentName = memento.getEmploymentName();
		this.workClosureId = memento.getWorkClosureId();
		this.salaryClosureId = memento.getSalaryClosureId();
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
		memento.setWorkClosureId(this.workClosureId);
		memento.setSalaryClosureId(this.salaryClosureId);
	}
	
}
