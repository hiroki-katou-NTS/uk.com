/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

/**
 * The Interface RetentionYearlySettingRepository.
 */
public interface RetentionYearlySettingRepository {
	
	/**
	 * Insert.
	 *
	 * @param setting the setting
	 */
	void insert(RetentionYearlySetting setting);
	
	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(RetentionYearlySetting setting);
	
	/**
	 * Removes the.
	 *
	 * @param setting the setting
	 */
	void remove(RetentionYearlySetting setting);
	
	/**
	 * Find by company id.
	 *
	 * @return the retention yearly setting
	 */
	RetentionYearlySetting findByCompanyId();
}
