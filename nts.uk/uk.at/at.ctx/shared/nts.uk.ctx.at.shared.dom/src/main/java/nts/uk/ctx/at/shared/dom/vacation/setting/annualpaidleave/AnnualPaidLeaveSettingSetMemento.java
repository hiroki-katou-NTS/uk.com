/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface AnnualPaidLeaveSettingSetMemento.
 */
public interface AnnualPaidLeaveSettingSetMemento {
    
    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);

    /**
     * Sets the year manage type.
     *
     * @param yearManageType the new year manage type
     */
    void setYearManageType(ManageDistinct yearManageType);

    /**
     * Sets the year manage setting.
     *
     * @param yearManageSetting the new year manage setting
     */
    void setYearManageSetting(ManageAnnualSetting yearManageSetting);
}
