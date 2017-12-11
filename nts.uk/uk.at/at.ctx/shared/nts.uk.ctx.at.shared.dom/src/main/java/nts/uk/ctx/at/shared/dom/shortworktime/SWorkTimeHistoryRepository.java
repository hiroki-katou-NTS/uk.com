/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface SWorkTimeHistoryRepository.
 */
public interface SWorkTimeHistoryRepository {

	/**
	 * Find by key.
	 *
	 * @param empId the emp id
	 * @param histId the hist id
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistory> findByKey(String empId, String histId);

	/**
	 * Find by base date.
	 *
	 * @param empId the emp id
	 * @param baseDate the base date
	 * @return the optional
	 */
	Optional<ShortWorkTimeHistory> findByBaseDate(String empId, GeneralDate baseDate);
}
