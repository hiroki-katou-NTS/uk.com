/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

/**
 * The Interface NursingVacationSettingRepository.
 */
public interface NursingVacationSettingRepository {
    
    /**
     * Adds the.
     *
     * @param setting the setting
     */
    void add(NursingVacationSetting setting);
    
    /**
     * Update.
     *
     * @param setting the setting
     */
    void update(NursingVacationSetting setting);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the nursing vacation setting
     */
    NursingVacationSetting findByCompanyId(String companyId);
}
