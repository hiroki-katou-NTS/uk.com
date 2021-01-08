/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

/**
 * The Interface UpperLimitSettingGetMemento.
 */
public interface UpperLimitSettingGetMemento {
	
	/**
	 * Gets the retention years amount.
	 *
	 * @return the retention years amount
	 */
	RetentionYearsAmount getRetentionYearsAmount();
	
	/**
	 * Gets the max days cumulation.
	 *
	 * @return the max days cumulation
	 */
	MaxDaysRetention getMaxDaysCumulation();
	
	
}
