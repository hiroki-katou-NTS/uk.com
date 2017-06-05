/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ClosureHistorry.
 */
@Getter
@Setter
public class ClosureHistorry extends DomainObject{
	
	/** The close name. */
	private CloseName closeName;
	
	/** The closure id. */
	private ClosureId closureId;
	
	/** The closure year. */
	private ClosureYear endDate;
	
	/** The closure date. */
	private ClosureDate closureDate;
	
	/** The start date. */
	private ClosureDate startDate;

}
