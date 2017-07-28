/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Getter;

/**
 * The Class Employee.
 */
@Getter
public class Employee {

	/** The business name. */
	private String businessName;

	/** The company id. */
	private String companyId;

	/** The employee id. */
	private Integer employeeId;

	/** The employee code. */
	private EmployeeCode employeeCode;

	/**
	 * Instantiates a new employee.
	 *
	 * @param memento
	 *            the memento
	 */
	public Employee(EmployeeGetMemento memento) {
		this.businessName = memento.getBusinessName();
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.employeeCode = memento.getEmployeeCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmployeeSetMemento memento) {
		memento.setBusinessName(this.businessName);
		memento.setCompanyId(this.companyId);
		memento.setEmployeeCode(this.employeeCode);
		memento.setEmployeeId(this.employeeId);
	}

}
