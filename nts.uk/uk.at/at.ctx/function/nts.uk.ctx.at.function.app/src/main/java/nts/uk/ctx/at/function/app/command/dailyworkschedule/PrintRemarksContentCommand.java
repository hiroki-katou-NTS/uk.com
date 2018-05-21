/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import lombok.Data;
import lombok.Setter;

/**
 * The Class PrintRemarksContentDto.
 * @author HoangDD
 */
@Data
@Setter
public class PrintRemarksContentCommand {
	
	/** The used classification. */
	private int usedClassification;
	
	/** The print item. */
	private int printItem;
}
