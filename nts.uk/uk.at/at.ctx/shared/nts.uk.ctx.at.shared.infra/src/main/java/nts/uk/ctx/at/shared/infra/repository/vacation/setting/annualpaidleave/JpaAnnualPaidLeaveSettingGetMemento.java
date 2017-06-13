/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmamtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeVacationSet;

/**
 * The Class JpaAnnualPaidLeaveSettingGetMemento.
 */
public class JpaAnnualPaidLeaveSettingGetMemento implements AnnualPaidLeaveSettingGetMemento {
    
    /** The entity. */
    private KalmtAnnualPaidLeave entity;
    
    /** The entity annual. */
    private KmamtMngAnnualSet entityAnnual;
    
    /** The entity time. */
    private KtvmtTimeVacationSet entityTime;
    
    /**
     * Instantiates a new jpa annual paid leave setting get memento.
     *
     * @param entity the entity
     * @param entityAnnual the entity annual
     * @param entityTime the entity time
     */
    public JpaAnnualPaidLeaveSettingGetMemento(KalmtAnnualPaidLeave entity, KmamtMngAnnualSet entityAnnual,
            KtvmtTimeVacationSet entityTime) {
        this.entity = entity;
        this.entityAnnual = entityAnnual;
        this.entityTime = entityTime;
    }
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entity.getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getAcquisitionSetting()
     */
    @Override
    public AcquisitionSetting getAcquisitionSetting() {
        AcquisitionSetting setting = AcquisitionSetting.builder()
                .permitType(ApplyPermission.valueOf(this.entity.getPermitAtr()))
                .annualPriority(AnnualPriority.valueOf(this.entity.getPriorityType()))
                .build();
        return setting;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getYearManageType()
     */
    @Override
    public ManageDistinct getYearManageType() {
        return ManageDistinct.valueOf(this.entity.getManageAtr());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getManageAnnualSetting()
     */
    @Override
    public ManageAnnualSetting getManageAnnualSetting() {
        return new ManageAnnualSetting(new JpaManageAnnualSettingGetMemento(this.entityAnnual));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getTimeSetting()
     */
    @Override
    public TimeVacationSetting getTimeSetting() {
        return new TimeVacationSetting(new JpaTimeVacationSettingGetMemento(this.entityTime));
    }

}
