/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find.dto;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;

/**
 * The Class ManageAnnualSettingFindDto.
 */
public class ManageAnnualSettingFindDto implements ManageAnnualSettingSetMemento {

    /** The add attendance day. */
    public Integer addAttendanceDay;

    /** The max manage semi vacation. */
    public Integer maxManageSemiVacation;

    /** The max number semi vacation. */
    public Integer maxNumberSemiVacation;

    /** The max number company. */
    public BigDecimal maxNumberCompany;

    /** The max grant day. */
    public BigDecimal maxGrantDay;

    /** The max remaining day. */
    public Integer maxRemainingDay;

    /** The number year retain. */
    public Integer numberYearRetain;

    /** The preemption annual vacation. */
    public Integer preemptionAnnualVacation;

    /** The preemption year leave. */
    public Integer preemptionYearLeave;

    /** The remaining number display. */
    public Integer remainingNumberDisplay;

    /** The next grant day display. */
    public Integer nextGrantDayDisplay;

    /** The time manage type. */
    public Integer timeManageType;

    /** The time unit. */
    public Integer timeUnit;

    /** The manage max day vacation. */
    public Integer manageMaxDayVacation;

    /** The reference. */
    public Integer reference;

    /** The max time day. */
    public Integer maxTimeDay;

    /** The is enough time one day. */
    public Boolean isEnoughTimeOneDay;
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setRemainingNumberSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting)
     */
    @Override
    public void setRemainingNumberSetting(RemainingNumberSetting setting) {
        this.addAttendanceDay = setting.getWorkDayCalculate().value;
        this.maxManageSemiVacation = setting.getHalfDayManage().manageType.value;
        this.maxNumberSemiVacation = setting.getHalfDayManage().reference.value;
        this.maxNumberCompany = setting.getHalfDayManage().maxNumberUniformCompany.v();
        this.maxGrantDay = setting.getMaximumDayVacation().v();
        this.maxRemainingDay = setting.getRemainingDayMaxNumber();
        this.numberYearRetain = setting.getRetentionYear().v();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setAcquisitionSetting(nts.uk.ctx.at.shared.
     * dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting)
     */
    @Override
    public void setAcquisitionSetting(AcquisitionVacationSetting acquisition) {
        this.preemptionAnnualVacation = acquisition.yearVacationPriority.value;
        this.preemptionYearLeave = acquisition.permitType.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setDisplaySetting(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.DisplaySetting)
     */
    @Override
    public void setDisplaySetting(DisplaySetting displaySetting) {
        this.remainingNumberDisplay = displaySetting.remainingNumberDisplay.value;
        this.nextGrantDayDisplay = displaySetting.nextGrantDayDisplay.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingSetMemento#setTimeSetting(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.TimeVacationSetting)
     */
    @Override
    public void setTimeSetting(TimeVacationSetting timeSetting) {
        this.timeManageType = timeSetting.getTimeManageType().value;
        this.timeUnit = timeSetting.getTimeUnit().value;
        this.manageMaxDayVacation = timeSetting.getMaxDay().manageMaxDayVacation.value;
        this.reference = timeSetting.getMaxDay().reference.value;
        this.maxTimeDay = timeSetting.getMaxDay().maxTimeDay.v();
        this.isEnoughTimeOneDay = timeSetting.isEnoughTimeOneDay();
    }
}
