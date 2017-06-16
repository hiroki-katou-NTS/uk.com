/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaOccurrenceVacationGetMemento.
 */
public class JpaOccurrenceVacationGetMemento  {
    
    /** The entity. */
    private List<KmfmtOccurVacationSet> lstEntityOccur;
    
    /**
     * Instantiates a new jpa occurrence vacation get memento.
     *
     * @param entity the entity
     */
    public JpaOccurrenceVacationGetMemento(List<KmfmtOccurVacationSet> lstEntityOccur) {
        this.lstEntityOccur = lstEntityOccur;
    }
    
    /**
     * Gets the transfer setting.
     *
     * @return the transfer setting
     */

//	@Override
//	public CompensatoryTransferSetting getTransferSettingDayOffTime() {
//		return new CompensatoryTransferSetting(new JpaCompensatoryTransferGetMemento(findByType(CompensatoryOccurrenceDivision.WorkDayOffTime)));
//	}
//    
//    @Override
//    public CompensatoryTransferSetting getTransferSettingOverTime() {
//        return new CompensatoryTransferSetting(new JpaCompensatoryTransferGetMemento(findByType(CompensatoryOccurrenceDivision.FromOverTime)));
//    }

    private KmfmtOccurVacationSet findByType(CompensatoryOccurrenceDivision type) {
    	return lstEntityOccur.stream()
    			.filter(entity -> entity.getKmfmtOccurVacationSetPK().getOccurrDivision() == type.value)
    			.findFirst()
    			.get();
    }
    
}
