/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComNormalSettingSetMemento.
 */
public interface ComNormalSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	void setYear(Year year);

	/**
	 * Sets the statutory setting.
	 *
	 * @param statutorySetting the new statutory setting
	 */
	void setStatutorySetting(List<MonthlyTime> statutorySetting);

}
