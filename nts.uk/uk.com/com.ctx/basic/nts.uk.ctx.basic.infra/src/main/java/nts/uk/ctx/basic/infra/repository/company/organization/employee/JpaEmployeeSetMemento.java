/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeMailAddress;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento;
import nts.uk.ctx.basic.dom.company.organization.employee.PersonId;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployee;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployeePK;

/**
 * The Class JpaEmployeeSetMemento.
 */
public class JpaEmployeeSetMemento implements EmployeeSetMemento{
	
	/** The cempt employee. */
	
	/**
	 * Sets the cempt employee.
	 *
	 * @param cemptEmployee the new cempt employee
	 */
	@Setter
	private CemptEmployee cemptEmployee;

	/**
	 * Instantiates a new jpa employee set memento.
	 *
	 * @param cemptEmployee the cempt employee
	 */
	public JpaEmployeeSetMemento(CemptEmployee cemptEmployee) {
		this.cemptEmployee = cemptEmployee;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setCompanyId(nts.uk.ctx.basic.dom.company.organization.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CemptEmployeePK pk = new CemptEmployeePK();
		pk.setCcid(companyId.v());
		this.cemptEmployee.setCemptEmployeePK(pk);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setPersonId(nts.uk.ctx.basic.dom.company.organization.employee.PersonId)
	 */
	@Override
	public void setPersonId(PersonId personId) {
		CemptEmployeePK pk = this.cemptEmployee.getCemptEmployeePK();
		pk.setPid(personId.v());
		this.cemptEmployee.setCemptEmployeePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setJoinDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setJoinDate(GeneralDate joinDate) {
		this.cemptEmployee.setJoinDate(joinDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setEmployeeId(nts.uk.ctx.basic.dom.company.organization.employee.
	 * EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		CemptEmployeePK pk = this.cemptEmployee.getCemptEmployeePK();
		pk.setEmpid(employeeId.v());
		this.cemptEmployee.setCemptEmployeePK(pk);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setEmployeeCode(nts.uk.ctx.basic.dom.company.organization.employee.
	 * EmployeeCode)
	 */
	@Override
	public void setEmployeeCode(EmployeeCode employeeCode) {
		CemptEmployeePK pk = this.cemptEmployee.getCemptEmployeePK();
		pk.setEmpcd(employeeCode.v());
		this.cemptEmployee.setCemptEmployeePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setEmployeeMailAddress(nts.uk.ctx.basic.dom.company.organization.employee
	 * .EmployeeMailAddress)
	 */
	@Override
	public void setEmployeeMailAddress(EmployeeMailAddress employeeMailAddress) {
		this.cemptEmployee.setEmpmailAdrr(employeeMailAddress.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento#
	 * setRetirementDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setRetirementDate(GeneralDate retirementDate) {
		this.cemptEmployee.setRetirementDate(retirementDate);
	}

}
