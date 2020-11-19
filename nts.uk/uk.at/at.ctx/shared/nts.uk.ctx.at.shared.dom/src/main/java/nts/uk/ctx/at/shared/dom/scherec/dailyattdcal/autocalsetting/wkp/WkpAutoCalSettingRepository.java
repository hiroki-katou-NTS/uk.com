/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp;


import java.util.List;
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
     * Add.
     *
     * @param wkpAutoCalSetting the wkp auto cal setting
     */
    void add(WkpAutoCalSetting wkpAutoCalSetting);


	/**
	 * Gets the wkp auto cal setting.
	 *
	 * @param companyId the company id
	 * @param wkpId the wkp id
	 * @return the wkp auto cal setting
	 */
    Optional<WkpAutoCalSetting> getWkpAutoCalSetting(String companyId, String wkpId);
    
    /**
     * Gets the all wkp auto cal setting.
     *
     * @param companyId the company id
     * @return the all wkp auto cal setting
     */
    List<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId);
    
	/**
	 * Delete.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 */
    void delete(String cid, String wkpId);

}
