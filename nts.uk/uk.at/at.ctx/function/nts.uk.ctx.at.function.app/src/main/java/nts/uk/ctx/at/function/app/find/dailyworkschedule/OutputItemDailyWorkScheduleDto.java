/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;

/**
 * The Class OutputItemDailyWorkScheduleDto.
 * @author HoangDD
 */
@Data
public class OutputItemDailyWorkScheduleDto {

	/** The item code. */
	private String itemCode;
	
	/** The item name. */
	private String itemName;
	
	/** The lst displayed attendance. */
	private List<TimeitemTobeDisplayDto> lstDisplayedAttendance;
	
	/** The lst remark content. */
	private List<PrintRemarksContentDto> lstRemarkContent;
	
	/** The zone name. */
	private int workTypeNameDisplay;
	
	/** The remark input no. */
	private int remarkInputNo;
}
