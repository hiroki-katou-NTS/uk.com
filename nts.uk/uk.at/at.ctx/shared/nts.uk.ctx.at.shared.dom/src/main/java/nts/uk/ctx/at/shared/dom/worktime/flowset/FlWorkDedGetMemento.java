/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowWorkDedicateSettingGetMemento.
 */
public interface FlWorkDedGetMemento {

	/**
	 * Gets the overtime setting.
	 *
	 * @return the overtime setting
	 */
	FlowOTSet getOvertimeSetting();

	/**
	 * Gets the calculate setting.
	 *
	 * @return the calculate setting
	 */
	FlowCalculateSet getCalculateSetting();
}
