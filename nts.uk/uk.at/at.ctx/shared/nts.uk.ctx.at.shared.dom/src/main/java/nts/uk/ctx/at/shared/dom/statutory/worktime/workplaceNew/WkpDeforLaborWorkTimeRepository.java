/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.Optional;

/**
 * The Interface WkpDeforLaborWorkHourRepository.
 */
public interface WkpDeforLaborWorkTimeRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpDeforLaborWorkTime> findByCidAndWkpId(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void add(WkpDeforLaborWorkTime wkpDeforLaborWorkHour);

	/**
	 * Update.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void update(WkpDeforLaborWorkTime wkpDeforLaborWorkHour);

	/**
	 * Delete.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void delete(WkpDeforLaborWorkTime wkpDeforLaborWorkHour);

}
