/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import lombok.Data;
import lombok.Setter;

/**
 * The Class PrintRemarksContentDto.
 */
@Data
@Setter
public class PrintRemarksContentCommand {
	
	/** The used classification. */
	private int usedClassification;
	
	/** The printitem. */
	private int printitem;
}
