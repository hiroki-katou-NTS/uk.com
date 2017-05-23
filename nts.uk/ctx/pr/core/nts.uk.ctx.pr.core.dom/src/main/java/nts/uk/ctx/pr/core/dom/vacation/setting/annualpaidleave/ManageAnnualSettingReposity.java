/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

/**
 * The Interface YearVacationManageSettingReposity.
 */
public interface ManageAnnualSettingReposity {
    
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
     * Find.
     *
     * @param companyId the company id
     * @return the year vacation manage setting
     */
    ManageAnnualSetting find(String companyId);
}
