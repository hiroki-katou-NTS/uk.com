/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * The Interface YearVacationManageSettingSetMemento.
 */
public interface ManageAnnualSettingSetMemento {
    
    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    void setCompanyId(String companyId);
    
    /**
     * Sets the remaining number setting.
     *
     * @param remainingNumberSetting the new remaining number setting
     */
    void setRemainingNumberSetting(RemainingNumberSetting remainingNumberSetting);

    /**
     * Sets the acquisition setting.
     *
     * @param acquisitionSetting the new acquisition setting
     */
    void setAcquisitionSetting(AcquisitionVacationSetting acquisitionSetting);

    /**
     * Sets the display setting.
     *
     * @param displaySetting the new display setting
     */
    void setDisplaySetting(DisplaySetting displaySetting);

    /**
     * Sets the time setting.
     *
     * @param timeSetting the new time setting
     */
    void setTimeSetting(TimeVacationSetting timeSetting);
}
