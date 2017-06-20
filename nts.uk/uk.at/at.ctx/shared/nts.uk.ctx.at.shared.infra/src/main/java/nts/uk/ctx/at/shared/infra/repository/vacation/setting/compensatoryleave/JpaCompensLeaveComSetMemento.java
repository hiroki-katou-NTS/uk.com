/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaCompensLeaveComSetMemento.
 */
public class JpaCompensLeaveComSetMemento implements CompensatoryLeaveComSetMemento {
    
    /** The entity. */
    private KclmtCompensLeaveCom entity;
    
    /**
     * Instantiates a new jpa compens leave com set memento.
     *
     * @param entity the entity
     */
    public JpaCompensLeaveComSetMemento(KclmtCompensLeaveCom entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setIsManaged(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setIsManaged(ManageDistinct managed) {
        this.entity.setManageAtr(managed.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setCompensatoryAcquisitionUse(nts.uk.ctx.
     * at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryAcquisitionUse)
     */
    @Override
    public void setCompensatoryAcquisitionUse(CompensatoryAcquisitionUse compensatoryAcquisitionUse) {
        KclmtAcquisitionCom entityAcquisition = new KclmtAcquisitionCom();
        JpaCompensAcquisitionUseSetMemento memento = new JpaCompensAcquisitionUseSetMemento(entityAcquisition);
        compensatoryAcquisitionUse.saveToMemento(memento);
        
        entityAcquisition.setCid(this.entity.getCid());
        
        this.entity.setKclmtAcquisitionCom(entityAcquisition);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setCompensatoryDigestiveTimeUnit(nts.uk.
     * ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryDigestiveTimeUnit)
     */
    @Override
    public void setCompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
        KctmtDigestTimeCom entityDigestTime = new KctmtDigestTimeCom();
        JpaCompensDigestiveTimeUnitSetMemento memento = new JpaCompensDigestiveTimeUnitSetMemento(entityDigestTime);
        compensatoryDigestiveTimeUnit.saveToMemento(memento);
        
        entityDigestTime.setCid(this.entity.getCid());
        
        this.entity.setKctmtDigestTimeCom(entityDigestTime);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveComSetMemento#setCompensatoryOccurrenceSetting(java.util
     * .List)
     */
    @Override
    public void setCompensatoryOccurrenceSetting(List<CompensatoryOccurrenceSetting> occurrenceSetting) {
        List<KocmtOccurrenceSet> listEntity = new ArrayList<>();
        for (CompensatoryOccurrenceSetting setting : occurrenceSetting) {
            KocmtOccurrenceSet entity = new KocmtOccurrenceSet();
            JpaCompensOccurrenceSettingSetMemento memento = new JpaCompensOccurrenceSettingSetMemento(
                    this.entity.getCid(), entity);
            setting.saveToMemento(memento);
            
            listEntity.add(entity);
        }
        this.entity.setListOccurrence(listEntity);
    }

}
