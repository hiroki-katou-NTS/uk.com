/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FlowWorkRestSettingSetMemento.
 */
public interface FlowWorkRestSettingSetMemento {

	/**
	 * Sets the common rest setting.
	 *
	 * @param commonRest the new common rest setting
	 */
	 void setCommonRestSetting(CommonRestSetting commonRest);

	/**
	 * Sets the flow rest setting.
	 *
	 * @param flowRest the new flow rest setting
	 */
	 void setFlowRestSetting(FlowWorkRestSettingDetail flowRest);
}
