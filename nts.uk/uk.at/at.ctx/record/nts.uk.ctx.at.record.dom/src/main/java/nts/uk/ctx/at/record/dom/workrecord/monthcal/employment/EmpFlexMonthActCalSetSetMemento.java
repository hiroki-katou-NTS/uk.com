/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface EmploymentCalMonthlyFlexSetMemento.
 */
public interface EmpFlexMonthActCalSetSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	void setEmploymentCode(EmploymentCode employmentCode);

	/**
	 * Sets the aggr setting monthly of flx new.
	 *
	 * @param aggrSettingMonthlyOfFlxNew the new aggr setting monthly of flx new
	 */
	void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew);

}
