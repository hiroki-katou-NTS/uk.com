/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employment.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmpExternalCode;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentName;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentSetMemento;
import nts.uk.ctx.basic.dom.company.organization.employment.Memo;


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
	
	/** The emp external code. */
	private String empExternalCode;
	
	/** The memo. */
	private String memo;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setEmpExternalCode(nts.uk.ctx.basic.dom.company.organization.employment.
	 * EmpExternalCode)
	 */
	@Override
	public void setEmpExternalCode(EmpExternalCode empExternalCode) {
		this.empExternalCode = empExternalCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento
	 * #setMemo(nts.uk.ctx.basic.dom.company.organization.employment.Memo)
	 */
	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

}
