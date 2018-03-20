package nts.uk.ctx.at.record.dom.divergence.time.service.attendance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;

@AllArgsConstructor
@Value
public class DivergenceAttendanceNameDto {
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
}
