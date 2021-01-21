/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface MonthlyPatternGetMemento.
 */
public interface MonthlyPatternGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the monthly pattern code.
	 *
	 * @return the monthly pattern code
	 */
	public MonthlyPatternCode  getMonthlyPatternCode();
	
	
	/**
	 * Gets the monthly pattern name.
	 *
	 * @return the monthly pattern name
	 */
	public MonthlyPatternName getMonthlyPatternName();

	/**
	 * Gets the contract code.
	 *
	 * @return the contract code
	 */
	public String getContractCd();



}
