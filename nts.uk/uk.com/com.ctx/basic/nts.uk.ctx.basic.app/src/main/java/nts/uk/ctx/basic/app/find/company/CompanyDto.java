/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.company.CompanyId;
import nts.uk.ctx.basic.dom.company.CompanySetMemento;
import nts.uk.ctx.basic.dom.company.StartMonth;

/**
 * The Class CompanyDto.
 */
@Getter
@Setter
public class CompanyDto implements CompanySetMemento {

	/** The company id. */
	private String companyId;

	/** The company code. */
	private String companyCode;

	/** The start month. */
	private Integer startMonth;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.CompanySetMemento#setCompanyCode(nts.uk.ctx.
	 * basic.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.companyCode = companyCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.CompanySetMemento#setCompanyId(nts.uk.ctx.
	 * basic.dom.company.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.CompanySetMemento#setStartMonth(nts.uk.ctx.
	 * basic.dom.company.StartMonth)
	 */
	@Override
	public void setStartMonth(StartMonth startMonth) {
		this.startMonth = startMonth.v();
	}

}
