/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.businesstype;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;

/**
 * The Interface InsuranceBusinessTypeMemento.
 */
public interface InsuranceBusinessTypeMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the biz order.
	 *
	 * @return the biz order
	 */
	BusinessTypeEnum getBizOrder();

	/**
	 * Gets the biz name.
	 *
	 * @return the biz name
	 */
	BusinessName getBizName();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Gets the biz order.
	 *
	 * @return the biz order
	 */
	void setBizOrder(BusinessTypeEnum businessTypeEnum );

	/**
	 * Gets the biz name.
	 *
	 * @return the biz name
	 */
	void setBizName(BusinessName name);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
