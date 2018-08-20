/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;

/**
 * Instantiates a new daily attendance item dto.
 * @author HoangDD
 */
@Data
public class DailyAttendanceItemDto {
	
	/** The code. */
	// display number
	private int code;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private int id;
}
