/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

/**
 * The Interface UpperLimitSettingSetMemento.
 */
public interface UpperLimitSettingSetMemento {
	
	/**
	 * Sets the retention years amount.
	 *
	 * @param retentionYearsAmount the new retention years amount
	 */
	void setRetentionYearsAmount(RetentionYearsAmount retentionYearsAmount);
	
	/**
	 * Sets the max days retention.
	 *
	 * @param maxDaysCumulation the new max days retention
	 */
	void setMaxDaysRetention(MaxDaysRetention maxDaysCumulation);
}
