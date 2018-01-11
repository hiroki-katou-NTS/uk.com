/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.beginningmonth;

import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento;
import nts.uk.ctx.basic.infra.entity.company.beginningmonth.CbmstBeginningMonth;
import nts.uk.ctx.bs.company.dom.company.CompanyId;
import nts.uk.ctx.bs.company.dom.company.StartMonth;

/**
 * The Class JpaBeginningMonthSetMemento.
 */
public class JpaBeginningMonthSetMemento implements BeginningMonthSetMemento {

	/** The entity. */
	private CbmstBeginningMonth entity;

	/**
	 * Instantiates a new jpa beginning month set memento.
	 *
	 * @param entity the entity
	 */
	public JpaBeginningMonthSetMemento(CbmstBeginningMonth entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento#setCompanyId(nts.uk.ctx.bs.company.dom.company.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthSetMemento#setMonth(nts.uk.ctx.bs.company.dom.company.StartMonth)
	 */
	@Override
	public void setMonth(StartMonth startMonth) {
		this.entity.setMonth(startMonth.v());
	}
}
