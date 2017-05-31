/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;

/**
 * The Class AnnualPaidLeaveSettingFindDto.
 */
public class AnnualPaidLeaveSettingFindDto implements AnnualPaidLeaveSettingSetMemento {

    /** The annual manage. */
    public Integer annualManage;

    /** The manage setting. */
    public ManageAnnualSettingFindDto setting;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setYearManageType(nts.uk.ctx.at.shared.
     * dom.vacation.setting.ManageDistinct)
     */
    @Override
    public void setYearManageType(ManageDistinct yearManageType) {
        this.annualManage = yearManageType.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setYearManageSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting)
     */
    @Override
    public void setYearManageSetting(ManageAnnualSetting yearManageSetting) {
        ManageAnnualSettingFindDto setting = new ManageAnnualSettingFindDto();
        yearManageSetting.saveToMemento(setting);
        this.setting = setting;
    }

}
