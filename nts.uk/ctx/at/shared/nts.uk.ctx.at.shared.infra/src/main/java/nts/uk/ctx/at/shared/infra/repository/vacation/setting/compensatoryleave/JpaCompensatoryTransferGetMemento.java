/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaCompensatoryTransferGetMemento.
 */
public class JpaCompensatoryTransferGetMemento implements CompensatoryTransferGetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtOccurVacationSet entity;
    
    /**
     * Instantiates a new jpa compensatory transfer get memento.
     *
     * @param entity the entity
     */
    public JpaCompensatoryTransferGetMemento(KmfmtOccurVacationSet entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferGetMemento#getCertainTime()
     */
    @Override
    public OneDayTime getCertainTime() {
        return new OneDayTime(Long.valueOf(this.entity.getCertainTime()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferGetMemento#getUseDivision()
     */
    @Override
    public boolean getUseDivision() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferGetMemento#getOneDayTime()
     */
    @Override
    public OneDayTime getOneDayTime() {
        return new OneDayTime(Long.valueOf(this.entity.getOneDayTime()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferGetMemento#getHalfDayTime()
     */
    @Override
    public OneDayTime getHalfDayTime() {
        return new OneDayTime(Long.valueOf(this.entity.getHalfDayTime()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferGetMemento#getTransferDivision()
     */
    @Override
    public TransferSettingDivision getTransferDivision() {
        return TransferSettingDivision.valueOf(this.entity.getTransfDivision());
    }
}
