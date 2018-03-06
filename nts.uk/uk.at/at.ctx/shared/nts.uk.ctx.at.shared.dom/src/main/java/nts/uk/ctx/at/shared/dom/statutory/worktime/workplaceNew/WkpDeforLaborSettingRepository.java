/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface WkpDeforLaborSettingRepository.
 */
public interface WkpDeforLaborSettingRepository {

	/**
	 * Find by cid and wkp id and year.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<WkpDeforLaborSetting> findByCidAndWkpIdAndYear(String cid, String wkpId, Year year);

	/**
	 * Adds the.
	 *
	 * @param wkpDeforLaborSetting the wkp defor labor setting
	 */
	void add(WkpDeforLaborSetting wkpDeforLaborSetting);

	/**
	 * Update.
	 *
	 * @param wkpDeforLaborSetting the wkp defor labor setting
	 */
	void update(WkpDeforLaborSetting wkpDeforLaborSetting);

	/**
	 * Delete.
	 *
	 * @param wkpDeforLaborSetting the wkp defor labor setting
	 */
	void delete(WkpDeforLaborSetting wkpDeforLaborSetting);

}
