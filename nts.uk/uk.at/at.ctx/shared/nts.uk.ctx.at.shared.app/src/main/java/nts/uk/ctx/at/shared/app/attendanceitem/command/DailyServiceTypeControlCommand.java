package nts.uk.ctx.at.shared.app.attendanceitem.command;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class DailyServiceTypeControlCommand {
		public String attendanceItemId;

		public String businessTypeCode;

		public boolean youCanChangeIt;

		public boolean canBeChangedByOthers;
		
		public boolean use;
}
