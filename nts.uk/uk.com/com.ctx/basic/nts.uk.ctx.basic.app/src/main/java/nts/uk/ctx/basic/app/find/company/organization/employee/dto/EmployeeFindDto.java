/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeMailAddress;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeSetMemento;
import nts.uk.ctx.basic.dom.company.organization.employee.PersonId;

/**
 * The Class EmployeeFindDto.
 */
@Getter
@Setter
public class EmployeeFindDto implements EmployeeSetMemento {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
		
	}

	@Override
	public void setPersonId(PersonId personId) {
		// No thing code
		
	}

	@Override
	public void setJoinDate(GeneralDate joinDate) {
		// No thing code
		
	}

	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		// No thing code
		
	}

	@Override
	public void setEmployeeCode(EmployeeCode employeeCode) {
		this.code = employeeCode.v();
	}

	@Override
	public void setEmployeeMailAddress(EmployeeMailAddress employeeMailAddress) {
		// No thing code
		
	}

	@Override
	public void setRetirementDate(GeneralDate retirementDate) {
		// No thing code
		
	}
}
