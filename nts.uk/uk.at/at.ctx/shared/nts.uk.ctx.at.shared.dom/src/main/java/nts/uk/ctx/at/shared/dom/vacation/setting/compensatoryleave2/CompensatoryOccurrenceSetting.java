/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * Gets the transfer setting.
 *
 * @return the transfer setting
 */
@Getter
public class CompensatoryOccurrenceSetting extends DomainObject  {
	
	/** The occurrence type. */
	private CompensatoryOccurrenceDivision occurrenceType;
	
	/** The transfer setting. */
	private TransferSetting transferSetting;
	
	/**
	 * Instantiates a new compensatory occurrence setting.
	 *
	 * @param memento the memento
	 */
	public CompensatoryOccurrenceSetting(CompensatoryOccurrenceSettingGetMemento memento) {
		this.occurrenceType = memento.getOccurrenceType();
		this.transferSetting = memento.getTransferSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompensatoryOccurrenceSettingSetMemento memento) {
		memento.setOccurrenceType(this.occurrenceType);
		memento.setTransferSetting(this.transferSetting);
	}
}
