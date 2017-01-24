/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor;

import nts.arc.time.YearMonth;

public class HistoryInsurance {
	
	/**
	 * Convert month.
	 *
	 * @param yearMonth the year month
	 * @return the string
	 */
	public static String convertMonth(YearMonth yearMonth) {
		String convert = "";
		String mounth = "";
		if (yearMonth.month() < 10) {
			mounth = "0" + yearMonth.month();
		} else
			mounth = String.valueOf(yearMonth.month());
		convert = yearMonth.year() + "/" + mounth;
		return convert;
	}
}
