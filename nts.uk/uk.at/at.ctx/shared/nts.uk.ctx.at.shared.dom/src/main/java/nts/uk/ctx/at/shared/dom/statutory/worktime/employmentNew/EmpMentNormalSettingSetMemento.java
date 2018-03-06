/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.MonthlyTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface EmpMentNormalSettingSetMemento.
 */
public interface EmpMentNormalSettingSetMemento {

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
