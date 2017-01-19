/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceMemento;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaUnitPriceMemento implements UnitPriceMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitPriceCode getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitPriceName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(UnitPriceCode code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(UnitPriceName name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub

	}

}
