/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import lombok.Data;

/**
 * The Class TimeitemTobeDisplayDto.
 * @author HoangDD
 */
@Data
public class TimeitemTobeDisplayDto {
	
	/** The order no. */
	private int orderNo;
	
	/** The attendance display. */
	private int attendanceDisplay;
	
	/** The attendance name. */
	private String attendanceName;
}
