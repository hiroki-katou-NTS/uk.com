/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import lombok.Data;

/**
 * The Class EmployeeRoleImported.
 */
@Data
public class EmployeeRoleImported {
	
	/** The role id. */
	private String roleId;
	
	/** The employee reference range. */
	private EmployeeReferenceRange employeeReferenceRange;
	
}
