/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface PredeteminePolicyService.
 */
public interface PredeteminePolicyService {

	/**
	 * Validate one day.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @param startTime the start time
	 * @param endTime the end time
	 */
	public void validateOneDay(String companyId,WorkTimeCode worktimeCode,TimeWithDayAttr startTime,TimeWithDayAttr endTime);
}
