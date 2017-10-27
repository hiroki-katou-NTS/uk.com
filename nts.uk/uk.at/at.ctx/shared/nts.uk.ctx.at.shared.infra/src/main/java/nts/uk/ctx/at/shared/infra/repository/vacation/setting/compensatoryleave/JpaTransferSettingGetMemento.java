/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaTransferSettingGetMemento.
 */
public class JpaTransferSettingGetMemento implements TransferSettingGetMemento {
    
    /** The true. */
    private static int TRUE = 1;
    
    /** The entity. */
    private KocmtOccurrenceSet entity;
    
    /**
     * Instantiates a new jpa transfer setting get memento.
     *
     * @param entity the entity
     */
    public JpaTransferSettingGetMemento(KocmtOccurrenceSet entity) {
        this.entity = entity;
    }
    
    /**
     * Gets the certain time.
     *
     * @return the certain time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingGetMemento#getCertainTime()
     */
    @Override
    public OneDayTime getCertainTime() {
        return new OneDayTime(this.entity.getCertainTime().intValue());
    }

    /**
     * Checks if is use division.
     *
     * @return true, if is use division
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingGetMemento#isUseDivision()
     */
    @Override
    public boolean isUseDivision() {
        return this.entity.getUseType() == TRUE ? true : false;
    }

    /**
     * Gets the one day time.
     *
     * @return the one day time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingGetMemento#getOneDayTime()
     */
    @Override
    public OneDayTime getOneDayTime() {
        return new OneDayTime(this.entity.getOneDayTime().intValue());
    }

    /**
     * Gets the half day time.
     *
     * @return the half day time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingGetMemento#getHalfDayTime()
     */
    @Override
    public OneDayTime getHalfDayTime() {
        return new OneDayTime(this.entity.getHalfDayTime().intValue());
    }

    /**
     * Gets the transfer division.
     *
     * @return the transfer division
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingGetMemento#getTransferDivision()
     */
    @Override
    public TransferSettingDivision getTransferDivision() {
        return TransferSettingDivision.valueOf(this.entity.getTransfType());
    }

}
