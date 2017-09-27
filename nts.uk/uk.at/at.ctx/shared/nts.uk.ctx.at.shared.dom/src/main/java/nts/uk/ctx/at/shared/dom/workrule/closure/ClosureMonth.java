/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class ClosureMonth.
 */

// 当月
@Getter
public class ClosureMonth extends DomainObject{
	
	/** The processing date. */
	private YearMonth processingDate;
	
	/**
	 * Instantiates a new closure month.
	 *
	 * @param value the value
	 */
	public ClosureMonth(Integer value) {
		this.processingDate = YearMonth.of(value);
	}
	
}
