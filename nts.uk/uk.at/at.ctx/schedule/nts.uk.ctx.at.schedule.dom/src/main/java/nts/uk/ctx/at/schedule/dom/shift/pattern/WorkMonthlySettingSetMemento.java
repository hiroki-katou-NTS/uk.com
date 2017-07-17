/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;

/**
 * The Interface WorkMonthlySettingSetMemento.
 */
public interface WorkMonthlySettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the work monthly setting code.
	 *
	 * @param workMonthlySettingCode the new work monthly setting code
	 */
	public void setWorkMonthlySettingCode(WorkMonthlySettingCode workMonthlySettingCode);
	
	
	/**
	 * Sets the sift code.
	 *
	 * @param siftCode the new sift code
	 */
	public void setSiftCode(SiftCode siftCode);
	
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(GeneralDate date);
	
	
	/**
	 * Sets the monthly pattern code.
	 *
	 * @param monthlyPatternCode the new monthly pattern code
	 */
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode);

}
