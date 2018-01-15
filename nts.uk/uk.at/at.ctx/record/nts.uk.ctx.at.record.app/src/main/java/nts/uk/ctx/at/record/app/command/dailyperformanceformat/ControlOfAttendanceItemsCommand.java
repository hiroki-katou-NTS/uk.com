package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class ControlOfAttendanceItemsCommand {
		public int attandanceTimeId;
		public int inputUnitOfTimeItem;
		public String headerBackgroundColorOfDailyPerformance;
		public int nameLineFeedPosition;

}
