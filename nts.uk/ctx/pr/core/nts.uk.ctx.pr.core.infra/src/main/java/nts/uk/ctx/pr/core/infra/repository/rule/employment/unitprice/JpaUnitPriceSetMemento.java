/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceSetMemento;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHead;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHeadPK;

/**
 * The Class JpaUnitPriceSetMemento.
 */
public class JpaUnitPriceSetMemento implements UnitPriceSetMemento {

	/** The type value. */
	protected QupmtCUnitpriceHead typeValue;

	/**
	 * Instantiates a new jpa unit price set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceSetMemento(QupmtCUnitpriceHead typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceSetMemento#
	 * setCompanyCode(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QupmtCUnitpriceHeadPK qupmtCUnitpriceHeadPK = new QupmtCUnitpriceHeadPK();
		qupmtCUnitpriceHeadPK.setCcd(companyCode.v());
		this.typeValue.setQupmtCUnitpriceHeadPK(qupmtCUnitpriceHeadPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceSetMemento#
	 * setCode(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode)
	 */
	@Override
	public void setCode(UnitPriceCode code) {
		QupmtCUnitpriceHeadPK qupmtCUnitpriceHeadPK = this.typeValue.getQupmtCUnitpriceHeadPK();
		qupmtCUnitpriceHeadPK.setCUnitpriceCd(code.v());
		this.typeValue.setQupmtCUnitpriceHeadPK(qupmtCUnitpriceHeadPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceSetMemento#
	 * setName(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName)
	 */
	@Override
	public void setName(UnitPriceName name) {
		this.typeValue.setCUnitpriceName(name.v());
	}

}
