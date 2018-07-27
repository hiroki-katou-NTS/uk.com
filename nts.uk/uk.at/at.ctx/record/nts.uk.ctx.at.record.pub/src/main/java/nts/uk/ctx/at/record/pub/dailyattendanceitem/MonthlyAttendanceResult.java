package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAttendanceResult {

	private String employeeId;

	private YearMonth yearMonth;
	
	private int closureId;
	
	private int clouseDate;
	
	private boolean lastDayOfMonth;

	private List<AttendanceItemValue> attendanceItems;
}
