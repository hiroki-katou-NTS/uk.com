/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import java.util.Optional;

/**
 * The Interface ComRegularLaborTimeRepository.
 */
public interface ComRegularLaborTimeRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(ComRegularLaborTime setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(ComRegularLaborTime setting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param year the year
	 */
	void remove(String companyId);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the optional
	 */
	Optional<ComRegularLaborTime> find(String companyId);

}
