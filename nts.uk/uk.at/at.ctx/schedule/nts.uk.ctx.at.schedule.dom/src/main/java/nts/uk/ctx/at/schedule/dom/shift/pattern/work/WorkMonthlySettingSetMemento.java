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
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	public void setWorkTypeCode(WorkTypeCode workTypeCode);

	/**
	 * Sets the working code.
	 *
	 * @param workingCode the new working code
	 */
	public void setWorkingCode(WorkingCode workingCode);

	/**
	 * Sets the ymd K.
	 *
	 * @param ymdk the new ymd K
	 */
	public void setYmdK(GeneralDate ymdk);

	/**
	 * Sets the monthly pattern code.
	 *
	 * @param monthlyPatternCode the new monthly pattern code
	 */
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode);

}
