/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowWorkDedicateSettingSetMemento.
 */
public interface FlWorkDedSetMemento {

	/**
	 * Sets the overtime setting.
	 *
	 * @param otSet the new overtime setting
	 */
	void setOvertimeSetting(FlowOTSet otSet);

	/**
	 * Sets the calculate setting.
	 *
	 * @param fcSet the new calculate setting
	 */
	void setCalculateSetting(FlowCalculateSet fcSet);
}
