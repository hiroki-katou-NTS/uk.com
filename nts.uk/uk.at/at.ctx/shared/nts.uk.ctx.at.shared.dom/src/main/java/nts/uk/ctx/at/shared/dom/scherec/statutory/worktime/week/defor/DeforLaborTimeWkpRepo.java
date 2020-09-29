/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import java.util.Optional;

/**
 * The Interface WkpDeforLaborWorkHourRepository.
 */
public interface DeforLaborTimeWkpRepo {

	/**
	 * Find by cid and wkp id.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return the optional
	 */
	Optional<DeforLaborTimeWkp> find(String cid, String wkpId);

	/**
	 * Adds the.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void add(DeforLaborTimeWkp wkpDeforLaborWorkHour);

	/**
	 * Update.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void update(DeforLaborTimeWkp wkpDeforLaborWorkHour);

	/**
	 * Delete.
	 *
	 * @param wkpDeforLaborWorkHour the wkp defor labor work hour
	 */
	void remove(String cid, String wkpId);

}
