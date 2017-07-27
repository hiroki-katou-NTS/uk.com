package nts.uk.ctx.at.shared.app.attendanceitem.command;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class ControlOfAttendanceItemsCommand {
		public String attandanceTimeId;
		public int inputUnitOfTimeItem;
		public String headerBackgroundColorOfDailyPerformance;
		public int nameLineFeedPosition;

}
