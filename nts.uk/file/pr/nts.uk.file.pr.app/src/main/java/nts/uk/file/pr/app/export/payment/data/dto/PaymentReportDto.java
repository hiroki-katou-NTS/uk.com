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
	private PaymentDepartmentDto departmentInfo;

	/** The employee info. */
	private PaymentEmployeeDto employeeInfo;
	
	/** The company info. */
	private PaymentCompanyDto companyInfo;

	/** 支給項目 */
	private List<PaymentSalaryItemDto> paymentItems;

	/** 控除項目 */
	private List<PaymentSalaryItemDto> deductionItems;

	/** 勤怠項目 */
	private List<PaymentSalaryItemDto> attendanceItems;

	/** 記事項目*/
	private List<PaymentSalaryItemDto> articleItems;
	
	/** 他項目*/
	private List<PaymentSalaryItemDto> otherItems;
	
	/** The remark. */
	private String remark;
	
	/** The menu. */
	private String menu;
	
	/** The name. */
	private String name;
	
}
