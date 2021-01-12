/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface OtherEmTimezoneLateEarlySetSetMemento.
 */
public interface OtherEmTimezoneLateEarlySetSetMemento {

	/**
	 * Sets the del time rounding set.
	 *
	 * @param delTimeRoundingSet the new del time rounding set
	 */
	void setDelTimeRoundingSet(TimeRoundingSetting delTimeRoundingSet);
	
	

	
	/**
	 * Sets the grace time set.
	 *
	 * @param graceTimeSet the new grace time set
	 */
	void setGraceTimeSet(GraceTimeSetting graceTimeSet);
	
	
	/**
	 * Sets the record time rounding set.
	 *
	 * @param recordTimeRoundingSet the new record time rounding set
	 */
	void setRecordTimeRoundingSet(TimeRoundingSetting recordTimeRoundingSet);
	
	
	/**
	 * Sets the late early atr.
	 *
	 * @param lateEarlyAtr the new late early atr
	 */
	void setLateEarlyAtr(LateEarlyAtr lateEarlyAtr);
}
