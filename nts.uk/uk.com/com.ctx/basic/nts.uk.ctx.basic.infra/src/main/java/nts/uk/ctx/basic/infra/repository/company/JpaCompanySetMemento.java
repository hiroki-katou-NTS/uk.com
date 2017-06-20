/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company;

import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.company.CompanyId;
import nts.uk.ctx.basic.dom.company.CompanySetMemento;
import nts.uk.ctx.basic.dom.company.StartMonth;
import nts.uk.ctx.basic.infra.entity.company.CmnmtCompany;

/**
 * The Class JpaCompanySetMemento.
 */
public class JpaCompanySetMemento implements CompanySetMemento{
	
	/** The company. */
	private CmnmtCompany company;
	
	
	/**
	 * Instantiates a new jpa company set memento.
	 *
	 * @param company the company
	 */
	public JpaCompanySetMemento(CmnmtCompany company) {
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

}
