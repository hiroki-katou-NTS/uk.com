/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Interface OccurrenceVacationGetMemento.
 */
public interface OccurrenceVacationGetMemento {
	
	/**
	 * Gets the transfer setting over time.
	 *
	 * @return the transfer setting over time
	 */
	CompensatoryTransferSetting getTransferSettingOverTime();
	
	/**
	 * Gets the transfer setting day off time.
	 *
	 * @return the transfer setting day off time
	 */
	CompensatoryTransferSetting getTransferSettingDayOffTime();
}
