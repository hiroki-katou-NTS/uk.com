package nts.uk.ctx.at.record.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class BPUnitUseSettingUpdateCommand {
		public int workplaceUseAtr;
		public int personalUseAtr;
		public int workingTimesheetUseAtr;

}
