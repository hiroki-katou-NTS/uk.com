/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PredeteminePolicyService.
 */
public interface PredeteminePolicyService {
	
	/**
	 * Validate one day.
	 *
	 * @param pred the pred
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return true, if successful
	 */
	public boolean validateOneDay(PredetemineTimeSetting pred,TimeWithDayAttr startTime,TimeWithDayAttr endTime);

	/**
	 * Validate predetemine time.
	 *
	 * @param pred the pred
	 */
	public void validatePredetemineTime(PredetemineTimeSetting pred);
	
	/**
	 * Compare with one day range.
	 *
	 * @param pred the pred
	 * @param designatedTime the designated time
	 */
	public void compareWithOneDayRange(PredetemineTimeSetting pred,DesignatedTime designatedTime);
	
	/**
	 * Compare with one day range.
	 *
	 * @param pred the pred
	 * @param lateEarlyGraceTime the late early grace time
	 */
	public void compareWithOneDayRange(PredetemineTimeSetting pred,LateEarlyGraceTime lateEarlyGraceTime);
}
