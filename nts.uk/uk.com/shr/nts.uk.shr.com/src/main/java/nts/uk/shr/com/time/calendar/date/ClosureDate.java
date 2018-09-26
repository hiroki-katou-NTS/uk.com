/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.shr.com.time.calendar.date;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.Day;

/**
 * The Class ClosureDate.
 */
// 日付
@Getter
public class ClosureDate extends DomainObject {

	/** The closure day. */
	// 日
	private Day closureDay;

	/** The last day of month. */
	// 末日とする
	private Boolean lastDayOfMonth;

	/**
	 * Instantiates a new closure date.
	 *
	 * @param closureDay
	 *            the closure day
	 * @param lastDayOfMonth
	 *            the last day of month
	 */
	public ClosureDate(Integer closureDay, Boolean lastDayOfMonth) {
		this.closureDay = new Day(closureDay);
		this.lastDayOfMonth = lastDayOfMonth;
	}
}
