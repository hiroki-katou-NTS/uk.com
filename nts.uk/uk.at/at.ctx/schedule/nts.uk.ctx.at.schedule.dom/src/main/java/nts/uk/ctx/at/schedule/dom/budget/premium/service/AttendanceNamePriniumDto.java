package nts.uk.ctx.at.schedule.dom.budget.premium.service;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class AttendanceNamePriniumDto {
	private int attendanceItemId;

	private String attendanceItemName;

	private int attendanceItemDisplayNumber;
}
