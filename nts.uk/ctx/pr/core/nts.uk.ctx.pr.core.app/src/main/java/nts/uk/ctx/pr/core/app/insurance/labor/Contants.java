/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor;

import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;

public class Contants {

	/** The Constant YEAR_MONTH_MAX. */
	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
		DateTimeConstraints.LIMIT_MONTH.max());
}
