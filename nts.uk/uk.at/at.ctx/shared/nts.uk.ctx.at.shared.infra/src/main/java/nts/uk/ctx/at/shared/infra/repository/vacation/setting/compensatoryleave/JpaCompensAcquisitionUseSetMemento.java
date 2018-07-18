/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionEmp;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaCompensAcquisitionUseSetMemento.
 */
public class JpaCompensAcquisitionUseSetMemento implements CompensatoryAcquisitionUseSetMemento {
    
    /** The entity com. */
    private KclmtAcquisitionCom entityCom;
    
    /** The entity emp. */
    private KclmtAcquisitionEmp entityEmp;
    
    /**
     * Instantiates a new jpa compens acquisition use set memento.
     *
     * @param obj the obj
     */
    public JpaCompensAcquisitionUseSetMemento(Object obj) {
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
     * CompensatoryAcquisitionUseSetMemento#setExpirationTime(nts.uk.ctx.at.
     * shared.dom.vacation.setting.ExpirationTime)
     */
    @Override
    public void setExpirationTime(ExpirationTime expirationTime) {
        if (this.entityCom != null) {
            this.entityCom.setExpTime(expirationTime.value);
        } else {
            this.entityEmp.setExpTime(expirationTime.value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryAcquisitionUseSetMemento#setPreemptionPermit(nts.uk.ctx.at.
     * shared.dom.vacation.setting.ApplyPermission)
     */
    @Override
    public void setPreemptionPermit(ApplyPermission preemptionPermit) {
        if (this.entityCom != null) {
            this.entityCom.setPreempPermitAtr(preemptionPermit.value);
        } else {
            this.entityEmp.setPreempPermitAtr(preemptionPermit.value);
        }
    }

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseSetMemento#setDeadlCheckMonth(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth)
	 */
	@Override
	public void setDeadlCheckMonth(DeadlCheckMonth deadlCheckMonth) {
		if (this.entityCom != null) {
			this.entityCom.setDeadlCheckMonth(deadlCheckMonth.value);
		} else {
			this.entityEmp.setDeadlCheckMonth(deadlCheckMonth.value);
		}
	}

}
