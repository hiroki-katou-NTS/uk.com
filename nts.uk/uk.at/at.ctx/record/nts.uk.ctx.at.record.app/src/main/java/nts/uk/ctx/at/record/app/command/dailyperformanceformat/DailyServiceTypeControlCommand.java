package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class DailyServiceTypeControlCommand {
		public int attendanceItemId;

		public String businessTypeCode;

		public boolean youCanChangeIt;

		public boolean canBeChangedByOthers;
		
		public boolean use;
}
