package nts.uk.ctx.bs.employee.app.command.employment.history;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class EmploymentHistoryCommand {
	/** The history Id. */
	// 履歴ID
	@PeregRecordId
	private String historyId;
	
	/** The Employee Id. */
	// 社員ID
	@PeregEmployeeId
	private String employeeId;
	
	/** The SalarySegment */
	// 給与区分
	@PeregItem("IS00069")
	private int salarySegment;
	
	/** The employment code. */
	// 雇用コード. 
	@PeregItem("IS00068")
	private String employmentCode;
	
	@PeregItem("IS00066")
	private GeneralDate startDate;
	
	@PeregItem("IS00067")
	private GeneralDate endDate;
}
