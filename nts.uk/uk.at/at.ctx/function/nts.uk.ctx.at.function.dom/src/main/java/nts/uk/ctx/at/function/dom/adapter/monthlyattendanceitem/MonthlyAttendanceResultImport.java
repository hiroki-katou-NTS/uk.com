package nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyAttendanceResultImport {

	private String employeeId;

	private YearMonth yearMonth;

	private int closureId;

	private int clouseDate;

	private boolean lastDayOfMonth;

	private List<AttendanceItemValueImport> attendanceItems;
}
