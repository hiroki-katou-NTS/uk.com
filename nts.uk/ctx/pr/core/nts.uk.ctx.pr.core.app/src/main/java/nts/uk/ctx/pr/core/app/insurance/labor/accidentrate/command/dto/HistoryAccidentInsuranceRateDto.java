/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

@Data
public class HistoryAccidentInsuranceRateDto {

	/** The history id. */
	private String historyId;

	/** The start month rage. */
	private String startMonthRage;

	/** The end month rage. */
	private String endMonthRage;

	public MonthRange toDomain() {
		return MonthRange.range(this.startMonthRage, this.endMonthRage, "/");
	}
}
