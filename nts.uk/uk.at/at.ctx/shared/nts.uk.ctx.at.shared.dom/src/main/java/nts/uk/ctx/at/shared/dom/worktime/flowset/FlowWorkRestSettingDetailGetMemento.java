/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowWorkRestSettingDetailGetMemento.
 */
public interface FlowWorkRestSettingDetailGetMemento {

	/**
 	 * Gets the flow rest setting.
 	 *
 	 * @return the flow rest setting
 	 */
 	FlowRestSet getFlowRestSetting();

	/**
	 * Gets the flow fixed rest setting.
	 *
	 * @return the flow fixed rest setting
	 */
	 FlowFixedRestSet getFlowFixedRestSetting();

	/**
	 * Gets the use plural work rest time.
	 *
	 * @return the use plural work rest time
	 */
	 boolean getUsePluralWorkRestTime();
}
