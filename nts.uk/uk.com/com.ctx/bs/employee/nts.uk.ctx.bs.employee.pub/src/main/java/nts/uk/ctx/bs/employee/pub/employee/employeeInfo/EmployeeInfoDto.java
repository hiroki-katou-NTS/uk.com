/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee.employeeInfo;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeDto. 
 * Dto by Request List #18
 */
@Data
public class EmployeeInfoDto {

	/** The company id. */
	private String companyId;

	/** The employee code. */
	private String employeeCode;

	/** The employee id. */
	private String employeeId;

	/** The person Id. */
	private String personId;

}
