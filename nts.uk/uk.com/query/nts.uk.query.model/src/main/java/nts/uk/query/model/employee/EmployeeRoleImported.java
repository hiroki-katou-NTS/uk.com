/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class EmployeeRoleImported.
 */
@Data
@AllArgsConstructor
public class EmployeeRoleImported {
	
	/** The role id. */
	private String roleId;
	
	/** The employee reference range. */
	private EmployeeReferenceRange employeeReferenceRange;
	
}
