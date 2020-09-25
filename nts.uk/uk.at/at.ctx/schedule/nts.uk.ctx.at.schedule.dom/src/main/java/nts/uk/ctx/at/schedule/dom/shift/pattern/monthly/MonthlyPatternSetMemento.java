/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

public interface MonthlyPatternSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the monthly pattern code.
	 *
	 * @param monthlyPatternCode the new monthly pattern code
	 */
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode);
	
	
	/**
	 * Sets the monthly pattern name.
	 *
	 * @param monthlyPatternName the new monthly pattern name
	 */
	public void setMonthlyPatternName(MonthlyPatternName monthlyPatternName);

	public void setContractCd();
}
