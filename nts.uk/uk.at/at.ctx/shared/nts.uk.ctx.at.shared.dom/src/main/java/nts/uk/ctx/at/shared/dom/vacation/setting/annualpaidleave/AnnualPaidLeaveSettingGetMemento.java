/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface AnnualVacationSettingGetMemento.
 */
public interface AnnualPaidLeaveSettingGetMemento {
    
    /**
     * Gets the company id.
     *
     * @return the company id
     */
    String getCompanyId();

    /**
     * Gets the acquisition setting.
     *
     * @return the acquisition setting
     */
    AcquisitionSetting getAcquisitionSetting();

    /**
     * Gets the year manage type.
     *
     * @return the year manage type
     */
    ManageDistinct getYearManageType();

    /**
     * Gets the manage annual setting.
     *
     * @return the manage annual setting
     */
    ManageAnnualSetting getManageAnnualSetting();

    /**
     * Gets the time setting.
     *
     * @return the time setting
     */
    TimeAnnualSetting getTimeSetting();
}
