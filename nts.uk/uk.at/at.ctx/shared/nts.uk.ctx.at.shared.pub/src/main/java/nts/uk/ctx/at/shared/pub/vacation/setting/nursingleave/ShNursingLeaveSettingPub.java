/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ShNursingLeaveSettingPub.
 */
public interface ShNursingLeaveSettingPub {

	/**
	 * Aggr child nursing remain period.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param period the period
	 * @param mode the mode
	 * @return the child nursing remain export
	 */
	// RequestList206: æœŸé–“å†…ã�®å­�çœ‹è­·æ®‹ã‚’é›†è¨ˆã�™ã‚‹
	// ï¼œINPUTï¼žãƒ»ä¼šç¤¾IDãƒ»ç¤¾å“¡IDãƒ»é›†è¨ˆé–‹å§‹æ—¥ãƒ»é›†è¨ˆçµ‚äº†æ—¥ãƒ»ãƒ¢ãƒ¼ãƒ‰
	ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId, String employeeId, DatePeriod period, Integer mode);

	/**
	 * Aggr nursing remain period.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param period the period
	 * @param mode the mode
	 * @return the child nursing remain export
	 */
	// RequestList207: æœŸé–“å†…ã�®ä»‹è­·æ®‹ã‚’é›†è¨ˆã�™ã‚‹
	// ï¼œINPUTï¼ž ãƒ»ä¼šç¤¾ID ãƒ»ç¤¾å“¡ID ãƒ»é›†è¨ˆé–‹å§‹æ—¥ ãƒ»é›†è¨ˆçµ‚äº†æ—¥ ãƒ»ãƒ¢ãƒ¼ãƒ‰
	ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate , Boolean monthlyMode);
}
