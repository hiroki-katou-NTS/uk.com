/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktype.vacation.history;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * Instantiates a new vacation history dto.
 */
@Data
public class VacationHistoryDto {
	
	/** The history id. */
	private String historyId;
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
}
