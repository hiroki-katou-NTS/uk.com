/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.employee.jobtitle;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeJobHistExport.
 * @author HoangNDH
 */
@Data
@Builder
// 社員所属職位履歴を取得
public class EmployeeJobHistExport {

	// 社員ID
	private String employeeId;

	// 職位ID
	private String jobTitleID;

	// 職位名称
	private String jobTitleName;

	private String jobCode;

	// 配属期間 start
	private GeneralDate startDate;

	// 配属期間 end
	private GeneralDate endDate;

}
