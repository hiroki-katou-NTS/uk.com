/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular;

import java.util.Optional;

/**
 * The Interface ComRegularLaborTimeRepository.
 */
public interface RegularLaborTimeComRepo {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(RegularLaborTimeCom setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(RegularLaborTimeCom setting);

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
	Optional<RegularLaborTimeCom> find(String companyId);

}
