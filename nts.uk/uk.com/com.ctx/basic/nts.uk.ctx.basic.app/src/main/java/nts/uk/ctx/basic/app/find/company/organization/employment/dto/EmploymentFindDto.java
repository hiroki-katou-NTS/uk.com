/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employment.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentName;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento;


/**
 * The Class EmploymentFindDto.
 */
@Getter
@Setter
public class EmploymentFindDto implements EmploymentSetMemento {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setCompanyId(nts.uk.ctx.basic.dom.company.organization.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setWorkClosureId(java.lang.Integer)
	 */
	@Override
	public void setWorkClosureId(Integer workDaysPerMonth) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setSalaryClosureId(java.lang.Integer)
	 */
	@Override
	public void setSalaryClosureId(Integer paymentDay) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setEmploymentCode(nts.uk.ctx.basic.dom.company.organization.employment.
	 * EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.code = employmentCode.v();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setEmploymentName(nts.uk.ctx.basic.dom.company.organization.employment.
	 * EmploymentName)
	 */
	@Override
	public void setEmploymentName(EmploymentName employmentName) {
		this.name = employmentName.v();
	}

}
