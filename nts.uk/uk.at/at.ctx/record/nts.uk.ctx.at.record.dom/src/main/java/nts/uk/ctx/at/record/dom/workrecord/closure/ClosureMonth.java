/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ClosureMonth.
 */
@Getter
@Setter
public class ClosureMonth extends DomainObject{
	
	/** The processing date. */
	private ClosureYearMonth processingDate;
	
	/**
	 * Instantiates a new closure month.
	 *
	 * @param value the value
	 */
	public ClosureMonth(Integer value) {
		this.processingDate = ClosureYearMonth.of(value);
	}
	
}
