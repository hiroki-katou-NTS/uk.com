/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Interface AnnualVacationSettingRepository.
 */
public interface AnnualPaidLeaveSettingRepository {
    
    /**
     * Adds the.
     *
     * @param setting the setting
     */
    void add(AnnualPaidLeaveSetting setting);
    
    /**
     * Update.
     *
     * @param setting the setting
     */
    void update(AnnualPaidLeaveSetting setting);
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the annual vacation setting
     */
    AnnualPaidLeaveSetting findByCompanyId(String companyId);
}
