/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaOccurrenceVacationGetMemento.
 */
public class JpaOccurrenceVacationGetMemento implements OccurrenceVacationGetMemento {
    
    /** The entity. */
    @Inject
    private KmfmtOccurVacationSet entity;
    
    /**
     * Instantiates a new jpa occurrence vacation get memento.
     *
     * @param entity the entity
     */
    public JpaOccurrenceVacationGetMemento(KmfmtOccurVacationSet entity) {
        this.entity = entity;
    }
    
    /**
     * Gets the transfer setting.
     *
     * @return the transfer setting
     */
    /* (non-Javadoc)
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationGetMemento#getTransferSetting()
     */
    @Override
    public CompensatoryTransferSetting getTransferSetting() {
        return new CompensatoryTransferSetting(new JpaCompensatoryTransferGetMemento(this.entity));
    }

    /**
     * Gets the occurrence division.
     *
     * @return the occurrence division
     */
    /* (non-Javadoc)
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationGetMemento#getOccurrenceDivision()
     */
    @Override
    public CompensatoryOccurrenceDivision getOccurrenceDivision() {
        return CompensatoryOccurrenceDivision.valueOf(this.entity.getOccurrDivision());
    }

}
