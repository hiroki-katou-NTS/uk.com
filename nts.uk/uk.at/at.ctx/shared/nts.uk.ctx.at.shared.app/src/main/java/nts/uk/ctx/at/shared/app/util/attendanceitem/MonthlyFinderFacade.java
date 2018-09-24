package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public abstract class MonthlyFinderFacade extends FinderFacade {

	@Override
	public <T extends ConvertibleAttendanceItem> T find(String employeeId, GeneralDate baseDate) {
		return null;
	}
	
	public abstract <T extends ConvertibleAttendanceItem> T find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
	
	public <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate){
		return new ArrayList<>();
	}
	
	public abstract <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, DatePeriod range);
	
	public abstract <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, YearMonth yearMonth);

	public abstract <T extends ConvertibleAttendanceItem> List<T> find(Collection<String> employeeId, Collection<YearMonth> yearMonth);
	
	public <T extends ConvertibleAttendanceItem> List<T> finds(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		return new ArrayList<>();
	}
}
