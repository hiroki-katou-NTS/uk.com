/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.sixtyhours.find;

import java.util.List;

import nts.uk.ctx.at.shared.app.vacation.setting.sixtyhours.find.dto.Emp60HourVacationDto;
import nts.uk.ctx.at.shared.app.vacation.setting.sixtyhours.find.dto.SixtyHourVacationSettingDto;

/**
 * The Interface 60HourVacationFinder.
 */
public interface SixtyHourVacationFinder {

	/**
	 * Find com setting.
	 *
	 * @return the subst vacation setting dto
	 */
	SixtyHourVacationSettingDto findComSetting();

	/**
	 * Find emp setting.
	 *
	 * @param companyId the company id
	 * @param contractTypeCode the contract type code
	 * @return the emp subst vacation dto
	 */
	Emp60HourVacationDto findEmpSetting(String companyId, String contractTypeCode);
	
	/**
	 * Find all employment.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<String> findAllEmployment();
}
