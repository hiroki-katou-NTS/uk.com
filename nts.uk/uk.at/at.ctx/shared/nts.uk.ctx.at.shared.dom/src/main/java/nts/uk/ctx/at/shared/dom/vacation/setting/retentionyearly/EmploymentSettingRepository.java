/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmploymentSettingRepository.
 */
public interface EmploymentSettingRepository {
	
	/**
	 * Insert.
	 *
	 * @param emptYearlyRetentionSetting the employment setting
	 */
	void insert(EmptYearlyRetentionSetting emptYearlyRetentionSetting);
	
	/**
	 * Update.
	 *
	 * @param emptYearlyRetentionSetting the employment setting
	 */
	void update(EmptYearlyRetentionSetting emptYearlyRetentionSetting);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 */
	void remove(String companyId, String employmentCode);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @return the employment setting
	 */
	Optional<EmptYearlyRetentionSetting> find(String companyId, String employmentCode);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<EmptYearlyRetentionSetting> findAll(String companyId);
}
