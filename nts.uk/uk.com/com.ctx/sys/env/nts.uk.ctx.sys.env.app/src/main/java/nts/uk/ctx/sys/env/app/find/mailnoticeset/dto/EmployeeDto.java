/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import lombok.Getter;

/**
 * The Class EmployeeDto.
 */
@Getter
public class EmployeeDto {

	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;

	/** The employee name. */
	private String employeeName;

	/**
	 * Instantiates a new employee dto.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param employeeCode
	 *            the employee code
	 * @param employeeName
	 *            the employee name
	 */
	public EmployeeDto(String employeeId, String employeeCode, String employeeName) {
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
	}

}
