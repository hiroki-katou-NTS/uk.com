/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.file.pr.app.export.payment.data.dto.DepartmentDto;
import nts.uk.file.pr.app.export.payment.data.dto.EmployeeDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;

/**
 * The Class PaymentReportData.
 */
@Setter
@Getter
public class PaymentReportData {

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

	/** The processing ym. */
	private int processingYm;
	
	/** The remark. */
	private String remark;

}