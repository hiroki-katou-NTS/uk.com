/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;

public interface WorkMonthlySettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the work monthly setting code.
	 *
	 * @return the work monthly setting code
	 */
	public WorkMonthlySettingCode getWorkMonthlySettingCode();
	
	
	/**
	 * Gets the sift code.
	 *
	 * @return the sift code
	 */
	public SiftCode getSiftCode();
	
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public GeneralDate getDate();
	
	
	/**
	 * Gets the monthly pattern code.
	 *
	 * @return the monthly pattern code
	 */
	public MonthlyPatternCode getMonthlyPatternCode();
}
