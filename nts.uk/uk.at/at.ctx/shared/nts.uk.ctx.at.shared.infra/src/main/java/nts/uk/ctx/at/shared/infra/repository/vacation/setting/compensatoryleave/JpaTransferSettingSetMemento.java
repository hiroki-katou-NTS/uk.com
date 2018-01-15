/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;

/**
 * The Class JpaTransferSettingSetMemento.
 */
public class JpaTransferSettingSetMemento implements TransferSettingSetMemento {
    
    /** The entity. */
    private KocmtOccurrenceSet entity;
    
    /** The value true. */
    private static int VALUE_TRUE = 1;
    
    /** The value false. */
    private static int VALUE_FALSE = 0;
    
    /**
     * Instantiates a new jpa transfer setting set memento.
     *
     * @param entity the entity
     */
    public JpaTransferSettingSetMemento(KocmtOccurrenceSet entity) {
        this.entity = entity;
    }
    
    /**
     * Sets the certain time.
     *
     * @param certainTime the new certain time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingSetMemento#setCertainTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setCertainTime(OneDayTime certainTime) {
        if (certainTime != null) {
            this.entity.setCertainTime(certainTime.v().longValue());
        }
    }

    /**
     * Sets the use division.
     *
     * @param useDivision the new use division
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingSetMemento#setUseDivision(boolean)
     */
    @Override
    public void setUseDivision(boolean useDivision) {
        this.entity.setUseType(useDivision == true ? VALUE_TRUE : VALUE_FALSE);
    }

    /**
     * Sets the one day time.
     *
     * @param oneDayTime the new one day time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingSetMemento#setOneDayTime(nts.uk.ctx.at.shared.dom.vacation
     * .setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setOneDayTime(OneDayTime oneDayTime) {
        if (oneDayTime != null) {
            this.entity.setOneDayTime(oneDayTime.v().longValue());
        }
    }

    /**
     * Sets the half day time.
     *
     * @param halfDayTime the new half day time
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingSetMemento#setHalfDayTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setHalfDayTime(OneDayTime halfDayTime) {
        if (halfDayTime != null) {
            this.entity.setHalfDayTime(halfDayTime.v().longValue());
        }
    }

    /**
     * Sets the transfer division.
     *
     * @param transferDivision the new transfer division
     */
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * TransferSettingSetMemento#setTransferDivision(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.TransferSettingDivision)
     */
    @Override
    public void setTransferDivision(TransferSettingDivision transferDivision) {
        this.entity.setTransfType(transferDivision.value);
    }

}
