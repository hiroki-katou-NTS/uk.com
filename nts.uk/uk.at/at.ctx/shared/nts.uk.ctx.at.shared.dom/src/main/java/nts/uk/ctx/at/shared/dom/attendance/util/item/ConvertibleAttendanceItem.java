package nts.uk.ctx.at.shared.dom.attendance.util.item;

import nts.arc.time.GeneralDate;

public interface ConvertibleAttendanceItem {
	
	String employeeId();
	
	GeneralDate workingDate();
}
