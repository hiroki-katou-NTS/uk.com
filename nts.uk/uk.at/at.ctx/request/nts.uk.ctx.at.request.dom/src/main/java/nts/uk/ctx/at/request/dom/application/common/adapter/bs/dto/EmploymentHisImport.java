package nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto;

import lombok.Value;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class EmploymentHisImport {
	// 社員ID
	private String employeeId;

	// 履歴ID
	private String historyID;
	
	// 期間
	private  DatePeriod datePeriod;
	
	// 雇用コード
	private String employmentCode;
}
