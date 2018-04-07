package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.Value;

@Value
public class EmploymentHisImport {
	// 社員ID
	private String employeeId;

	// 履歴ID
	private String historyID;

	// 雇用コード
	private String employmentCode;

	// 給与区分
	// 1: 日給 - DailySalary(1),
	// 2: 日給月給 - DailyMonthlySalary(2),
	// 3: 時間給 - HourlySalary(3),
	// 4: 月給 - MonthlySalary(4);
	private int salarySegment;
}
