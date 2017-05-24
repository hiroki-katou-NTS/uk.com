/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Interface YearVacationManageSettingReposity.
 */
public interface ManageAnnualSettingRepository {
    
    /**
     * Adds the.
     *
     * @param setting the setting
     */
    void add(ManageAnnualSetting setting);
    
    /**
     * Update.
     *
     * @param setting the setting
     */
    void update(ManageAnnualSetting setting);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the manage annual setting
     */
    ManageAnnualSetting findByCompanyId(String companyId);
}
