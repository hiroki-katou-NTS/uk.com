/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company;

import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.company.CompanyGetMemento;
import nts.uk.ctx.basic.dom.company.CompanyId;
import nts.uk.ctx.basic.dom.company.StartMonth;
import nts.uk.ctx.basic.infra.entity.company.CmnmtCompany;

/**
 * The Class JpaCompanyGetMemento.
 */
public class JpaCompanyGetMemento implements CompanyGetMemento {
	
	/** The company. */
	private CmnmtCompany company;

	public JpaCompanyGetMemento(CmnmtCompany company) {
		this.company = company;
	}
	
	
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.company.getCcd());
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.company.getCid());
	}

	/**
	 * Gets the start month.
	 *
	 * @return the start month
	 */
	@Override
	public StartMonth getStartMonth() {
		return new StartMonth(this.company.getStrM());
	}

}
