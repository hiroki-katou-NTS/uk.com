/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComDeformationLaborSettingRepository.
 */
public interface ComDeformationLaborSettingRepository {

	/**
	 * Gets the com deformation labor setting by cid and year.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the com deformation labor setting by cid and year
	 */
	Optional<ComDeformationLaborSetting> getComDeformationLaborSettingByCidAndYear(String companyId, Year year);

}
