/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Department.
 */
@Getter
public class Department extends AggregateRoot {
	
	/** The id. */
	/* 会社コード */
	private String id;
	
	/** The department code. */
	/* 部門コード */
	private DepartmentCode departmentCode;
	
	/** The department name. */
	/* 部門名称 */
	private DepartmentName departmentName;
	
	/** The hierarchy code. */
	/* 階層コード */
	private String hierarchyCode;
}
