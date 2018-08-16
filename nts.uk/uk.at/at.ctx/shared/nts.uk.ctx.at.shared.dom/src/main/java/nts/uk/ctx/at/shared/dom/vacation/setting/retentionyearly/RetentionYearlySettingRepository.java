/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import java.util.Optional;

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
	 * Find by company id.
	 *
	 * @return the retention yearly setting
	 */
	Optional<RetentionYearlySetting> findByCompanyId(String companyId);
	
	/**
	 * Copy master data.
	 *
	 * @param sourceCid the source cid
	 * @param targetCid the target cid
	 * @param isReplace the is replace
	 */
	void copyMasterData(String sourceCid, String targetCid, boolean isReplace);
}
