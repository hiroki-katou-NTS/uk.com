/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new kcp 009 employee search data.
 */
@Builder
@Setter
@Getter
public class Kcp009EmployeeSearchData{
	
	/** The employee id. */
	private String employeeId;
	
	/** The employee code. */
	private String employeeCode;
	
	/** The business name. */
	private String businessName;
	
	/** The org name. */
	private String orgName;
	
}
