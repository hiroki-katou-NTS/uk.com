/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface OccurrenceVacationSetMemento.
 */
public interface OccurrenceVacationSetMemento {
	
	/**
	 * Sets the transfer setting over time.
	 *
	 * @param transferSetting the new transfer setting over time
	 */
	void setTransferSettingOverTime(CompensatoryTransferSetting transferSetting);

	/**
	 * Sets the transfer setting day off time.
	 *
	 * @param transferSetting the new transfer setting day off time
	 */
	void setTransferSettingDayOffTime(CompensatoryTransferSetting transferSetting);
}
