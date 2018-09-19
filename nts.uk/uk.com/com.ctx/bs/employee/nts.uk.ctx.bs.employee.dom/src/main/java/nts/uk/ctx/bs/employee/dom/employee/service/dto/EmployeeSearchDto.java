package nts.uk.ctx.bs.employee.dom.employee.service.dto;
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
import lombok.Data;

/**
 * Instantiates a new employee search dto.
 */
@Data
public class EmployeeSearchDto {
	
	/** The employee code. */
	private String employeeCode;
	
	/** The system. */
	private String system;
}