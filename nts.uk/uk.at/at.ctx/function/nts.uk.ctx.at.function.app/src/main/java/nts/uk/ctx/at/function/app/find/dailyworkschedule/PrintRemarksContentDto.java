/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;

/**
 * The Class PrintRemarksContentDto.
 * @author HoangDD
 */
@Data
public class PrintRemarksContentDto {
	
	/** The used classification. */
	private int usedClassification;
	
	/** The print item. */
	private int printItem;
	
	private String divergenceReasonCode;
	
	private List<String> reasonForDivergence;
}
