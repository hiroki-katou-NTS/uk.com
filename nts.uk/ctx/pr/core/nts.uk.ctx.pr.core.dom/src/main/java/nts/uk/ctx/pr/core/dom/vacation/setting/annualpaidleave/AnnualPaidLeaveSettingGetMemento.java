/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Interface AnnualPaidLeaveSettingGetMemento.
 */
public interface AnnualPaidLeaveSettingGetMemento {
    
    /**
     * Gets the company id.
     *
     * @return the company id
     */
    String getCompanyId();

    /**
     * Gets the year manage type.
     *
     * @return the year manage type
     */
    ManageDistinct getYearManageType();

    /**
     * Gets the year manage setting.
     *
     * @return the year manage setting
     */
    ManageAnnualSetting getYearManageSetting();
}
