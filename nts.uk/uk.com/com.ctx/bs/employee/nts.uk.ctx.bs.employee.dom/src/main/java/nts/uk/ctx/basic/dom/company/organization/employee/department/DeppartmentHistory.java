/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.bs.employee.dom.common.history.Period;

/**
 * The Class DeppartmentHistory.
 */
@Getter
public class DeppartmentHistory extends DomainObject {
	
	/** The id. */
	private String id;
	
	/** The period. */
	private Period period; 
}
