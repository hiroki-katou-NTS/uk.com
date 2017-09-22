/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import java.util.Optional;

/**
 * The Interface JobAutoCalSettingRepository.
 */
public interface JobAutoCalSettingRepository {
	
	/**
	 * Update.
	 *
	 * @param jobAutoCalSetting the job auto cal setting
	 */
    void update(JobAutoCalSetting jobAutoCalSetting);

	/**
	 * Gets the all job auto cal setting.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @return the all job auto cal setting
	 */
    Optional<JobAutoCalSetting> getAllJobAutoCalSetting(String companyId, String jobId);
    
	/**
	 * Delete.
	 *
	 * @param cid the cid
	 * @param jobCd the job cd
	 */
    void delete(String cid, String jobCd);

}
