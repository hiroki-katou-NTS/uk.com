/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import lombok.Data;
import lombok.Setter;

/**
 * The Class TimeitemTobeDisplayDto.
 * @author HoangDD
 */
@Data
@Setter
public class TimeItemTobeDisplayCommand {
	
	/** The sort by. */
	private int sortBy;
	
	/** The item to display. */
	private int itemToDisplay;
}
