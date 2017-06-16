/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtOccurVacationSet;

/**
 * The Class JpaOccurrenceVacationSetMemento.
 */
public class JpaOccurrenceVacationSetMemento {

	/** The entity. */
	@Inject
	private KmfmtOccurVacationSet overTimeEntity;
	
	/** The entity. */
	@Inject
	private KmfmtOccurVacationSet dayOffTimeEntity;
	/**
	 * Instantiates a new jpa occurrence vacation set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOccurrenceVacationSetMemento(KmfmtOccurVacationSet overTimeEntity,KmfmtOccurVacationSet dayOffTimeEntity) {
		this.overTimeEntity = overTimeEntity;
		this.dayOffTimeEntity = dayOffTimeEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * OccurrenceVacationSetMemento#setTransferSettingOverTime(nts.uk.ctx.at.
	 * shared.dom.vacation.setting.compensatoryleave.
	 * CompensatoryTransferSetting)
	 */
//	@Override
//	public void setTransferSettingOverTime(CompensatoryTransferSetting transferSettingOverTime) {
//		JpaCompensatoryTransferSetMemento memento = new JpaCompensatoryTransferSetMemento(this.overTimeEntity);
//		transferSettingOverTime.saveToMemento(memento);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
	 * OccurrenceVacationSetMemento#setTransferSettingDayOffTime(nts.uk.ctx.at.
	 * shared.dom.vacation.setting.compensatoryleave.
	 * CompensatoryTransferSetting)
	 */
//	@Override
//	public void setTransferSettingDayOffTime(CompensatoryTransferSetting transferSettingDayOffTime) {
//		JpaCompensatoryTransferSetMemento memento = new JpaCompensatoryTransferSetMemento(this.dayOffTimeEntity);
//		transferSettingDayOffTime.saveToMemento(memento);
//	}

}
