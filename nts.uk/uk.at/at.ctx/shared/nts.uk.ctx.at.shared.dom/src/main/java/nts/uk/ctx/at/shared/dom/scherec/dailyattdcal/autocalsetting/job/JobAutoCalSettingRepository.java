/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job;

import java.util.List;
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
	 * Add.
	 *
	 * @param jobAutoCalSetting the job auto cal setting
	 */
    void add(JobAutoCalSetting jobAutoCalSetting);


	/**
	 * Gets the job auto cal setting.
	 *
	 * @param companyId the company id
	 * @param jobId the job id
	 * @return the job auto cal setting
	 */
    Optional<JobAutoCalSetting> getJobAutoCalSetting(String companyId, String jobId);
    
    
    /**
     * Gets the all job auto cal setting.
     *
     * @param companyId the company id
     * @return the all job auto cal setting
     */
    List<JobAutoCalSetting> getAllJobAutoCalSetting(String companyId);
    
	/**
	 * Delete.
	 *
	 * @param cid the cid
	 * @param jobCd the job cd
	 */
    void delete(String cid, String jobCd);

}
