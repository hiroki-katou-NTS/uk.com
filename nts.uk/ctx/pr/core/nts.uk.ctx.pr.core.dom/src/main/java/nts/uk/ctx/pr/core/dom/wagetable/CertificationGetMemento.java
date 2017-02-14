/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Interface UnitPriceMemento.
 */
public interface CertificationGetMemento {

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
	String getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

}
