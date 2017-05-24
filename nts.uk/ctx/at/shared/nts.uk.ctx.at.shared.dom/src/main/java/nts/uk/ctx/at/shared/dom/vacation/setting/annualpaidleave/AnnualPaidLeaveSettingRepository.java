/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.util.Optional;

/**
 * The Interface AnnualPaidLeaveSettingRepository.
 */
public interface AnnualPaidLeaveSettingRepository {
    
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
     * @return the annual paid leave setting
     */
    Optional<AnnualPaidLeaveSetting> findByCompanyId(String companyId);
}
