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

	/** The transfer setting over time. */
	private CompensatoryTransferSetting transferSettingOverTime;
	
	/** The transfer setting day off time. */
	private CompensatoryTransferSetting transferSettingDayOffTime;
	
	/**
	 * Instantiates a new occurrence vacation setting.
	 *
	 * @param memento the memento
	 */
	public OccurrenceVacationSetting(OccurrenceVacationGetMemento memento) {
		this.transferSettingOverTime = memento.getTransferSettingOverTime();
		this.transferSettingDayOffTime = memento.getTransferSettingDayOffTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OccurrenceVacationSetMemento memento) {
		memento.setTransferSettingOverTime(this.transferSettingOverTime);
		memento.setTransferSettingDayOffTime(this.transferSettingDayOffTime);
	}
}
