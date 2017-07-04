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
	
	/** The closure day. */
	private Integer closureDay;
	
	/** The last day of month. */
	private Boolean lastDayOfMonth;

	/**
	 * Instantiates a new closure date.
	 *
	 * @param closureDay the closure day
	 * @param lastDayOfMonth the last day of month
	 */
	public ClosureDate(Integer closureDay, Boolean lastDayOfMonth) {
		this.closureDay = closureDay;
		this.lastDayOfMonth = lastDayOfMonth;
	}
}
