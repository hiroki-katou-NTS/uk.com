/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.department;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceConfigHistory.
 */

@Getter
public class DepartmentConfigHistory extends DomainObject {

	/** The history id. */
	//ID
	private String historyId;
	
	/** The period. */
	//期間
	private DatePeriod period;
}
