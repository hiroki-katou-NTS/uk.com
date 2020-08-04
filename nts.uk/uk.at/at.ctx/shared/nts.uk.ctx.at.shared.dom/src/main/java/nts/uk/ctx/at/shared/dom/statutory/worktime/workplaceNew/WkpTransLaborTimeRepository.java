/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.Optional;

/**
 * The Interface WkpDeforLaborWorkHourRepository.
 */
public interface WkpTransLaborTimeRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpTransLaborTime> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void add(WkpTransLaborTime wkpDeforLaborWorkHour);

	/**
	 * Update.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void update(WkpTransLaborTime wkpDeforLaborWorkHour);

	/**
	 * Delete.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void remove(String cid, String wkpId);

}
