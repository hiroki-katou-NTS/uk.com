package nts.uk.ctx.at.shared.app.util.attendanceitem;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public abstract class FinderFacade {

	public abstract <T extends ConvertibleAttendanceItem> T find(String employeeId, GeneralDate baseDate);
}
