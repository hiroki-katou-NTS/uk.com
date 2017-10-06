/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ScheduleManagementControl.
 */
// 予定管理制御
@Getter
public class ScheduleManagementControl extends DomainObject{

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	
	/** The schedule management atr. */
	// 予定管理区分
	private UseAtr scheduleManagementAtr;
}
