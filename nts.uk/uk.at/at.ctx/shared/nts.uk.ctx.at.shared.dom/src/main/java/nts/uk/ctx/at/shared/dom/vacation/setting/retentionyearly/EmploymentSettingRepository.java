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
	 * @param employmentSetting the employment setting
	 */
	void insert(EmploymentSetting employmentSetting);
	
	/**
	 * Update.
	 *
	 * @param employmentSetting the employment setting
	 */
	void update(EmploymentSetting employmentSetting);
	
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
	Optional<EmploymentSetting> find(String companyId, String employmentCode);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<EmploymentSetting> findAll(String companyId);
}
