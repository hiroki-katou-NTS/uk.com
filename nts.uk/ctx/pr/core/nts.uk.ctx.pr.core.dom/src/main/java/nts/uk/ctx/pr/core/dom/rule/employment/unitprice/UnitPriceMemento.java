/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface UnitPriceMemento.
 */
public interface UnitPriceMemento {

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

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(UnitPriceCode code);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(UnitPriceName name);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
