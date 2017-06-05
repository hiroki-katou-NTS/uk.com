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
	 * Sets the transfer setting.
	 *
	 * @param transferSetting the new transfer setting
	 */
	void setTransferSetting(CompensatoryTransferSetting transferSetting);

	/**
	 * Sets the occurrence division.
	 *
	 * @param occurrenceDivision the new occurrence division
	 */
	void setOccurrenceDivision(CompensatoryOccurrenceDivision occurrenceDivision);
}
