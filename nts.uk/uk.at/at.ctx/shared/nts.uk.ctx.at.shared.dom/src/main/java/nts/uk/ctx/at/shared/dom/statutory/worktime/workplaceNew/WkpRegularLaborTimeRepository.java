/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WkpNormalWorkingHourRepository.
 */
public interface WkpRegularLaborTimeRepository {

	/**
	 * Find by cid and wkp id and year.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<WkpRegularLaborTime> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void add(WkpRegularLaborTime domain);

	/**
	 * Update.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void update(WkpRegularLaborTime domain);

	/**
	 * Delete.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void remove(String cid, String wkpId);
	
	/**
	 * Find all.
	 *
	 * @param cid the cid
	 * @return the list
	 */
	List<WkpRegularLaborTime> findAll(String cid);
}
