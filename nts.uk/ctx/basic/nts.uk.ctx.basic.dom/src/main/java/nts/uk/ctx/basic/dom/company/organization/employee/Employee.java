/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class Employee.
 */

@Getter
public class Employee extends AggregateRoot{
	
	/** The company id. */
	private CompanyId companyId;
	
	/** The person id. */
	private PersonId personId;
	
	/** The join date. */
	private GeneralDate joinDate;
	
	/** The employee id. */
	private EmployeeId employeeId;
	
	/** The employee code. */
	private EmployeeCode employeeCode;
	
	/** The employee mail address. */
	private EmployeeMailAddress employeeMailAddress;
	
	/** The retirement date. */
	private GeneralDate retirementDate;
	
	
	/**
	 * Instantiates a new employee.
	 *
	 * @param memento the memento
	 */
	public Employee(EmployeeGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.personId = memento.getPersonId();
		this.joinDate = memento.getJoinDate();
		this.employeeId = memento.getEmployeeId();
		this.employeeCode = memento.getEmployeeCode();
		this.employeeMailAddress = memento.getEmployeeMailAddress();
		this.retirementDate = memento.getRetirementDate();
	}
	
	
	/**
	 * Save memento.
	 *
	 * @param memento the memento
	 */
	public void saveMemento(EmployeeSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setPersonId(this.personId);
		memento.setJoinDate(this.joinDate);
		memento.setEmployeeId(this.employeeId);
		memento.setEmployeeCode(this.employeeCode);
		memento.setEmployeeMailAddress(this.employeeMailAddress);
		memento.setRetirementDate(this.retirementDate);
	}
	
}
