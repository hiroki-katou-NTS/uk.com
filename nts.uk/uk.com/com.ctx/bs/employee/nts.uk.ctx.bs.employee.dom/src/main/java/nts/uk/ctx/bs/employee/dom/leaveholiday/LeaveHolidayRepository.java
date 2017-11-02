package nts.uk.ctx.bs.employee.dom.leaveholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface LeaveHolidayRepository {
	
	public Optional<LeaveHoliday> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);
	
}
