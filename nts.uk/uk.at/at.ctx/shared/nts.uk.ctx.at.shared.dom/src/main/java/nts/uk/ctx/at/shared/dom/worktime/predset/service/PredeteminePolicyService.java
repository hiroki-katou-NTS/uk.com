/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
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
	 */
	public void validateOneDay(PredetemineTimeSet pred,TimeWithDayAttr startTime,TimeWithDayAttr endTime);
	
	/**
	 * Compare with one day range.
	 *
	 * @param pred the pred
	 * @param designatedTime the designated time
	 */
	public void compareWithOneDayRange(PredetemineTimeSet pred,DesignatedTime designatedTime);
	
	/**
	 * Compare with one day range.
	 *
	 * @param pred the pred
	 * @param lateEarlyGraceTime the late early grace time
	 */
	public void compareWithOneDayRange(PredetemineTimeSet pred,LateEarlyGraceTime lateEarlyGraceTime);
}
