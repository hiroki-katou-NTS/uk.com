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
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeEmp;

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
        KclmtCompensLeaveEmpPK pk = this.entity.getKclmtCompensLeaveEmpPK();
        if (pk == null) {
            pk = new KclmtCompensLeaveEmpPK();
        }
        pk.setCid(companyId);
        this.entity.setKclmtCompensLeaveEmpPK(pk);
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
        KclmtCompensLeaveEmpPK pk = this.entity.getKclmtCompensLeaveEmpPK();
        if (pk == null) {
            pk = new KclmtCompensLeaveEmpPK();
        }
        pk.setEmpcd(employmentCode.v());
        this.entity.setKclmtCompensLeaveEmpPK(pk);
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
        KclmtAcquisitionEmp entityAcquisition = new KclmtAcquisitionEmp();
        JpaCompensAcquisitionUseSetMemento memento = new JpaCompensAcquisitionUseSetMemento(entityAcquisition);
        compensatoryAcquisitionUse.saveToMemento(memento);
        
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
        KctmtDigestTimeEmp entityDigestTime = new KctmtDigestTimeEmp();
        JpaCompensDigestiveTimeUnitSetMemento memento = new JpaCompensDigestiveTimeUnitSetMemento(entityDigestTime);
        compensatoryDigestiveTimeUnit.saveToMemento(memento);
        
        this.entity.setKctmtDigestTimeEmp(entityDigestTime);
    }

}
