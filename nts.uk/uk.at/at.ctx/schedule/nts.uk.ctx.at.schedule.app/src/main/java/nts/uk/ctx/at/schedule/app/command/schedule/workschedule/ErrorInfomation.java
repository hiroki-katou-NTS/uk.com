/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfomation {
	
	private String sid;
	
	private String scd;
	
	private String empName;
	
	private GeneralDate date;
	
	private Integer attendanceItemId;
	
	private String errorMessage;
	
}
