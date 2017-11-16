package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

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
