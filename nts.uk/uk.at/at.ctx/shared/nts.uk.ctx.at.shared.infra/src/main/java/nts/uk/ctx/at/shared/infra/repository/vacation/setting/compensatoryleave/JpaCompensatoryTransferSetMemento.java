/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryTransferSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSetPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaCompensatoryTransferSetMemento.
 */
public class JpaCompensatoryTransferSetMemento implements CompensatoryTransferSetMemento {
	
    private static final Integer useValue = 1;
    private static final Integer notUseValue = 0;
    
    /** The entity. */
    @Inject
    private KmfmtOccurVacationSet entity;
    
    /**
     * Instantiates a new jpa compensatory transfer set memento.
     *
     * @param entity the entity
     */
    public JpaCompensatoryTransferSetMemento(KmfmtOccurVacationSet entity) {
        this.entity = entity;
    }
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferSetMemento#setCertainTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setCertainTime(OneDayTime certainTime) {
        this.entity.setCertainTime(certainTime.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferSetMemento#setUseDivision(boolean)
     */
    @Override
    public void setUseDivision(boolean useDivision) {
		this.entity.setUseDivision(useDivision == true?useValue:notUseValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferSetMemento#setOneDayTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setOneDayTime(OneDayTime oneDayTime) {
        this.entity.setOneDayTime(oneDayTime.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferSetMemento#setHalfDayTime(nts.uk.ctx.at.shared.dom.
     * vacation.setting.compensatoryleave.OneDayTime)
     */
    @Override
    public void setHalfDayTime(OneDayTime halfDayTime) {
        this.entity.setHalfDayTime(halfDayTime.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensatoryTransferSetMemento#setTransferDivision(nts.uk.ctx.at.shared.
     * dom.vacation.setting.compensatoryleave.TransferSettingDivision)
     */
    @Override
    public void setTransferDivision(TransferSettingDivision transferDivision) {
        this.entity.setTransfDivision(transferDivision.value);
    }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * CompensatoryTransferSetMemento#setCompensatoryOccurrenceDivision(nts.uk.
	 * ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * CompensatoryOccurrenceDivision)
	 */
	@Override
	public void setCompensatoryOccurrenceDivision(CompensatoryOccurrenceDivision compensatoryOccurrenceDivision) {
		this.entity.setKmfmtOccurVacationSetPK(new KmfmtOccurVacationSetPK(AppContexts.user().companyId(),compensatoryOccurrenceDivision.value));
	}

}
