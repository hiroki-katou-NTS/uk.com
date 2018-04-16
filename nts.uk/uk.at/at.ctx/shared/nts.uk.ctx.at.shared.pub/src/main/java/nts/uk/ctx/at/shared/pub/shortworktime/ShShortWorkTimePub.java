/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.shortworktime;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface ShShortWorkTimePub.
 */
public interface ShShortWorkTimePub {

	/**
	 * Find short work time.
	 *
	 * @param empId the emp id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList72
	Optional<ShShortWorkTimeExport> findShortWorkTime(String empId, GeneralDate baseDate);
}
