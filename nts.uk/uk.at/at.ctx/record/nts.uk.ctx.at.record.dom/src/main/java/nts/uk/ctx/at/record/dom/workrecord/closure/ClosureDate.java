/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ClosureDate.
 */
@Getter
@Setter
public class ClosureDate extends DomainObject{
	
	/** The day. */
	private Integer day;
	
	/** The last day of month. */
	private Boolean lastDayOfMonth;

}
