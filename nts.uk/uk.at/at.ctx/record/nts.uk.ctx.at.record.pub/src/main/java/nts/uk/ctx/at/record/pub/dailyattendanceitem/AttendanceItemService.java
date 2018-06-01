package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public interface AttendanceItemService {

	public Optional<AttendanceItemValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId);

	/** RequestList332 */
	public AttendanceResult getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

	public List<AttendanceResult> getValueOf(Collection<String> employeeId, DatePeriod workingDate,
			Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(Collection<String> employeeId, YearMonth yearMonth,
			int closureId, int clouseDate, boolean lastDayOfMonth, Collection<Integer> itemIds);

	public MonthlyAttendanceResult getMonthlyValueOf(String employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(Collection<String> employeeId, DatePeriod range,
			Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(String employeeId, DatePeriod range, Collection<Integer> itemIds);
	
	public List<MonthlyAttendanceResult> getMonthlyValueOf(Collection<String> employeeId, YearMonthPeriod range,
			Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(String employeeId, YearMonthPeriod range, Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(Collection<String> employeeId, YearMonth ym,
			Collection<Integer> itemIds);

	public List<MonthlyAttendanceResult> getMonthlyValueOf(String employeeId, YearMonth ym, Collection<Integer> itemIds);
}
