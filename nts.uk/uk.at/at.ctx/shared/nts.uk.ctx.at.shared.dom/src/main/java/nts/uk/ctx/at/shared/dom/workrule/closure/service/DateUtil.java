/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.Calendar;

import nts.arc.time.GeneralDate;

/**
 * The Class DateUtils.
 */

public class DateUtil {

	/**
	 * Gets the last date of month.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @return the last date of month
	 */
	public static GeneralDate getLastDateOfMonth(int year, int month) {
		GeneralDate baseDate = GeneralDate.ymd(year, month, 1);
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate.date());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return GeneralDate.legacyDate(c.getTime());
	}

	/**
	 * Checks if is date of month.
	 *
	 * @return true, if is date of month
	 */
	public static boolean isDateOfMonth(int year, int month, int dayOfMonth) {
		GeneralDate baseDate = DateUtil.getLastDateOfMonth(year, month);
		return dayOfMonth <= baseDate.day();
	}
}
