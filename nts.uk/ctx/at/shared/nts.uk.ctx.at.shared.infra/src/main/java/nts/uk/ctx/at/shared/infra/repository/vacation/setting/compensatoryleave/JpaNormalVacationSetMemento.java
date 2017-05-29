/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtNormalVacationSet;

/**
 * The Class JpaNormalVacationSetMemento.
 */
public class JpaNormalVacationSetMemento implements NormalVacationSetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtNormalVacationSet entity;
    
    /**
     * Instantiates a new jpa normal vacation set memento.
     *
     * @param entity the entity
     */
    public JpaNormalVacationSetMemento(KmfmtNormalVacationSet entity) {
        this.entity = entity;
    }
    
    /**
     * Sets the expiration time.
     *
     * @param expirationTime the new expiration time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationSetMemento#setExpirationTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.ExpirationTime)
     */
    @Override
    public void setExpirationTime(ExpirationTime expirationTime) {
        this.entity.setExpireTime(expirationTime.value);
    }

    /**
     * Sets the preemption permit.
     *
     * @param preemptionPermit the new preemption permit
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationSetMemento#setPreemptionPermit(java.lang.Boolean)
     */
    @Override
    public void setPreemptionPermit(Boolean preemptionPermit) {
        this.entity.setPreempPermit(preemptionPermit == true ? 1 : 0);
    }

    /**
     * Sets the checks if is manage by time.
     *
     * @param isManageByTime the new checks if is manage by time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationSetMemento#setIsManageByTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setIsManageByTime(ManageDistinct isManageByTime) {
        this.entity.setIsMngTime(isManageByTime.value);
    }

    /**
     * Sets the digestive unit.
     *
     * @param digestiveUnit the new digestive unit
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationSetMemento#setDigestiveUnit(nts.uk.ctx.at.shared.dom.
     * vacation.setting.TimeVacationDigestiveUnit)
     */
    @Override
    public void setDigestiveUnit(TimeVacationDigestiveUnit digestiveUnit) {
        this.entity.setDigestiveUnit(digestiveUnit.value);
    }

}
