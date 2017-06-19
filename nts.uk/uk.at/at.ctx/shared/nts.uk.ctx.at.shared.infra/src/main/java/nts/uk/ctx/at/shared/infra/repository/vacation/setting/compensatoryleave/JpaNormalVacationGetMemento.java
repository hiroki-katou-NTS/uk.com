/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtNormalVacationSet;

/**
 * The Class JpaNormalVacationGetMemento.
 */
public class JpaNormalVacationGetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtNormalVacationSet entity;
    
    /**
     * Instantiates a new jpa normal vacation get memento.
     *
     * @param entity the entity
     */
    public JpaNormalVacationGetMemento(KmfmtNormalVacationSet entity) {
        this.entity = entity;
    }
    
//    /*
//     * (non-Javadoc)
//     * 
//     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
//     * NormalVacationGetMemento#getExpirationTime()
//     */
//    @Override
//    public ExpirationTime getExpirationTime() {
//        return ExpirationTime.valueOf(this.entity.getExpireTime());
//    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationGetMemento#getPreemptionPermit()
     */
//    @Override
//    public ApplyPermission getPreemptionPermit() {
//        return ApplyPermission.valueOf(this.entity.getPreempPermit());
//    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationGetMemento#getIsManageByTime()
     */
//    @Override
//    public ManageDistinct getIsManageByTime() {
//        return ManageDistinct.valueOf(this.entity.getIsMngTime());
//    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * NormalVacationGetMemento#getdigestiveUnit()
     */
//    @Override
//    public TimeVacationDigestiveUnit getdigestiveUnit() {
//        return TimeVacationDigestiveUnit.valueOf(this.entity.getDigestiveUnit());
//    }

}
