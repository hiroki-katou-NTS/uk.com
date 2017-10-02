/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob;

import java.util.Optional;

/**
 * The Interface WkpJobAutoCalSettingRepository.
 */
public interface WkpJobAutoCalSettingRepository {
	
	/**
	 * Update.
	 *
	 * @param wkpJobAutoCalSetting the wkp job auto cal setting
	 */
    void update(WkpJobAutoCalSetting wkpJobAutoCalSetting);
    
    /**
     * Add.
     *
     * @param wkpJobAutoCalSetting the wkp job auto cal setting
     */
    void add(WkpJobAutoCalSetting wkpJobAutoCalSetting);

	/**
	 * Gets the all wkp job auto cal setting.
	 *
	 * @param companyId the company id
	 * @param wkpId the wkp id
	 * @param jobId the job id
	 * @return the all wkp job auto cal setting
	 */
    Optional<WkpJobAutoCalSetting> getAllWkpJobAutoCalSetting(String companyId,String wkpId,String jobId);
    
	/**
	 * Delete.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @param jobId the job id
	 */
    void delete(String cid, String wkpId, String jobId);

}
