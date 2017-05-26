/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OccurrenceVacationSetting.
 */
@Getter
public class OccurrenceVacationSetting extends DomainObject {

	/** The transfer setting. */
	private CompensatoryTransferSetting transferSetting;

	/** The occurrence division. */
	private CompensatoryOccurrenceDivision occurrenceDivision;
	
	/**
	 * Instantiates a new occurrence vacation setting.
	 *
	 * @param memento the memento
	 */
	public OccurrenceVacationSetting(OccurrenceVacationGetMemento memento) {
		this.occurrenceDivision = memento.getOccurrenceDivision();
		this.transferSetting = memento.getTransferSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OccurrenceVacationSetMemento memento) {
		memento.setOccurrenceDivision(this.occurrenceDivision);
		memento.setTransferSetting(this.transferSetting);
	}
}
