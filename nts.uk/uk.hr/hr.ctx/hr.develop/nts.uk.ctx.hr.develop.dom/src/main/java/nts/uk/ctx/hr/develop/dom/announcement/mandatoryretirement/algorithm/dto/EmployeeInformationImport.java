/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeInformationImport {

	private String employeeId;

	private String employeeCode;

	private String businessName;

	private String businessNameKana;
	
	private String departmentId;

	private String departmentCode;

	private String departmentName;
	
	private String positionId; 

	private String positionCode;
	
	private String positionName; 

	private String employmentCode;
	
	private String employmentName;
}
