/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import lombok.Data;

/**
 * The Class PrintRemarksContentDto.
 */
@Data
public class PrintRemarksContentCommand {
	
	/** The used classification. */
	private int usedClassification;
	
	/** The printitem. */
	private int printitem;
}
