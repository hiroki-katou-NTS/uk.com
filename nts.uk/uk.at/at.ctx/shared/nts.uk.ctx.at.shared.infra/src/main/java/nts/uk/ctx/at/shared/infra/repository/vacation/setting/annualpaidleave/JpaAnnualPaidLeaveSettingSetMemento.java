/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtHdpaidSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtHdpaidSetMng;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshmtHdpaidTimeSet;

/**
 * The Class JpaAnnualPaidLeaveSettingSetMemento.
 */
public class JpaAnnualPaidLeaveSettingSetMemento implements AnnualPaidLeaveSettingSetMemento {
    
    /** The entity. */
    private KshmtHdpaidSet entity;
    
    /**
     * Instantiates a new jpa annual paid leave setting get memento.
     *
     * @param entity the entity
     * @param entityAnnual the entity annual
     * @param entityTime the entity time
     */
    public JpaAnnualPaidLeaveSettingSetMemento(KshmtHdpaidSet entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setAcquisitionSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting)
     */
    @Override
    public void setAcquisitionSetting(AcquisitionSetting acquisitionSetting) {
        this.entity.setPriorityType(acquisitionSetting.annualPriority.value);
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
        this.entity.setManageAtr(yearManageType.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setManageAnnualSetting(nts.uk.ctx.at.
     * shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting)
     */
    @Override
    public void setManageAnnualSetting(ManageAnnualSetting manageAnnualSetting) {
        KshmtHdpaidSetMng entityAnnual = this.entity.getKshmtHdpaidSetMng();
        if (entityAnnual == null) {
            entityAnnual = new KshmtHdpaidSetMng();
        }
        JpaManageAnnualSettingSetMemento memento = new JpaManageAnnualSettingSetMemento(entityAnnual);
        manageAnnualSetting.saveToMemento(memento);
        
        entityAnnual.setCid(this.entity.getCid());
        this.entity.setKshmtHdpaidSetMng(entityAnnual);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
     * AnnualPaidLeaveSettingSetMemento#setTimeSetting(nts.uk.ctx.at.shared.dom.
     * vacation.setting.annualpaidleave.TimeVacationSetting)
     */
    @Override
    public void setTimeSetting(TimeAnnualSetting timeSetting) {
        KshmtHdpaidTimeSet entityTime = this.entity.getKtvmtTimeVacationSet();
        if (entityTime == null) {
            entityTime = new KshmtHdpaidTimeSet();
        }
        JpaTimeAnnualSettingSetMemento memento = new JpaTimeAnnualSettingSetMemento(entityTime);
        timeSetting.saveToMemento(memento);
        
        entityTime.setCid(this.entity.getCid());
        this.entity.setKtvmtTimeVacationSet(entityTime);
    }
}
