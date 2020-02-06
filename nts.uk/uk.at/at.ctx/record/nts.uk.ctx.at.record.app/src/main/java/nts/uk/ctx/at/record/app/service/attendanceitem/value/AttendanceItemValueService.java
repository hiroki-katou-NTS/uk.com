package nts.uk.ctx.at.record.app.service.attendanceitem.value;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public interface AttendanceItemValueService {
	
	public Optional<ItemValue> getValueOf(String employeeId, GeneralDate workingDate, int itemId);

	/** RequestList332 */
	public AttendanceItemValueResult getValueOf(String employeeId, GeneralDate workingDate, Collection<Integer> itemIds);

	public List<AttendanceItemValueResult> getValueOf(Collection<String> employeeId, DatePeriod workingDate,
			Collection<Integer> itemIds);

	public MonthlyAttendanceItemValueResult getMonthlyValueOf(String employeeId, YearMonth yearMonth, int closureId,
			int clouseDate, boolean lastDayOfMonth, Collection<Integer> itemIds);

	/** RequestList421 */
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, DatePeriod range,
			Collection<Integer> itemIds);

	/** RequestList421 */
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, DatePeriod range, Collection<Integer> itemIds);
	
	/** RequestList421 */
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, YearMonthPeriod range,
			Collection<Integer> itemIds);

	/** RequestList421 */
	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, YearMonthPeriod range, Collection<Integer> itemIds);

	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(Collection<String> employeeId, YearMonth ym,
			Collection<Integer> itemIds);

	public List<MonthlyAttendanceItemValueResult> getMonthlyValueOf(String employeeId, YearMonth ym, Collection<Integer> itemIds);
	
	@Getter
	@Builder
	public class AttendanceItemValueResult {

		private String employeeId;
		
		private GeneralDate workingDate;
		
		private List<ItemValue> attendanceItems;
	}

	@Getter
	@Builder
	public class MonthlyAttendanceItemValueResult {

		private String employeeId;

		private YearMonth yearMonth;
		
		private int closureId;
		
		private int clouseDate;
		
		private boolean lastDayOfMonth;

		private List<ItemValue> attendanceItems;
	}
}
