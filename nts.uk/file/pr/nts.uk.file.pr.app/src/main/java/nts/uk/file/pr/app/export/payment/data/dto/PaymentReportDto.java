/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentReportDto.
 */
@Getter
@Setter
public class PaymentReportDto {
	/** The department info. */
	private DepartmentDto departmentInfo;

	/** The employee info. */
	private EmployeeDto employeeInfo;

	/** 支給項目 */
	private List<SalaryItemDto> paymentItems;

	/** 控除項目 */
	private List<SalaryItemDto> deductionItems;

	/** 勤怠項目 */
	private List<SalaryItemDto> attendanceItems;

	/** 記事項目*/
	private List<SalaryItemDto> articleItems;
	
	/** The remark. */
	private String remark;
	
	/** The menu. */
	private String menu;
	
}
