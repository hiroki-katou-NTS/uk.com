/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Interface HealthInsuranceRateMemento.
 */
public interface HealthInsuranceRateGetMemento {

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	String getHistoryId();

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the office code.
	 *
	 * @return the office code
	 */
	OfficeCode getOfficeCode();

	/**
	 * Gets the apply range.
	 *
	 * @return the apply range
	 */
	MonthRange getApplyRange();

	/**
	 * Gets the auto calculate.
	 *
	 * @return the auto calculate
	 */
	Boolean getAutoCalculate();

	/**
	 * Gets the max amount.
	 *
	 * @return the max amount
	 */
	CommonAmount getMaxAmount();

	/**
	 * Gets the rate items.
	 *
	 * @return the rate items
	 */
	List<InsuranceRateItem> getRateItems();

	/**
	 * Gets the rounding methods.
	 *
	 * @return the rounding methods
	 */
	List<HealthInsuranceRounding> getRoundingMethods();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

}
