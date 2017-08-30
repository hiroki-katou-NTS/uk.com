package nts.uk.ctx.at.shared.app.attendanceitem.find;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class ControlOfAttendanceItemsDto {
	public int attendanceItemId;
	public String attendanceItemName;
	public int inputUnitOfTimeItem;
	public String headerBackgroundColorOfDailyPerformance;
	public int nameLineFeedPosition;
}
