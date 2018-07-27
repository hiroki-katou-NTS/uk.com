package nts.uk.ctx.at.shared.dom.adapter.attendanceitemname;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class MonthlyAttendanceItemNameDto {
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
}
