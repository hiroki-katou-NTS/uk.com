/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.List;

/**
 * The Interface CompensLeaveEmSetRepository.
 */
public interface CompensLeaveEmSetRepository {
	
	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	void insert(CompensatoryLeaveEmSetting setting);
	
	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(CompensatoryLeaveEmSetting setting);
	
	/**
	 * Delete.
	 *
	 * @param setting the setting
	 */
	void delete(String companyId, String employmentCode);
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @return the compensatory leave em setting
	 */
	CompensatoryLeaveEmSetting find(String companyId, String employmentCode);
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<CompensatoryLeaveEmSetting> findAll(String companyId);
}
