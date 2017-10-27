/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.subst;

import java.util.List;

import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;

/**
 * The Interface SubstVacationFinder.
 */
public interface SubstVacationFinder {

	/**
	 * Find com setting.
	 *
	 * @param companyId the company id
	 * @return the subst vacation setting dto
	 */
	SubstVacationSettingDto findComSetting(String companyId);

	/**
	 * Find emp setting.
	 *
	 * @param companyId the company id
	 * @param contractTypeCode the contract type code
	 * @return the emp subst vacation dto
	 */
	EmpSubstVacationDto findEmpSetting(String companyId, String contractTypeCode);
	
	/**
	 * Find all employment.
	 *
	 * @return the list
	 */
	List<String> findAllEmployment();
}
