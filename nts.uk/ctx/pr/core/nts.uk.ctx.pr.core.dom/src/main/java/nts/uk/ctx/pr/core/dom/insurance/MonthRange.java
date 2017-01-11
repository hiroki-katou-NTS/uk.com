/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance;

import lombok.Data;
import nts.arc.time.YearMonth;

/**
 * The Class MonthRange.
 */
@Data
public class MonthRange {

	/** The start month. */
	private YearMonth startMonth;

	/** The end month. */
	private YearMonth endMonth;

}
