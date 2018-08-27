/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeEmpPK;

/**
 * The Class JpaCompensLeaveEmSettingSetMemento.
 */
public class JpaCompensLeaveEmSettingSetMemento implements CompensatoryLeaveEmSettingSetMemento {
    
    /** The entity. */
    private KclmtCompensLeaveEmp entity;
    
    /**
     * Instantiates a new jpa compens leave em setting set memento.
     *
     * @param entity the entity
     */
    public JpaCompensLeaveEmSettingSetMemento(KclmtCompensLeaveEmp entity) {
        // check exist primary key
        if (entity.getKclmtCompensLeaveEmpPK() == null) {
            entity.setKclmtCompensLeaveEmpPK(new KclmtCompensLeaveEmpPK());
        }
        this.entity = entity;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveEmSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.getKclmtCompensLeaveEmpPK().setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveEmSettingSetMemento#setEmploymentCode(nts.uk.ctx.at.
     * shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
     */
    @Override
    public void setEmploymentCode(EmploymentCode employmentCode) {
        this.entity.getKclmtCompensLeaveEmpPK().setEmpcd(employmentCode.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveEmSettingSetMemento#setIsManaged(nts.uk.ctx.at.shared.
     * dom.vacation.setting.ManageDistinct)
     */
    @Override
    public void setIsManaged(ManageDistinct isManaged) {
        this.entity.setManageAtr(isManaged.value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveEmSettingSetMemento#setCompensatoryAcquisitionUse(nts.uk
     * .ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryAcquisitionUse)
     */
    @Override
    public void setCompensatoryAcquisitionUse(CompensatoryAcquisitionUse compensatoryAcquisitionUse) {
        KclmtAcquisitionEmp entityAcquisition = this.entity.getKclmtAcquisitionEmp();
        if (entityAcquisition == null) {
            entityAcquisition = new KclmtAcquisitionEmp();
        }
        JpaCompensAcquisitionUseSetMemento memento = new JpaCompensAcquisitionUseSetMemento(entityAcquisition);
        compensatoryAcquisitionUse.saveToMemento(memento);
        
        KclmtAcquisitionEmpPK pk = new KclmtAcquisitionEmpPK();
        pk.setCid(this.entity.getKclmtCompensLeaveEmpPK().getCid());
        pk.setEmpcd(this.entity.getKclmtCompensLeaveEmpPK().getEmpcd());
        entity.setDeadlCheckMonth(0);
        entityAcquisition.setKclmtAcquisitionEmpPK(pk);
        
        this.entity.setKclmtAcquisitionEmp(entityAcquisition);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryLeaveEmSettingSetMemento#setCompensatoryDigestiveTimeUnit(nts
     * .uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryDigestiveTimeUnit)
     */
    @Override
    public void setCompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
        KctmtDigestTimeEmp entityDigestTime = this.entity.getKctmtDigestTimeEmp();
        if (entityDigestTime == null) {
            entityDigestTime = new KctmtDigestTimeEmp();
        }
        JpaCompensDigestiveTimeUnitSetMemento memento = new JpaCompensDigestiveTimeUnitSetMemento(entityDigestTime);
        compensatoryDigestiveTimeUnit.saveToMemento(memento);
        
        KctmtDigestTimeEmpPK pk = new KctmtDigestTimeEmpPK();
        pk.setCid(this.entity.getKclmtCompensLeaveEmpPK().getCid());
        pk.setEmpcd(this.entity.getKclmtCompensLeaveEmpPK().getEmpcd());
        
        entityDigestTime.setKctmtDigestTimeEmpPK(pk);
        
        this.entity.setKctmtDigestTimeEmp(entityDigestTime);
    }

}
