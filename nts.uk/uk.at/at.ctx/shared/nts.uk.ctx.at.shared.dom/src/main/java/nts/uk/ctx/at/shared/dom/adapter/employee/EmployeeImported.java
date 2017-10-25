/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.Getter;
import nts.arc.time.GeneralDate;


/**
 * The Class EmployeeImported.
 */
//社員
@Getter
public class EmployeeImported {
	
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
	private GeneralDate entireDate;

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
	public EmployeeImported(GeneralDate entryDate, String employeeId, String employeeCode,
			MailAddress employeeMailAddress, String employeeName, GeneralDate entireDate) {
		super();
		this.entryDate = entryDate;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeMailAddress = employeeMailAddress;
		this.employeeName = employeeName;
		this.entireDate = entireDate;
	}
	
}
