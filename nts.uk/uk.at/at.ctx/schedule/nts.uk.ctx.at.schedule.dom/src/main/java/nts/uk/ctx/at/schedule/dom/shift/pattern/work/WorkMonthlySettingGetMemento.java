/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public interface WorkMonthlySettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	public WorkTypeCode getWorkTypeCode();
	
	
	/**
	 * Gets the working code.
	 *
	 * @return the working code
	 */
	public WorkingCode getWorkingCode();
	
	
	/**
	 * Gets the ymdk.
	 *
	 * @return the ymdk
	 */
	public GeneralDate getYmdK();
	
	
	/**
	 * Gets the monthly pattern code.
	 *
	 * @return the monthly pattern code
	 */
	public MonthlyPatternCode getMonthlyPatternCode();
}
