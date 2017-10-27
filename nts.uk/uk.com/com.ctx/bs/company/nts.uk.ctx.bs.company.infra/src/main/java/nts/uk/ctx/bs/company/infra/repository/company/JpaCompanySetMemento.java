/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.infra.repository.company;

import nts.uk.ctx.bs.company.dom.company.CompanyCode;
import nts.uk.ctx.bs.company.dom.company.CompanyId;
import nts.uk.ctx.bs.company.dom.company.CompanyName;
import nts.uk.ctx.bs.company.dom.company.CompanySetMemento;
import nts.uk.ctx.bs.company.dom.company.StartMonth;
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtCompany;

/**
 * The Class JpaCompanySetMemento.
 */
public class JpaCompanySetMemento implements CompanySetMemento{
	
	/** The company. */
	private BcmmtCompany company;
	
	
	/**
	 * Instantiates a new jpa company set memento.
	 *
	 * @param company the company
	 */
	public JpaCompanySetMemento(BcmmtCompany company) {
		this.company = company;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.company.setCcd(companyCode.v());
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.company.setCid(companyId.v());
	}

	/**
	 * Sets the start month.
	 *
	 * @param startMonth the new start month
	 */
	@Override
	public void setStartMonth(StartMonth startMonth) {
		this.company.setStrM(startMonth.v());
	}

	@Override
	public void setCompanyName(CompanyName companyName) {
		this.company.setCompanyName(companyName.v());
	}

}
