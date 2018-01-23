package nts.uk.ctx.at.shared.app.util.attendanceitem;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public interface SetterCommonCommand {

	public void setRecords(ConvertibleAttendanceItem item);
	
	public void forEmployee(String employeId);
	
	public void withDate(GeneralDate date);
}
