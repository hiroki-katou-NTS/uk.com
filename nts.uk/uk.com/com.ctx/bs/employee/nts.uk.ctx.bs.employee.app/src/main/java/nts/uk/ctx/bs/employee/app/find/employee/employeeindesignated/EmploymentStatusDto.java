/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employee.employeeindesignated;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class EmploymentStatusDto.
 */
@Getter
@Setter
public class EmploymentStatusDto {
	/** The employee id. */
	private String employeeId;
	
	/** The referene date. */
	private GeneralDate refereneDate;
	
	/** The status of employment. */
	private int statusOfEmployment;
	
	/** The leave holiday type. */
	private int leaveHolidayType;

}
