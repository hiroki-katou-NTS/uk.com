/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WkpNormalSettingRepository.
 */
public interface WkpNormalSettingRepository {

	/**
	 * Find by cid and wkp id and year.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<WkpNormalSetting> find(String cid, String wkpId, int year);

	/**
	 * Adds the.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void add(WkpNormalSetting wkpNormalSetting);

	/**
	 * Update.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void update(WkpNormalSetting wkpNormalSetting);

	/**
	 * Delete.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void remove(String cid, String wkpId, int year);
		

	/**
	 * Find list.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the list
	 */
	List<WkpNormalSetting> findList(String cid, String wkpId);
}
