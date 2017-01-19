/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.Set;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Interface AccidentInsuranceRateMemento.
 */
public interface AccidentInsuranceRateMemento {

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
	 * Gets the apply range.
	 *
	 * @return the apply range
	 */
	MonthRange getApplyRange();

	/**
	 * Gets the rate items.
	 *
	 * @return the rate items
	 */
	Set<InsuBizRateItem> getRateItems();

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
	 * Sets the apply range.
	 *
	 * @param applyRange the new apply range
	 */
	void setApplyRange(MonthRange applyRange);

	/**
	 * Sets the rate items.
	 *
	 * @param items the new rate items
	 */
	void setRateItems(Set<InsuBizRateItem> items);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
