/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.department;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class DepartmentHistory. 部門履歴
 */
@Getter
public class DepartmentHistory extends DomainObject {
	
	/** The id. */
	/* 履歴ID */
	private String id;
	
	/** The period. */
	/* 期間 */
	private DatePeriod period; 
}
