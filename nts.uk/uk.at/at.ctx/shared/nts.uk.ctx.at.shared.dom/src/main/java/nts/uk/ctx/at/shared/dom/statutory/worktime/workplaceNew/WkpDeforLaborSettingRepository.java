/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.Optional;

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
	Optional<WkpDeforLaborSetting> find(String cid, String wkpId, int year);

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
	void remove(String cid, String wkpId, int year);

}
