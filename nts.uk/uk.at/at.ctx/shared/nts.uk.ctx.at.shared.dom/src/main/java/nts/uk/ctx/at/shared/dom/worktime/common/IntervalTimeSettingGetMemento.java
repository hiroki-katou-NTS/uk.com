/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface IntervalTimeSettingGetMemento.
 */
public interface IntervalTimeSettingGetMemento {

	/**
	 * Gets the use interval exemption time.
	 *
	 * @return the use interval exemption time
	 */
	boolean getuseIntervalExemptionTime();
	
	/**
	 * Gets the interval exemption time round.
	 *
	 * @return the interval exemption time round
	 */
	TimeRoundingSetting getIntervalExemptionTimeRound();
	
	
	/**
	 * Gets the interval time.
	 *
	 * @return the interval time
	 */
	IntervalTime getIntervalTime();
	
	
	/**
	 * Gets the use interval time.
	 *
	 * @return the use interval time
	 */
	boolean getuseIntervalTime();
}
