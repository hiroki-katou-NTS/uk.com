/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface WkpNormalSettingRepository.
 */
public interface WkpNormalSettingRepository {

	/**
	 * Find list by cid.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	List<WkpNormalSetting> findListByCid(String cid);

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpNormalSetting> findByCidAndWkpId(String cid, String wkpId);

	/**
	 * Find by cid and wkp id and year.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<WkpNormalSetting> findByCidAndWkpIdAndYear(String cid, String wkpId, Year year);

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
	void delete(WkpNormalSetting wkpNormalSetting);
}
