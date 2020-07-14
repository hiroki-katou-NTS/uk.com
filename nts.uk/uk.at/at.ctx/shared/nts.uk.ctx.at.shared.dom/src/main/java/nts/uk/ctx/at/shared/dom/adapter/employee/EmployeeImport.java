/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;


/**
 * The Class EmployeeImport.
 */
//社員
// Imported Class for RequestList #1-2
@Getter
@Builder
public class EmployeeImport implements Serializable{
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The entry date. */
	// 入社年月日
	private GeneralDate entryDate;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The employee code. */
	// 社員コード
	private String employeeCode;
	
	/** The employee mail address. */
	// 社員メールアドレス
	private MailAddress employeeMailAddress;
	
	/** The employee name. */
	// 社員名
	private String employeeName;
	
	/** The entire date. */
	// 退職年月日
	private GeneralDate retiredDate;

	/**
	 * Instantiates a new employee imported.
	 *
	 * @param entryDate the entry date
	 * @param employeeId the employee id
	 * @param employeeCode the employee code
	 * @param employeeMailAddress the employee mail address
	 * @param employeeName the employee name
	 * @param entireDate the entire date
	 */
	public EmployeeImport(GeneralDate entryDate, String employeeId, String employeeCode,
			MailAddress employeeMailAddress, String employeeName, GeneralDate retiredDate) {
		super();
		this.entryDate = entryDate;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeMailAddress = employeeMailAddress;
		this.employeeName = employeeName;
		this.retiredDate = retiredDate;
	}
	
}
