/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.List;

/**
 * The Interface NursingVacationSettingRepository.
 */
public interface NursingVacationSettingRepository {
    
    /**
     * Adds the.
     *
     * @param settings the settings
     */
    void add(List<NursingVacationSetting> settings);
    
    /**
     * Update.
     *
     * @param setting the setting
     */
    void update(List<NursingVacationSetting> settings);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the list
     */
    List<NursingVacationSetting> findByCompanyId(String companyId);
}
