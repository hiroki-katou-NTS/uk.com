/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface OtherEmTimezoneLateEarlySetGetMemento.
 */
public interface OtherEmTimezoneLateEarlySetGetMemento {

	/**
	 * Gets the del time rounding set.
	 *
	 * @return the del time rounding set
	 */
	TimeRoundingSetting getDelTimeRoundingSet();
	
	

	
	
	/**
	 * Gets the grace time set.
	 *
	 * @return the grace time set
	 */
	GraceTimeSetting getGraceTimeSet();
	
	
	/**
	 * Gets the record time rounding set.
	 *
	 * @return the record time rounding set
	 */
	TimeRoundingSetting getRecordTimeRoundingSet();
	
	
	/**
	 * Gets the late early atr.
	 *
	 * @return the late early atr
	 */
	LateEarlyAtr getLateEarlyAtr();
}
