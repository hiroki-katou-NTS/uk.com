package nts.uk.ctx.at.shared.dom.employment.employwork.leaveinfo;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;

public class EmployeeLeaveJobPeriodImportHelper {

	public static EmployeeLeaveJobPeriodImport getData(){
		
		return new EmployeeLeaveJobPeriodImport(
				"empID-000001",
				new DatePeriod(GeneralDate.today(), GeneralDate.today()));
	}
}
