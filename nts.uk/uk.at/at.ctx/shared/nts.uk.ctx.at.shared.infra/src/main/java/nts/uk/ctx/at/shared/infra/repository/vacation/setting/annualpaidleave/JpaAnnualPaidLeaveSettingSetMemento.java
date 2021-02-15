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
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmamtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KtvmtTimeAnnualSet;

/**
 * The Class JpaAnnualPaidLeaveSettingSetMemento.
 */
public class JpaAnnualPaidLeaveSettingSetMemento implements AnnualPaidLeaveSettingSetMemento {
    
    /** The entity. */
    private KalmtAnnualPaidLeave entity;
    
    /**
     * Instantiates a new jpa annual paid leave setting get memento.
     *
     * @param entity the entity
     * @param entityAnnual the entity annual
     * @param entityTime the entity time
     */
    public JpaAnnualPaidLeaveSettingSetMemento(KalmtAnnualPaidLeave entity) {
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
        KmamtMngAnnualSet entityAnnual = this.entity.getKmamtMngAnnualSet();
        if (entityAnnual == null) {
            entityAnnual = new KmamtMngAnnualSet();
        }
        JpaManageAnnualSettingSetMemento memento = new JpaManageAnnualSettingSetMemento(entityAnnual);
        manageAnnualSetting.saveToMemento(memento);
        
        entityAnnual.setCid(this.entity.getCid());
        this.entity.setKmamtMngAnnualSet(entityAnnual);
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
        KtvmtTimeAnnualSet entityTime = this.entity.getKtvmtTimeVacationSet();
        if (entityTime == null) {
            entityTime = new KtvmtTimeAnnualSet();
        }
        JpaTimeAnnualSettingSetMemento memento = new JpaTimeAnnualSettingSetMemento(entityTime);
        timeSetting.saveToMemento(memento);
        
        entityTime.setCid(this.entity.getCid());
        this.entity.setKtvmtTimeVacationSet(entityTime);
    }
}
