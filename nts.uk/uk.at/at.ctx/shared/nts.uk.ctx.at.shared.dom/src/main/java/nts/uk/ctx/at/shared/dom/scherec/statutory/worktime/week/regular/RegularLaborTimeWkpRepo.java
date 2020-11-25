/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WkpNormalWorkingHourRepository.
 */
public interface RegularLaborTimeWkpRepo {

	/**
	 * Find by cid and wkp id and year.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param year the year
	 * @return the optional
	 */
	Optional<RegularLaborTimeWkp> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void add(RegularLaborTimeWkp domain);

	/**
	 * Update.
	 *
	 * @param wkpNormalSetting the wkp normal setting
	 */
	void update(RegularLaborTimeWkp domain);

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
	List<RegularLaborTimeWkp> findAll(String cid);
}
