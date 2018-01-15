/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.workplace;

import java.util.List;
import java.util.Optional;

/**
 * The Interface WorkPlaceWtSettingRepository.
 */
public interface WorkPlaceWtSettingRepository {

	/**
	 * Creates the.
	 *
	 * @param setting the setting
	 */
	void create(WorkPlaceWtSetting setting);

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(WorkPlaceWtSetting setting);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @param workPlaceId the work place id
	 */
	void remove(String companyId, int year, String workPlaceId);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @param workPlaceId the work place id
	 * @return the optional
	 */
	Optional<WorkPlaceWtSetting> find(String companyId, int year, String workPlaceId);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @param year the year
	 * @return the list
	 */
	List<String> findAll(String companyId, int year);
}
