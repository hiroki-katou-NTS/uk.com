/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface IntervalTimeSettingSetMemento.
 */
public interface IntervalTimeSettingSetMemento {
	
	/**
	 * Sets the use interval exemption time.
	 *
	 * @param useIntervalExemptionTime the new use interval exemption time
	 */
	void setUseIntervalExemptionTime(boolean useIntervalExemptionTime);
	
	
	/**
	 * Sets the interval exemption time round.
	 *
	 * @param intervalExemptionTimeRound the new interval exemption time round
	 */
	void setIntervalExemptionTimeRound(TimeRoundingSetting intervalExemptionTimeRound);
	
	
	/**
	 * Sets the interval time.
	 *
	 * @param intervalTime the new interval time
	 */
	void setIntervalTime(IntervalTime intervalTime);
	
	/**
	 * Sets the use interval time.
	 *
	 * @param useIntervalTime the new use interval time
	 */
	void setUseIntervalTime(boolean useIntervalTime);

}
