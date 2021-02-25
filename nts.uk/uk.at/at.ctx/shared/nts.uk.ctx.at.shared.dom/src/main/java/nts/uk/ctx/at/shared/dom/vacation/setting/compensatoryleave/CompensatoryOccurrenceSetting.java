/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;

/**
 * Gets the transfer setting.
 *
 * @return the transfer setting
 */
// 会社別代休時間設定
@Getter
public class CompensatoryOccurrenceSetting extends DomainObject {
    
	// 発生元区分
	/** The occurrence type. */
	private CompensatoryOccurrenceDivision occurrenceType;
	
	// 振替設定
	/** The transfer setting. */
	private SubHolTransferSet transferSetting;

	/**
	 * Instantiates a new compensatory occurrence setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompensatoryOccurrenceSetting(CompensatoryOccurrenceSettingGetMemento memento) {
		this.occurrenceType = memento.getOccurrenceType();
		this.transferSetting = memento.getTransferSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompensatoryOccurrenceSettingSetMemento memento) {
		memento.setOccurrenceType(this.occurrenceType);
		memento.setTransferSetting(this.transferSetting);
	}

	public CompensatoryOccurrenceSetting(CompensatoryOccurrenceDivision occurrenceType,
			SubHolTransferSet transferSetting) {
		super();
		this.occurrenceType = occurrenceType;
		this.transferSetting = transferSetting;
	}
	
}
