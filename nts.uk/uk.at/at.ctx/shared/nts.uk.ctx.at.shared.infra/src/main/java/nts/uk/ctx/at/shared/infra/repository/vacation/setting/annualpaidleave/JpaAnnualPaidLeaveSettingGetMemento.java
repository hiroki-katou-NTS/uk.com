/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtHdpaidSet;

/**
 * The Class JpaAnnualPaidLeaveSettingGetMemento.
 */
public class JpaAnnualPaidLeaveSettingGetMemento implements AnnualPaidLeaveSettingGetMemento {
    
    /** The entity. */
    private KshmtHdpaidSet entity;
    
    /**
     * Instantiates a new jpa annual paid leave setting get memento.
     *
     * @param entity the entity
     */
    public JpaAnnualPaidLeaveSettingGetMemento(KshmtHdpaidSet entity) {
        this.entity = entity;
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
        return new ManageAnnualSetting(new JpaManageAnnualSettingGetMemento(this.entity.getKshmtHdpaidSetMng()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingGetMemento#getTimeSetting()
     */
    @Override
    public TimeAnnualSetting getTimeSetting() {
        return new TimeAnnualSetting(new JpaTimeAnnualSettingGetMemento(this.entity.getKtvmtTimeVacationSet()));
    }
}
