/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface AnnualVacationSettingSetMemento.
 */
public interface AnnualPaidLeaveSettingSetMemento {
    
    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);

    /**
     * Sets the acquisition setting.
     *
     * @param acquisitionSetting the new acquisition setting
     */
    void setAcquisitionSetting(AcquisitionSetting acquisitionSetting);

    /**
     * Sets the year manage type.
     *
     * @param yearManageType the new year manage type
     */
    void setYearManageType(ManageDistinct yearManageType);

    /**
     * Sets the manage annual setting.
     *
     * @param manageAnnualSetting the new manage annual setting
     */
    void setManageAnnualSetting(ManageAnnualSetting manageAnnualSetting);

    /**
     * Sets the time setting.
     *
     * @param timeSetting the new time setting
     */
    void setTimeSetting(TimeAnnualSetting timeSetting);
}
