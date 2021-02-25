/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor;

import java.util.Optional;

/**
 * The Interface ComTransLaborTimeRepository.
 */
public interface DeforLaborTimeComRepo {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(DeforLaborTimeCom setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(DeforLaborTimeCom setting);

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
	Optional<DeforLaborTimeCom> find(String companyId);
}
