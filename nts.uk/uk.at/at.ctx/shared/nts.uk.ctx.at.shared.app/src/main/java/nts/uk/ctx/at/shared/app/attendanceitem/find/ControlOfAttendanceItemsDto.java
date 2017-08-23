package nts.uk.ctx.at.shared.app.attendanceitem.find;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class ControlOfAttendanceItemsDto {
	public int attandanceTimeId;
	public String attandanceName;
	public int inputUnitOfTimeItem;
	public String headerBackgroundColorOfDailyPerformance;
	public int nameLineFeedPosition;
}
