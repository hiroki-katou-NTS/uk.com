/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ClosureDate.
 */
@Getter
public class ClosureDate extends DomainObject{
	
	/** The day. */
	private Integer day;
	
	/** The last day of month. */
	private Boolean lastDayOfMonth;

	/**
	 * Instantiates a new closure date.
	 *
	 * @param day the day
	 * @param lastDayOfMonth the last day of month
	 */
	public ClosureDate(Integer day, Boolean lastDayOfMonth) {
		this.day = day;
		this.lastDayOfMonth = lastDayOfMonth;
	}
}
