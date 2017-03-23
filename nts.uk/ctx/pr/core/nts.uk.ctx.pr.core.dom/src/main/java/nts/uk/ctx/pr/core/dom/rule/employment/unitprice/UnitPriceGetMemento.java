/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface UnitPriceMemento.
 */
public interface UnitPriceGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	UnitPriceCode getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	UnitPriceName getName();

}
