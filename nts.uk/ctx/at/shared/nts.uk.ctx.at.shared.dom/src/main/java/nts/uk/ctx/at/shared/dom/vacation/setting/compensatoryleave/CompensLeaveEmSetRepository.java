/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

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
	 * Find.
	 *
	 * @param companyId the company id
	 * @return the compensatory leave em setting
	 */
	CompensatoryLeaveEmSetting find(String companyId);
}
