/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.schedulemanagementcontrol;

import nts.arc.time.GeneralDate;

/**
 * The Interface ScheduleManagementControlPub.
 */
public interface ScheduleManagementControlPub {

	/**
	 * Checks if is schedule management atr.
	 *
	 * @param employeeId the employee id
	 * @return true, if is schedule management atr
	 */
	//	RequestList27
	public boolean isScheduleManagementAtr(String employeeId);
			
	public boolean isScheduleManagementAtr(String employeeId, GeneralDate baseDate);
	

}
