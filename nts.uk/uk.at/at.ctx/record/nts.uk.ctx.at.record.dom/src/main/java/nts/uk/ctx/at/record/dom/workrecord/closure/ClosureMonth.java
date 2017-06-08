/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class ClosureMonth.
 */
@Getter
@Setter
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
