/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew;

import java.util.Optional;

/**
 * The Interface WkpDeforLaborWorkHourRepository.
 */
public interface WkpDeforLaborWorkHourRepository {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<WkpDeforLaborWorkHour> findByCidAndWkpId(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void add(WkpDeforLaborWorkHour wkpDeforLaborWorkHour);

	/**
	 * Update.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void update(WkpDeforLaborWorkHour wkpDeforLaborWorkHour);

	/**
	 * Delete.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void delete(WkpDeforLaborWorkHour wkpDeforLaborWorkHour);

}
