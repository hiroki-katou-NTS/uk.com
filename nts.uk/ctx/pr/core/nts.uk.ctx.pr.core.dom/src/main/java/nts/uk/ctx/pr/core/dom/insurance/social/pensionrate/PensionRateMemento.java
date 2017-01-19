/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Interface PensionRateMemento.
 */
public interface PensionRateMemento {

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
	 * Gets the max amount.
	 *
	 * @return the max amount
	 */
	Long getMaxAmount();

	/**
	 * Gets the fund rate items.
	 *
	 * @return the fund rate items
	 */
	List<FundRateItem> getFundRateItems();

	/**
	 * Gets the premium rate items.
	 *
	 * @return the premium rate items
	 */
	List<PensionPremiumRateItem> getPremiumRateItems();

	/**
	 * Gets the child contribution rate.
	 *
	 * @return the child contribution rate
	 */
	Double getChildContributionRate();

	/**
	 * Gets the rounding methods.
	 *
	 * @return the rounding methods
	 */
	List<PensionRateRounding> getRoundingMethods();

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
	 * Sets the max amount.
	 *
	 * @param maxAmount the new max amount
	 */
	void setMaxAmount(Long maxAmount);

	/**
	 * Sets the fund rate items.
	 *
	 * @param fundRateItems the new fund rate items
	 */
	void setFundRateItems(List<FundRateItem> fundRateItems);

	/**
	 * Sets the premium rate items.
	 *
	 * @param premiumRateItems the new premium rate items
	 */
	void setPremiumRateItems(List<PensionPremiumRateItem> premiumRateItems);

	/**
	 * Sets the child contribution rate.
	 *
	 * @param childContributionRate the new child contribution rate
	 */
	void setChildContributionRate(Double childContributionRate);

	/**
	 * Sets the rounding methods.
	 *
	 * @param roundingMethods the new rounding methods
	 */
	void setRoundingMethods(List<PensionRateRounding> roundingMethods);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
