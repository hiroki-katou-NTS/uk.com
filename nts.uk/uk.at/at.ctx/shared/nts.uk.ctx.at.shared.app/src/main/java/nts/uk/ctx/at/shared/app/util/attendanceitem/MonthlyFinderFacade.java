package nts.uk.ctx.at.shared.app.util.attendanceitem;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public abstract class MonthlyFinderFacade extends FinderFacade {

	@Override
	public <T extends ConvertibleAttendanceItem> T find(String employeeId, GeneralDate baseDate) {
		return null;
	}
	
	public abstract <T extends ConvertibleAttendanceItem> T find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
}
