/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

/**
 * The Interface YearVacationManageSettingGetMemento.
 */
public interface ManageAnnualSettingGetMemento {
    
    /**
     * Gets the company id.
     *
     * @return the company id
     */
    String getCompanyId();
    
    /**
     * Gets the remaining number setting.
     *
     * @return the remaining number setting
     */
    RemainingNumberSetting getRemainingNumberSetting();

    /**
     * Gets the acquisition setting.
     *
     * @return the acquisition setting
     */
    AcquisitionVacationSetting getAcquisitionSetting();

    /**
     * Gets the display setting.
     *
     * @return the display setting
     */
    DisplaySetting getDisplaySetting();

    /**
     * Gets the time setting.
     *
     * @return the time setting
     */
    TimeVacationSetting getTimeSetting();
}
