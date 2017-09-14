/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employment_old;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.employment_old.EmploymentCode;
import nts.uk.ctx.bs.employee.dom.employment_old.EmploymentGetMemento;
import nts.uk.ctx.bs.employee.dom.employment_old.EmploymentName;
import nts.uk.ctx.bs.employee.infra.entity.employment_old.CemptEmployment;

/**
 * The Class JpaEmploymentGetMemento.
 */
public class JpaEmploymentGetMemento implements EmploymentGetMemento {

	/** The typed value. */
	private CemptEmployment typedValue;
	
	/**
	 * Instantiates a new jpa employment get memento.
	 *
	 * @param typedValue the typed value
	 */
	public JpaEmploymentGetMemento(CemptEmployment typedValue) {
		super();
		this.typedValue = typedValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.EmploymentGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typedValue.getCemptEmploymentPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.EmploymentGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.typedValue.getCemptEmploymentPK().getCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.EmploymentGetMemento#getEmploymentName()
	 */
	@Override
	public EmploymentName getEmploymentName() {
		return new EmploymentName(this.typedValue.getName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.EmploymentGetMemento#getWorkClosureId()
	 */
	@Override
	public Integer getWorkClosureId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.EmploymentGetMemento#getSalaryClosureId()
	 */
	@Override
	public Integer getSalaryClosureId() {
		// TODO Auto-generated method stub
		return null;
	}

}
