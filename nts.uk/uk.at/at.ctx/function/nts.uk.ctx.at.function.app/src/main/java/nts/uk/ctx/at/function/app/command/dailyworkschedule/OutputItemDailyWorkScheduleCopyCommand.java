/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class OutputItemDailyWorkScheduleCopyCommand.
 */
// @author HoangDD
@Getter
@NoArgsConstructor
@Setter
public class OutputItemDailyWorkScheduleCopyCommand {
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private int id;
}
