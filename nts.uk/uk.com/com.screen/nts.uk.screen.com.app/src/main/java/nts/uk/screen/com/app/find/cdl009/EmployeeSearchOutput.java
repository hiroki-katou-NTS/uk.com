/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.find.cdl009;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * The Class EmployeeSearchOutput.
 */
@Data
@Builder
public class EmployeeSearchOutput implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;
	
	/** The employee name. */
	private String employeeName;
	
	/** The workplace name. */
	private String workplaceName;
}
