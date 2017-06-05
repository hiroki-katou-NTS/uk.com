/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employment;

import nts.uk.ctx.basic.dom.company.organization.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentName;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentSetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmployment;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.CemptEmploymentPK;

/**
 * The Class JpaEmploymentSetMemento.
 */
public class JpaEmploymentSetMemento implements EmploymentSetMemento {

	/** The typed value. */
	private CemptEmployment typedValue;
	
	
	/**
	 * Instantiates a new jpa employment set memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaEmploymentSetMemento(CemptEmployment typedValue) {
		super();
		this.typedValue = typedValue;
		if(this.typedValue.getCemptEmploymentPK() == null) {
			this.typedValue.setCemptEmploymentPK(new CemptEmploymentPK());
		}
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.typedValue.getCemptEmploymentPK().setCid(companyId.v());
	}

	/**
	 * Sets the work closure id.
	 *
	 * @param workClosureId the new work closure id
	 */
	@Override
	public void setWorkClosureId(Integer workClosureId) {
		this.typedValue.setWorkClosureId(workClosureId);
	}

	/**
	 * Sets the salary closure id.
	 *
	 * @param salaryClosureId the new salary closure id
	 */
	@Override
	public void setSalaryClosureId(Integer salaryClosureId) {
		this.typedValue.setSalaryClosureId(salaryClosureId);
	}

	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.typedValue.getCemptEmploymentPK().setCode(employmentCode.v());
	}

	/**
	 * Sets the employment name.
	 *
	 * @param employmentName the new employment name
	 */
	@Override
	public void setEmploymentName(EmploymentName employmentName) {
		this.typedValue.setName(employmentName.v());
	}

}
