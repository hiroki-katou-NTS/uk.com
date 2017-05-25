/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.PreemptionPermit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearVacationTimeMaxDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearVacationTimeUnit;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;

/**
 * The Class JpaManageAnnualSettingGetMemento.
 */
public class JpaManageAnnualSettingGetMemento implements ManageAnnualSettingGetMemento {

    /** The manage annual. */
    @Inject
    private KmfmtMngAnnualSet manageAnnual;

    /**
     * Instantiates a new jpa manage annual setting get memento.
     *
     * @param manageAnnual
     *            the manage annual
     */
    public JpaManageAnnualSettingGetMemento(KmfmtMngAnnualSet manageAnnual) {
        this.manageAnnual = manageAnnual;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.manageAnnual.getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingGetMemento#getRemainingNumberSetting()
     */
    @Override
    public RemainingNumberSetting getRemainingNumberSetting() {
        RemainingNumberSetting remain = new RemainingNumberSetting();
        remain.setWorkDayCalculate(ManageDistinct.valueOf(this.manageAnnual.getWorkDayCal()));
        HalfDayManage halfDayManage = HalfDayManage.builder()
                .manageType(ManageDistinct.valueOf(this.manageAnnual.getHalfDayMngAtr()))
                .reference(MaxDayReference.valueOf(this.manageAnnual.getMngReference()))
                .maxNumberUniformCompany(new AnnualNumberDay(this.manageAnnual.getCUniformMaxNumber())).build();
        remain.setHalfDayManage(halfDayManage);
        remain.setMaximumDayVacation(new AnnualLeaveGrantDate(this.manageAnnual.getMaxDayOneYear()));
        remain.setRemainingDayMaxNumber(this.manageAnnual.getRemainDayMaxNum());
        remain.setRetentionYear(new RetentionYear(this.manageAnnual.getRetentionYear()));
        return remain;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingGetMemento#getAcquisitionSetting()
     */
    @Override
    public AcquisitionVacationSetting getAcquisitionSetting() {
        return AcquisitionVacationSetting.builder()
                .yearVacationPriority(ApplyPermission.valueOf(this.manageAnnual.getYearVacaPriority()))
                .permitType(PreemptionPermit.valueOf(this.manageAnnual.getPermitType())).build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingGetMemento#getDisplaySetting()
     */
    @Override
    public DisplaySetting getDisplaySetting() {
        return DisplaySetting.builder()
                .remainingNumberDisplay(DisplayDivision.valueOf(this.manageAnnual.getRemainNumDisplay()))
                .nextGrantDayDisplay(DisplayDivision.valueOf(this.manageAnnual.getNextGrantDayDisplay())).build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
     * ManageAnnualSettingGetMemento#getTimeSetting()
     */
    @Override
    public TimeVacationSetting getTimeSetting() {
        TimeVacationSetting time = new TimeVacationSetting();
        time.setTimeManageType(ManageDistinct.valueOf(this.manageAnnual.getTimeMngAtr()));
        time.setTimeUnit(YearVacationTimeUnit.valueOf(this.manageAnnual.getTimeUnit()));
        YearVacationTimeMaxDay timeMaxDay = YearVacationTimeMaxDay.builder()
                .manageMaxDayVacation(ManageDistinct.valueOf(this.manageAnnual.getTimeMaxDayMng()))
                .reference(MaxDayReference.valueOf(this.manageAnnual.getTimeMngReference()))
                .maxTimeDay(new MaxTimeDay(this.manageAnnual.getTimeMngMaxDay())).build();
        time.setMaxDay(timeMaxDay);
        time.setEnoughTimeOneDay(this.manageAnnual.getEnoughOneDay() == 1 ? true : false);
        return time;
    }
}
