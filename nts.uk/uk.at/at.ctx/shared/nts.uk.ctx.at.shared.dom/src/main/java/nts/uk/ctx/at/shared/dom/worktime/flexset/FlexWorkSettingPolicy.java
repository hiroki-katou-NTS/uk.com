/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlexWorkSettingPolicy.
 */
public interface FlexWorkSettingPolicy {
	
	/**
	 * Can register flex work setting.
	 *
	 * @param flexWorkSetting the flex work setting
	 * @param predetemineTimeSet the predetemine time set
	 */
	public void canRegisterFlexWorkSetting(FlexWorkSetting flexWorkSetting, PredetemineTimeSetting predetemineTimeSet);

}
