/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;

/**
 * The Interface FlowWorkRestSettingGetMemento.
 */
public interface FlowWorkRestSettingGetMemento {

	/**
	 * Gets the common rest setting.
	 *
	 * @return the common rest setting
	 */
	 CommonRestSetting getCommonRestSetting();

	/**
	 * Gets the flow rest setting.
	 *
	 * @return the flow rest setting
	 */
	 FlowWorkRestSettingDetail getFlowRestSetting();
}
