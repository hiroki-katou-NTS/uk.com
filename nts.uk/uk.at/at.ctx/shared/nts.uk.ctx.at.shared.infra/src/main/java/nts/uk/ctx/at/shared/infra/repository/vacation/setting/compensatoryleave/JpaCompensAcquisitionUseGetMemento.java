/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TermManagement;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionEmp;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaCompensAcquisitionUseGetMemento.
 */
public class JpaCompensAcquisitionUseGetMemento implements CompensatoryAcquisitionUseGetMemento {
    
    /** The entity com. */
    private KclmtAcquisitionCom entityCom;
    
    /** The entity emp. */
    private KclmtAcquisitionEmp entityEmp;
    
    /**
     * Instantiates a new jpa compens acquisition use get memento.
     *
     * @param obj the obj
     */
    public JpaCompensAcquisitionUseGetMemento(Object obj) {
        this.initObject(obj);
    }
    
    /**
     * Inits the object.
     *
     * @param obj the obj
     */
    private void initObject(Object obj) {
        boolean isCorrect = false;
        if (obj instanceof KclmtAcquisitionCom) {
            this.entityCom = (KclmtAcquisitionCom) obj;
            isCorrect = true;
        }
        else if (obj instanceof KclmtAcquisitionEmp) {
            this.entityEmp = (KclmtAcquisitionEmp) obj;
            isCorrect = true;
        }
        if (!isCorrect) {
            throw new RuntimeException("Object incorrect.");
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryAcquisitionUseGetMemento#getExpirationTime()
     */
    @Override
    public ExpirationTime getExpirationTime() {
        return ExpirationTime.valueOf(
                this.entityCom == null ? this.entityEmp.getExpTime() : this.entityCom.getExpTime());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryAcquisitionUseGetMemento#getPreemptionPermit()
     */
    @Override
    public ApplyPermission getPreemptionPermit() {
        return ApplyPermission.valueOf(
                this.entityCom == null ? this.entityEmp.getPreempPermitAtr() : this.entityCom.getPreempPermitAtr());
    }

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseGetMemento#getDeadlCheckMonth()
	 */
	@Override
	public DeadlCheckMonth getDeadlCheckMonth() {
		return DeadlCheckMonth.valueOf(this.entityCom == null ? this.entityEmp.getDeadlCheckMonth() : this.entityCom.getDeadlCheckMonth());
	}

	@Override
	public TermManagement termManagement() {
		// TODO Auto-generated method stub
		return null;
	}

}
