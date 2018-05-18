package nts.uk.ctx.at.record.app.find.monthly.root.common;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public abstract class MonthlyItemCommon extends AttendanceItemCommon {

	@Override
	public GeneralDate workingDate() {
		return null;
	}
	
	@Override
	public Object toDomain(String employeeId, GeneralDate date) {
		return null;
	}
	
	public abstract YearMonth yearMonth();
	
	public abstract int getClosureID();
	
	public abstract ClosureDateDto getClosureDate();
}
