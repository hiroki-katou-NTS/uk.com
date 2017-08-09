/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter;

import lombok.Getter;

/**
 * The Class EmployeeDto.
 */
@Getter
public class EmployeeDto {

	//ビジネスネーム
	/** The business name. */
	private String businessName;

	//個人ID
	/** The personal id. */
	private String personalId;

	//社員ID
	/** The employee id. */
	private Integer employeeId;

	//社員コード
	/** The employee code. */
	private String employeeCode;

	/**
	 * @param businessName
	 * @param personalId
	 * @param employeeId
	 * @param employeeCode
	 */
	public EmployeeDto(String businessName, String personalId, Integer employeeId, String employeeCode) {
		this.businessName = businessName;
		this.personalId = personalId;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
	}
}
