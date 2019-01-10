/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class MonthlyReturnItemDto.
 */
@Data
@NoArgsConstructor
public class MonthlyReturnItemDto {

	/** The lst display time item. */
	private List<DisplayTimeItemDto> lstDisplayTimeItem;
	
	/** The error list. */
	private List<String> errorList;
	
}
