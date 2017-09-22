/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import java.util.Optional;

/**
 * The Interface WkpAutoCalSettingRepository.
 */
public interface WkpAutoCalSettingRepository {
	
	/**
	 * Update.
	 *
	 * @param wkpAutoCalSetting the wkp auto cal setting
	 */
    void update(WkpAutoCalSetting wkpAutoCalSetting);

	/**
	 * Gets the all wkp auto cal setting.
	 *
	 * @param companyId the company id
	 * @param wkpId the wkp id
	 * @return the all wkp auto cal setting
	 */
    Optional<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId, String wkpId);
    
	/**
     * Deleted.
     *
     * @param cid the cid
     * @param patternCd the pattern cd
     */
    void delete(String cid, String wkpId);

}
