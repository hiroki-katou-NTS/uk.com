package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;




import lombok.Getter;

import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の休職期間
 * @author HieuLt
 *
 */

@Setter
@Getter
public class EmployeeLeaveJobPeriodImport {
	/** 社員ID **/
	
	private String empID;
	/** 期間 **/
	private DatePeriod datePeriod;
	
	//C-0
	public EmployeeLeaveJobPeriodImport(String empID, DatePeriod datePeriod) {
		super();
		this.empID = empID;
		this.datePeriod = datePeriod;
	}
	
}
