/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.beginningmonth;

import lombok.Data;
import nts.uk.ctx.basic.dom.company.CompanyId;
import nts.uk.ctx.basic.dom.company.StartMonth;
import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento;

/**
 * The Class BeginningMonthDto.
 */
@Data
public class BeginningMonthDto implements BeginningMonthSetMemento {

	/** The company id. */
	private String companyId;

	/** The start month. */
	private Integer startMonth;

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
	public void setMonth(StartMonth startMonth) {
		this.startMonth = startMonth.v();
	}

}
