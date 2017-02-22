/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.dto;

import lombok.Data;

/**
 * The Class HistoryUnemployeeInsuranceDto.
 */
@Data
public class HistoryUnemployeeInsuranceDto {
	
	/** The history id. */
	private String historyId;
	
	/** The start month rage. */
	private String startMonthRage;
	
	/** The end month rage. */
	private String endMonthRage;
	
	/** The infor month rage. */
	private String inforMonthRage;
}
