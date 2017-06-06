/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeMailAddress;
import nts.uk.ctx.basic.dom.company.organization.employee.PersonId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployee;

/**
 * The Class JpaEmployeeGetMemento.
 */
public class JpaEmployeeGetMemento implements EmployeeGetMemento{
	
	/**
	 * Sets the cempt employee.
	 *
	 * @param cemptEmployee the new cempt employee
	 */
	@Setter
	private CemptEmployee cemptEmployee;

	/**
	 * Instantiates a new jpa employee get memento.
	 *
	 * @param cemptEmployee the cempt employee
	 */
	public JpaEmployeeGetMemento(CemptEmployee cemptEmployee) {
		this.cemptEmployee = cemptEmployee;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.cemptEmployee.getCemptEmployeePK().getCcid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getPersonId()
	 */
	@Override
	public PersonId getPersonId() {
		return new PersonId(this.cemptEmployee.getCemptEmployeePK().getPid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getJoinDate()
	 */
	@Override
	public GeneralDate getJoinDate() {
		return this.cemptEmployee.getJoinDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.cemptEmployee.getCemptEmployeePK().getEmpid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getEmployeeCode()
	 */
	@Override
	public EmployeeCode getEmployeeCode() {
		return new EmployeeCode(this.cemptEmployee.getCemptEmployeePK().getEmpcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getEmployeeMailAddress()
	 */
	@Override
	public EmployeeMailAddress getEmployeeMailAddress() {
		return new EmployeeMailAddress(this.cemptEmployee.getEmpmailAdrr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeGetMemento#
	 * getRetirementDate()
	 */
	@Override
	public GeneralDate getRetirementDate() {
		return this.cemptEmployee.getRetirementDate();
	}

}
