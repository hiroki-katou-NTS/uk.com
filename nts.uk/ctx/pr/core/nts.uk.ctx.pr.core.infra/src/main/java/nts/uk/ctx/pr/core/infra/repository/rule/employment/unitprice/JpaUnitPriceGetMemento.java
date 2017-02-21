/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceGetMemento;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHead;

/**
 * The Class JpaUnitPriceGetMemento.
 */
public class JpaUnitPriceGetMemento implements UnitPriceGetMemento {

	/** The type value. */
	protected QupmtCUnitpriceHead typeValue;

	/**
	 * Instantiates a new jpa unit price get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceGetMemento(QupmtCUnitpriceHead typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typeValue.getQupmtCUnitpriceHeadPK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceGetMemento#
	 * getCode()
	 */
	@Override
	public UnitPriceCode getCode() {
		return new UnitPriceCode(this.typeValue.getQupmtCUnitpriceHeadPK().getCUnitpriceCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceGetMemento#
	 * getName()
	 */
	@Override
	public UnitPriceName getName() {
		return new UnitPriceName(this.typeValue.getCUnitpriceName());
	}
}
