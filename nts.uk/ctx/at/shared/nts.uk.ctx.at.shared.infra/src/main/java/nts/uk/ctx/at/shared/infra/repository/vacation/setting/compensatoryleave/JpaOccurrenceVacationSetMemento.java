/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaOccurrenceVacationSetMemento.
 */
public class JpaOccurrenceVacationSetMemento implements OccurrenceVacationSetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtOccurVacationSet entity;
    
    /**
     * Instantiates a new jpa occurrence vacation set memento.
     *
     * @param entity the entity
     */
    public JpaOccurrenceVacationSetMemento(KmfmtOccurVacationSet entity) {
        this.entity = entity;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * OccurrenceVacationSetMemento#setTransferSetting(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.CompensatoryTransferSetting)
     */
    @Override
    public void setTransferSetting(CompensatoryTransferSetting transferSetting) {
        JpaCompensatoryTransferSetMemento memento = new JpaCompensatoryTransferSetMemento(this.entity);
        transferSetting.saveToMemento(memento);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * OccurrenceVacationSetMemento#setOccurrenceDivision(nts.uk.ctx.at.shared.
     * dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision)
     */
    @Override
    public void setOccurrenceDivision(CompensatoryOccurrenceDivision occurrenceDivision) {
        this.entity.setOccurrDivision(occurrenceDivision.value);
    }

}
