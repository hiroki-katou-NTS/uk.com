/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ScheduleManagementControl.
 */
// 予定管理制御
@Getter
public class ScheduleManagementControl extends AggregateRoot{

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	
	/** The schedule management atr. */
	// 予定管理区分
	private UseAtr scheduleManagementAtr;


	/**
	 * Instantiates a new schedule management control.
	 *
	 * @param employeeId the employee id
	 * @param scheduleManagementAtr the schedule management atr
	 */
	public ScheduleManagementControl(String employeeId, UseAtr scheduleManagementAtr) {
		this.employeeId = employeeId;
		this.scheduleManagementAtr = scheduleManagementAtr;
	}
	
	/**
	 * Checks if is schedule management atr.
	 *
	 * @return true, if is schedule management atr
	 */
	public boolean isScheduleManagementAtr(){
		return this.scheduleManagementAtr == UseAtr.USE;
	}
}
