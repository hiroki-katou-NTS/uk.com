/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FlowWorkRestSettingDetailSetMemento.
 */
public interface FlowWorkRestSettingDetailSetMemento {

	/**
	 * Sets the flow rest setting.
	 *
	 * @param set the new flow rest setting
	 */
 	void setFlowRestSetting(FlowRestSet set);

	/**
	 * Sets the flow fixed rest setting.
	 *
	 * @param set the new flow fixed rest setting
	 */
	 void setFlowFixedRestSetting(FlowFixedRestSet set);

	/**
	 * Sets the use plural work rest time.
	 *
	 * @param val the new use plural work rest time
	 */
	 void setUsePluralWorkRestTime(boolean val);
}
