/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Interface HealthInsuranceRateMemento.
 */
public interface HealthInsuranceRateMemento {

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
	Long getMaxAmount();

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

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(String historyId);

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the office code.
	 *
	 * @param officeCode the new office code
	 */
	void setOfficeCode(OfficeCode officeCode);

	/**
	 * Sets the apply range.
	 *
	 * @param applyRange the new apply range
	 */
	void setApplyRange(MonthRange applyRange);

	/**
	 * Sets the auto calculate.
	 *
	 * @param autoCalculate the new auto calculate
	 */
	void setAutoCalculate(Boolean autoCalculate);

	/**
	 * Sets the max amount.
	 *
	 * @param maxAmount the new max amount
	 */
	void setMaxAmount(Long maxAmount);

	/**
	 * Sets the rate items.
	 *
	 * @param rateItems the new rate items
	 */
	void setRateItems(List<InsuranceRateItem> rateItems);

	/**
	 * Sets the rounding methods.
	 *
	 * @param roundingMethods the new rounding methods
	 */
	void setRoundingMethods(List<HealthInsuranceRounding> roundingMethods);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
