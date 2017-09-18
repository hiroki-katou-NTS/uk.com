/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.bs.employee.dom.common.history.Period;

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
	private Period period; 
}
